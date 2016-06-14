

public class Truck extends Entity
{
	private Boolean full;
	private Long t;
	private Boolean live;
	Position p;
	private BoundingBox b;
	private Velocity v;
	Long angle;
	private Long angVel;
	private Boolean colliding;
	private Program programe;
	private BoundingBox front;
	private ParkingSpot pa;
	private Boolean depart;
	private volatile Boolean drive;
	private volatile Integer state;
	public Truck(Program prog,Position pos, BoundingBox box, Velocity vel, Boolean full, Long time)
	{
		programe = prog;
		p = pos;
		setB(box);
		v = vel;
		this.setFull(full);
		setT(time);
		angle = new Long(180);
		angVel = new Long(0);
		depart = false;
		state = 0;
		drive = true;
		live = true;
		colliding = false;
		front = new BoundingBox(p, -3d, 32d);
		pa = null;
	}
	
	@Override
	public void update(Double dt)
	{
		if(!colliding)
			if(drive)
			{
				p.x += v.x*dt;
				p.y += v.y*dt;
			}
			else
			{
				drive = programe.pl.drive.tryAcquire();
			}
		for(Truck tmp : programe.tl)
		{
			if(tmp == null)
				break;
			if(this!=tmp)
			{
				if(tmp.getDepart())
					continue;
				if(this.front.intersects(tmp.getB()))
				{
					if(!colliding)
						p.x = tmp.getP().x+90;
					colliding = true;
					break;
					
				}
				else
				{
					colliding = false;
				}
				
			}
			
		}
		switch(state)
		{
		case 0:
			if(p.x < 690)
			{
				programe.start = true;
				state = 1;
			}
			break;
		case 1:
			if(p.x < 480)
			{
				if(pa == null)
					pa = programe.pl.getFreeSpot();
				if(pa == null)
				{
					colliding = true;
				}
				else
				{
					colliding = false;
				}
				drive = false;
				if(pa != null)
					state = 2;
			}
			break;
		case 2:
			if(p.x < 390)
			{
				if(pa != null)
				{
					pa.t = this;
					state = 3;
					if(pa.getB().p.y > p.y)
					{
						v.set(0d, 20d);
						angle = (long)90;
						b.width = 32d;
						b.heigth = 90d;
					}
					else
					{
						v.set(0d, -20d);
						angle = (long)270;
						b.width = 32d;
						b.heigth = 90d;
					}
				}
			}
			break;
		case 3:
			if(v.y > 0)
			{
				if(pa.getB().p.y <= p.y)
				{
					p.y = pa.getB().p.y;
					v.set(-10d, 0d);
					angle = (long)0;
					state = 4;
				}
			}
			else
			{
				if(pa.getB().p.y >= p.y)
				{
					p.y = pa.getB().p.y;
					v.set(-10d, 0d);
					angle = (long)0;
					b.heigth = 32d;
					b.width = 90d;
					state = 4;
				}
			}
			break;
		case 4:
			if(pa.getB().p.x >= p.x)
			{
				p.x = pa.getB().p.x;
				pa.callForklift();
				programe.pl.drive.release();
				v.set(0d, 0d);
				state = 11;
			}
			break;
		case 5:
			drive = false;
			state = 6;
			break;
		case 6:
			if(p.x > 295)
			{
				p.x = 295d;
				state = 7;
				pa.empty = true;
				if(296 > p.y)
				{
					v.set(0d, 20d);
					angle = (long)90;
					b.width = 32d;
					b.heigth = 90d;
				}
				else
				{
					v.set(0d, -20d);
					angle = (long)270;
					b.width = 32d;
					b.heigth = 90d;
				}
			}
			break;
		case 7:
			if(v.y > 0)
			{
				if(296 <= p.y)
				{
					p.y = 296;
					v.set(20d, 0d);
					angle = (long)0;
					b.heigth = 32d;
					b.width = 90d;
					state = 8;
				}
			}
			else
			{
				if(296 >= p.y)
				{
					p.y = 296;
					v.set(20d, 0d);
					angle = (long)0;
					b.heigth = 32d;
					b.width = 90d;
					state = 8;
				}
			}
			break;
		case 8:
			if(p.x > 480)
			{
				state = 9;
			}
			break;
		case 9:
			programe.pl.drive.release();
			state = 10;
			break;
		case 10:
			if(p.x >= 800)
			{
				live = false;
				programe.s.acquireUninterruptibly();
				programe.tl.remove(this);
				programe.s.release();
				programe.tf.returnFull();
			}
		default:
			break;
		}
		
	}
	public Boolean getFull()
	{
		return full;
	}
	public  void setFull(Boolean full)
	{
		this.full = full;
	}
	public  Long getT()
	{
		return t;
	}
	public  void setT(Long t)
	{
		this.t = t;
	}
	public  BoundingBox getB()
	{
		return b;
	}
	public  void setB(BoundingBox b)
	{
		this.b = b;
	}
	public  Position getP()
	{
		return p;
	}
	public  void setP(Position p)
	{
		this.p = p;
	}
	public  Velocity getV()
	{
		return v;
	}
	public  void setV(Velocity v)
	{
		this.v = v;
	}
	public  Long getAngle() {
		return angle;
	}
	public  void setAngle(Long angle) {
		this.angle = angle;
	}
	public  Long getAngVel() {
		return angVel;
	}
	public  void setAngVel(Long angVel) {
		this.angVel = angVel;
	}

	public  Boolean getDepart()
	{
		return depart;
	}

	public  void setDepart(Boolean depart)
	{
		this.depart = depart;
	}

	public  Integer getState()
	{
		return state;
	}

	public synchronized void setState(Integer state)
	{
		this.state = state;
	}

	public synchronized Boolean getDrive()
	{
		return drive;
	}

	public synchronized void setDrive(Boolean drive)
	{
		this.drive = drive;
	}

	public  Boolean getLive()
	{
		return live;
	}

	public  void setLive(Boolean live)
	{
		this.live = live;
	}
}
