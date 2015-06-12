public class Camera
{
	public Vector2D position;
	public double scale;
	public int width;
	public int height;
	
	public Camera(Vector2D p, double s, int w, int h)
	{
		position = p;
		scale = s;
		width = w;
		height = h;
	}
	
	public Vector2D transform(Vector2D v)
	{
		return new Vector2D((v.x - position.x) * scale + width / 2, (v.y - position.y) * scale + height / 2);
	}
	
	public Vector2D untransform(Vector2D v)
	{
		return new Vector2D(position.x + (v.x - width / 2) / scale, position.y + (v.y - height / 2) / scale);
	}
}