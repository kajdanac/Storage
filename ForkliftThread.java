import java.util.Date;

public class ForkliftThread implements Runnable
{
	Forklift f;
	Double dt;
	public ForkliftThread(Forklift f)
	{
		this.f = f;
		dt = 0d;
	}
	@Override
	public void run()
	{
		while(true)
		{
			Date d1 = new Date();
			f.update(dt);
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			Date d2 = new Date();
			dt = (d2.getTime() - d1.getTime())/1000d;
		}
	}

}
