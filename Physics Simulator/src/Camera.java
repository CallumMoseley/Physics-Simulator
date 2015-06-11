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
}