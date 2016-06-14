
public class ParkingSpot {
	
	public Position p;
	private BoundingBox b;
	public Forklift f;
	public Truck t;
	public boolean empty = true;
	
	public ParkingSpot(Position p, BoundingBox b, Forklift f, Parking par) 
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
		t.setDepart(true);
		t.setState(5);
		tvel = new Velocity(20d, 0d);
		t.setV(tvel);
		t = null;
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
