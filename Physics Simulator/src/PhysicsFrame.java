import javax.swing.JFrame;

public class PhysicsFrame extends JFrame
{
	private PhysicsPanel panel;

	public PhysicsFrame()
	{
		super("Physics Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new PhysicsPanel();
		setContentPane(panel);
	}

	public static void main(String[] args)
	{
		PhysicsFrame frame = new PhysicsFrame();
		frame.pack();
		frame.setVisible(true);
	}
}