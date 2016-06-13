import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Parking 
{
	LinkedList<ParkingSpot> pl;
	Semaphore drive;
	LinkedList<Thread> t;

	public Parking(Program p) {
		super();
		pl = new LinkedList<ParkingSpot>();
		ParkingSpot p1=new ParkingSpot(new Position(196d, 92d), new BoundingBox(new Position(199d,72d),91d,41d), p.fl.getFirst(), this);
		pl.add(p1);
		ParkingSpot p2=new ParkingSpot(new Position(196d, 238d), new BoundingBox(new Position(199d,205d),91d,41d), p.fl.get(1), this);
		pl.add(p2);
		ParkingSpot p3=new ParkingSpot(new Position(196d, 336d), new BoundingBox(new Position(199d,329d),91d,41d), p.fl.get(2), this);
		pl.add(p3);
		ParkingSpot p4=new ParkingSpot(new Position(196d, 467d), new BoundingBox(new Position(199d,461d),91d,41d), p.fl.getLast(), this);
		pl.add(p4);
		drive = new Semaphore(1);
		t = new LinkedList<Thread>();
	}
	
	public synchronized ParkingSpot getFreeSpot()
	{
		for(ParkingSpot tmp : pl)
		{
			if(tmp.empty)
			{
				tmp.empty = false;
				waitDrive();
				return tmp;
			}
		}
		return null;
	}
	public synchronized void waitDrive()
	{
		while(!drive.tryAcquire())
		{
			try
			{
				this.wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	public synchronized void signalDrive()
	{
		drive.release();
		this.notifyAll();
	}
}
