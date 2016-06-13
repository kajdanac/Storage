import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Painter extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8870656231956300015L;
	Program p;
	BufferedImage background;
	BufferedImage truck;
	BufferedImage forklift;
	AffineTransform tr;
	public Painter(Program p)
	{
		this.p = p;
		try
		{
			background = ImageIO.read(getClass().getResource("/art/background.png"));
			truck = ImageIO.read(getClass().getResource("art/truck.png"));
			forklift = ImageIO.read(getClass().getResource("art/forklift.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D b = (Graphics2D)g;
		b.drawImage(background, 0, 0, 800, 600, 0, 0, 800, 600, null);
		p.s.acquireUninterruptibly();
		for(Truck tmp : p.tl)
		{
			tr = new AffineTransform(b.getTransform());
			b.translate((int)tmp.getP().x+45, (int)tmp.getP().y+16);
			b.rotate(Math.toRadians(tmp.getAngle()));
			b.drawImage(truck, -45, -16,
					45, 16,
					0, 0, 90, 32, null);
			b.setTransform(tr);
		}
		p.s.release();
		for(Forklift tmp : p.fl)
		{
			tr = new AffineTransform(b.getTransform());
			b.translate((int)tmp.p.x+32, (int)tmp.p.y+16);
			b.rotate(Math.toRadians(tmp.angle));
			b.drawImage(forklift, -32, -16,
					32, 16,
					0, 0, 64, 32, null);
			b.setTransform(tr);
		}
		super.paint(g);
	}
}
