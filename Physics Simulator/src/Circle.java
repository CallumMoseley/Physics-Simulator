import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Circle implements PhysicsObject
{
	private static final double RESTITUTION = 0.8;
	private Vector2D position;
	private Vector2D velocity;
	private Vector2D acceleration;
	private double radius;
	private double mass;
	
	private ArrayList<PhysicsObject> alreadyCollided;
	
	public Circle(Vector2D p, Vector2D v, double r)
	{
		position = p;
		velocity = v;
		
		acceleration = new Vector2D(0, 0.5);
		
		radius = r;
		mass = Math.PI * r * r;
		
		alreadyCollided = new ArrayList<PhysicsObject>();
	}
	
	@Override
	public void update(int width, int height)
	{
		acceleration = PhysicsPanel.gravity;
		velocity.addToThis(acceleration);
		position.addToThis(velocity);
		alreadyCollided.clear();
		
		if (position.x >= width - radius)
		{
			velocity.x = -RESTITUTION * velocity.x;
			velocity.y = RESTITUTION * velocity.y;
			position.x = width - radius;
		}
		if (position.x <= radius)
		{
			velocity.x = -RESTITUTION * velocity.x;
			velocity.y = RESTITUTION * velocity.y;
			position.x = radius + 1;
		}
		if (position.y >= height - radius)
		{
			velocity.y = -RESTITUTION * velocity.y;
			velocity.x = RESTITUTION * velocity.x;
			position.y = height - radius;
		}
		if (position.y <= radius)
		{
			velocity.y = -RESTITUTION * velocity.y;
			velocity.x = RESTITUTION * velocity.x;
			position.y = radius;
		}
	}
	
	@Override
	public void collide(ArrayList<PhysicsObject> objects)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i) != this)
			{
				if (checkCollision(objects.get(i)))
				{
					resolveCollision(objects.get(i));
				}
			}
		}
	}

	@Override
	public void addCollision(PhysicsObject po)
	{
		alreadyCollided.add(po);
	}

	@Override
	public boolean checkCollision(PhysicsObject other)
	{
		if (other instanceof Circle)
		{
			Circle o = (Circle) other;
			return position.subtract(o.position).getLengthSquared() <= (radius + o.radius) * (radius + o.radius);
		}
		return false;
	}

	@Override
	public void resolveCollision(PhysicsObject other)
	{
		if (!alreadyCollided.contains(other))
		{
			if (other instanceof Circle)
			{
				Circle o = (Circle) other;
				Vector2D d = o.position.subtract(position);
				if (d.getLength() < 0.0001)
				{
					return;
				}
				Vector2D dn = d.getNormalized();
				
				position.addToThis(dn.multiply(-(radius + o.radius - d.getLength()) * radius / (radius + o.radius)));
				o.position.addToThis(dn.multiply((radius + o.radius - d.getLength()) * o.radius / (radius + o.radius)));
				
				double p = 2 * (Vector2D.dotProduct(velocity, dn) - Vector2D.dotProduct(o.velocity, dn)) / (mass + o.mass);
				velocity = velocity.subtract(dn.multiply(p * o.mass).multiply(RESTITUTION));
				o.velocity = o.velocity.add(dn.multiply(p * mass).multiply(RESTITUTION));
				
//				velocity = velocity.subtract(position.subtract(o.position).multiply(2 * o.mass / (mass + o.mass) * Vector2D.dotProduct(velocity.subtract(o.velocity), position.subtract(o.position)) / position.subtract(o.position).getLengthSquared()));
//				o.velocity = o.velocity.subtract(o.position.subtract(position).multiply(2 * mass / (mass + o.mass) * Vector2D.dotProduct(o.velocity.subtract(velocity), o.position.subtract(position)) / o.position.subtract(position).getLengthSquared()));
				alreadyCollided.add(other);
				other.addCollision(this);
			}
		}
	}

	@Override
	public void draw(Graphics g, Camera c)
	{
		g.setColor(Color.BLACK);
		g.fillOval((int) Math.round((position.getX() - radius - c.position.x) * c.scale + c.width / 2), (int) Math.round((position.getY() - radius - c.position.y) * c.scale + c.height / 2), (int) (radius * 2 * c.scale), (int) (radius * 2 * c.scale));
	}
}
