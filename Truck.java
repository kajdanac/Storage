

public class Truck extends Entity
{
	private Boolean full;
	private Long t;
	private Position p;
	private BoundingBox b;
	private Velocity v;
	private Long angle;
	private Long angVel;
	private Program programe;
	private ParkingSpot pa;
	private Boolean depart;
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
	}
	
	@Override
	public synchronized void update(Double dt)
	{
		p.x += v.x*dt;
		p.y += v.y*dt;
		for(Truck tmp : programe.tl)
		{
			if(v.x == 0 || depart)
				break;
			if(this!=tmp)
			{
				if(tmp.getDepart())
					break;
				if(this.b.intersects(tmp.getB()))
				{
					v.set(0d,  0d);
					p.x = tmp.getP().x+90;
				}
				
			}
			
		}
		switch(state)
		{
		case 0:
			if(p.x < 390)
			{
				p.x = 390d;
				if(pa == null)
					pa = programe.pl.getFreeSpot();
				if(pa != null)
				{
					pa.t = this;
					state = 1;
					if(pa.getB().p.y > p.y)
					{
						v.set(0d, 20d);
						angle = (long)90;
					}
					else
					{
						v.set(0d, -20d);
						angle = (long)270;
					}
				}
			}
			break;
		case 1:
			if(v.y > 0)
			{
				if(pa.getB().p.y <= p.y)
				{
					p.y = pa.getB().p.y;
					v.set(-10d, 0d);
					angle = (long)0;
					state = 2;
				}
			}
			else
			{
				if(pa.getB().p.y >= p.y)
				{
					p.y = pa.getB().p.y;
					v.set(-10d, 0d);
					angle = (long)0;
					state = 2;
				}
			}
			break;
		case 2:
			if(pa.getB().p.x >= p.x)
			{
				p.x = pa.getB().p.x;
				pa.callForklift();
				v.set(0d, 0d);
				state = 6;
			}
			break;
		case 3:
			if(p.x > 295)
			{
				p.x = 295d;
				state = new Integer(4);
				if(296 > p.y)
				{
					v.set(0d, 20d);
					angle = (long)90;
				}
				else
				{
					v.set(0d, -20d);
					angle = (long)270;
				}
			}
			break;
		case 4:
			if(v.y > 0)
			{
				if(296 <= p.y)
				{
					p.y = 296;
					v.set(20d, 0d);
					angle = (long)0;
					state = 5;
				}
			}
			else
			{
				if(296 >= p.y)
				{
					p.y = 296;
					v.set(20d, 0d);
					angle = (long)0;
					state = 5;
				}
			}
			break;
		case 5:
			programe.pl.signalDrive();
			state = 6;
			break;
		default:
			break;
		}
		
	}
	public synchronized Boolean getFull()
	{
		return full;
	}
	public synchronized void setFull(Boolean full)
	{
		this.full = full;
	}
	public synchronized Long getT()
	{
		return t;
	}
	public synchronized void setT(Long t)
	{
		this.t = t;
	}
	public synchronized BoundingBox getB()
	{
		return b;
	}
	public synchronized void setB(BoundingBox b)
	{
		this.b = b;
	}
	public synchronized Position getP()
	{
		return p;
	}
	public synchronized void setP(Position p)
	{
		this.p = p;
	}
	public synchronized Velocity getV()
	{
		return v;
	}
	public synchronized void setV(Velocity v)
	{
		this.v = v;
	}
	public synchronized Long getAngle() {
		return angle;
	}
	public synchronized void setAngle(Long angle) {
		this.angle = angle;
	}
	public synchronized Long getAngVel() {
		return angVel;
	}
	public synchronized void setAngVel(Long angVel) {
		this.angVel = angVel;
	}

	public synchronized Boolean getDepart()
	{
		return depart;
	}

	public synchronized void setDepart(Boolean depart)
	{
		this.depart = depart;
	}

	public synchronized Integer getState()
	{
		return state;
	}

	public synchronized void setState(Integer state)
	{
		this.state = state;
	}
}
