import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Program
{
	Window w;
	LinkedList<Truck> tl;
	LinkedList<Forklift> fl;
	Timer t;
	Boolean u;
	TruckFactory tf;
	Semaphore s;
	Parking pl;
	volatile Boolean start;
	private void init()
	{
		w = null;
		tl = new LinkedList<Truck>();
		fl = new LinkedList<Forklift>();
		u = true;
		tf = new TruckFactory(this,new Position(800d, 244d), new Velocity(-20d, 0d));
		s = new Semaphore(1);
		t = new Timer();
		start = true;
		Position k;
		ForkliftThread tt;
		Thread nt;
		k = new Position(96d, 72d);
		fl.add(new Forklift(k, new BoundingBox(k, 64d, 32d), new Velocity(0d,  0d), new Long(10000)));
		k = new Position(96d, 205d);
		fl.add(new Forklift(k, new BoundingBox(k, 64d, 32d), new Velocity(0d,  0d), new Long(10000)));
		k = new Position(96d, 329d);
		fl.add(new Forklift(k, new BoundingBox(k, 64d, 32d), new Velocity(0d,  0d), new Long(10000)));
		k = new Position(96d, 461d);
		fl.add(new Forklift(k, new BoundingBox(k, 64d, 32d), new Velocity(0d,  0d), new Long(10000)));
		pl = new Parking(this);
		for(Forklift tmp : fl)
		{
			tt = new ForkliftThread(tmp);
			nt = new Thread(tt);
			nt.start();
		}
	}
	public void run()
	{
		init();
		try
		{
			EventQueue.invokeAndWait(new Runnable()
			{
				
				@Override
				public void run()
				{
					w = new Window(Program.this);
					w.setVisible(true);
				}
			});
		} catch (InvocationTargetException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Loop();
	}
	private void Loop()
	{
		while(true)
		{	
			if(u && start)
			{
				update();
				Truck tmp = null;
				tmp = tf.nextTruck();
				if(tmp != null)
				{
					s.acquireUninterruptibly();
					tl.add(tmp);
					s.release();
					TruckThread tt = new TruckThread(tl.getLast());
					Thread tn = new Thread(tt);
					tn.setDaemon(true);
					tn.start();
					start = false;
				}
			}
			render();
		}
	}
	
	private void update()
	{
		u = false;
		Random r = new Random(System.currentTimeMillis());
		t.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				u = true;
			}
		}, new Long(8000+r.nextInt(10000)));
	}
	private void render()
	{
		w.render();
	}
}
