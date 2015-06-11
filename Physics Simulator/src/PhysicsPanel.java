import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PhysicsPanel extends JPanel implements KeyListener,
		MouseMotionListener, MouseListener
{
	public static final int FPS = 60;
	
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
//	private long frameCount;
	private long lastFrame;
	private boolean pause;
	private boolean oneTick;
	
	public static Vector2D gravity;
	
	private ArrayList<PhysicsObject> objects;

	public PhysicsPanel()
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		objects = new ArrayList<PhysicsObject>();
		gravity = new Vector2D(0, 0.5);

//		frameCount = 0;
		lastFrame = System.currentTimeMillis();

		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				gameLoop();
			}
		};
		thread.start();
	}

	public void gameLoop()
	{
		while (true)
		{
//			long deltaTime = System.currentTimeMillis() - lastFrame;
//			frameCount++;
			lastFrame = System.currentTimeMillis();
			
			if (!pause)
			{
				for (int i = 0; i < objects.size(); i++)
				{
					objects.get(i).update(getWidth(), getHeight());
				}
				for (int i = 0; i < objects.size(); i++)
				{
					objects.get(i).collide(objects);
				}
			}
			
			if (oneTick)
			{
				pause = true;
				oneTick = false;
			}
			
			repaint();
			try
			{
				long diff = System.currentTimeMillis() - lastFrame;
				Thread.sleep(Math.max(0, 1000 / FPS - diff));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).draw(g);
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			objects.add(new Circle(new Vector2D(Math.random() * getWidth(), Math.random() * getHeight()), new Vector2D(Math.random() * 10 - 5, Math.random() * 10 - 5), 30));
		}
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			pause = !pause;
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			pause = false;
			oneTick = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			objects.clear();
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			gravity = new Vector2D(0, 0.5);
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			gravity = new Vector2D(0, -0.5);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			gravity = new Vector2D(0.5, 0);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			gravity = new Vector2D(-0.5, 0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		objects.add(new Circle(new Vector2D(e.getPoint()), new Vector2D(), 30));
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}
