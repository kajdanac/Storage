import java.util.Random;

public class TruckFactory
{
	Velocity v;
	Position p;
	Integer full;
	Integer empty;
	Truck tmp;
	Program programe;
	
	public TruckFactory(Program prog)
	{
		programe = prog;
		v = new Velocity(0d, 0d);
		p = new Position(0d, 0d);
		tmp = null;
		full = 0;
		empty = 0;
	}
	public TruckFactory(Program prog,Position p, Velocity v)
	{
		this.p = p;
		this.v = v;
		tmp = null;
		full = 0;
		empty = 0;
		programe = prog;
	}
	public void returnFull()
	{
		if(full > 0)
			full--;
	}
	public void returnEmpty()
	{
		if(empty > 0)
			empty--;
	}
	public Truck nextTruck()
	{
		Position t = new Position(p);
		Random r = new Random(System.currentTimeMillis());
		if(r.nextBoolean() && full < 4)
		{
			tmp = new Truck(programe,t, new BoundingBox(t, 90d, 32d), new Velocity(v), true, 3000l+r.nextInt(10000));
			full++;
			return tmp;
		}
		else if(empty < 4)
		{
			tmp = new Truck(programe,t, new BoundingBox(t, 90d, 32d), new Velocity(v), false, 3000l+r.nextInt(10000));
			empty++;
			return tmp;
		}
		return null;
	}
}
