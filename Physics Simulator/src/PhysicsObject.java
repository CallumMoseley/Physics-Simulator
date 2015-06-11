import java.awt.Graphics;
import java.util.ArrayList;

public interface PhysicsObject
{
	public void update(int width, int height);
	public void collide(ArrayList<PhysicsObject> objects);
	public void addCollision(PhysicsObject po);
	public boolean checkCollision(PhysicsObject other);
	public void resolveCollision(PhysicsObject other);
	public void draw(Graphics g, Camera c);
}