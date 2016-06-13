
public class ParkingSpot {
	
	public Position p;
	private BoundingBox b;
	public Forklift f;
	public Truck t;
	public boolean empty = true;
	
	public ParkingSpot(Position p, BoundingBox b, Forklift f) 
	{
		super();
		this.p = p;
		this.setB(b);
		this.f = f;
		f.par = this;
	}
	
	
	public Position callForklift()
	{
		f.v.x = 10d;
		return this.p;
	}
	
	public boolean endDrop()
	{
		Velocity tvel;
		t.setAngle(new Long(0));
		t.setDepart(true);
		empty = true;
		if(t.getP().y > 312)
		{
			tvel = new Velocity(20d, -20d);
			t.setV(tvel);
		}
		else if(t.getP().y < 312)
		{
			tvel = new Velocity(20d, 20d);
			t.setV(tvel);
		}
		return true;
	}


	public BoundingBox getB()
	{
		return b;
	}


	public void setB(BoundingBox b)
	{
		this.b = b;
	}
	
}
