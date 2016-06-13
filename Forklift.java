import java.util.Timer;
import java.util.TimerTask;

public class Forklift extends Entity
{
	Boolean loaded;
	Long t;
	ParkingSpot par;
	Timer ti;
	Position back;
	public Forklift()
	{
		p = new Position(0d, 0d);
		b = new BoundingBox(p, 64d, 32d);
		v = new Velocity(0d, 0d);
	}
	public Forklift(Position p, BoundingBox b, Velocity v, Long t)
	{
		this.p = p;
		this.b = b;
		this.v = v;
		this.t = t;
		loaded = false;
		angle = new Long(0);
		angVel = new Long(0);
		ti = new Timer();
		back = new Position(p);
	}
	@Override
	public void update(Double dt)
	{
		p.x += v.x*dt;
		angle += (angVel*dt.longValue())%360;
		if(b.intersects(par.getB()))
		{
			v.set(0d, 0d);
			p.x = par.getB().p.x-b.width;
			ti.schedule(new TimerTask()
			{
				
				@Override
				public void run()
				{
					Forklift.this.par.endDrop();
					v.x = -10d;
				}
			}, t);
		}
		if(p.x <= back.x)
		{
			p.x = back.x;
			v.set(0d, 0d);
		}
	}

}
