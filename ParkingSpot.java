
public class ParkingSpot {
	
	public Position p;
	private BoundingBox b;
	public Forklift f;
	public Truck t;
	public boolean empty = true;
	private Parking par;
	
	public ParkingSpot(Position p, BoundingBox b, Forklift f, Parking par) 
	{
		super();
		this.p = p;
		this.setB(b);
		this.f = f;
		f.par = this;
		this.par = par;
	}
	
	
	public Position callForklift()
	{
		f.v.x = 10d;
		par.signalDrive();
		return this.p;
	}
	
	public boolean endDrop()
	{
		Velocity tvel;
		par.waitDrive();
		t.setDepart(true);
		t.setState(3);
		empty = true;
		tvel = new Velocity(20d, 0d);
		t.setV(tvel);
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
