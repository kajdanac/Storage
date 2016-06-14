import java.util.Date;
//import java.util.Random;

public class TruckThread implements Runnable
{
	Truck t;
	Double dt;
	public TruckThread()
	{
		//Random r = new Random(System.currentTimeMillis());
		dt = 0d;
	}
	public TruckThread(Truck t)
	{
		this.t= t;
		dt = 0d;
	}
	/*@Override
	protected Object doInBackground() throws Exception
	{
		while(true)
		{
			Date d1 = new Date();
			t.update(dt);
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			Date d2 = new Date();
			dt = ((d2.getTime()-d1.getTime())/1000d);
			d1 = d2 = null;
			
		}
	}*/
	
	@Override
	public void run()
	{
		while(t.getLive())
		{
			Date d1 = new Date();
			t.update(dt);
			try
			{
				Thread.sleep(33);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			Date d2 = new Date();
			dt = (d2.getTime()-d1.getTime())/1000d;
			d1 = d2 = null;
			
		}
	}

}
