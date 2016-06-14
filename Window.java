import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255319694373975038L;
	Painter p;
	public Window(Program b)
	{
		p = new Painter(b);
		p.setSize(new Dimension(800, 600));
		this.setSize(new Dimension(806, 629));
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().add(p);
		revalidate();
	}
	public void init()
	{
		
	}
	
	public void render()
	{
		p.repaint();
	}
}
