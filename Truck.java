

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
	private Boolean arrive;
	private Boolean depart;
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
		arrive = true;
		depart = false;
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
		
		if(!depart)
		{
			if(p.x < 480)
			{
				if(arrive)
				{
					pa = programe.pl.getFreeSpot();
					if(pa == null)
					{
						v.x = 0.0;
						p.x = 480;
					}
					else
					{
						arrive = false;
						pa.t = this;
						if(pa.getB().p.y-p.y > 0)
						{
							v.y = 20d;
						}
						else
						{
							v.y = -20d;
						}
					}
				}
			}
			if(v.y > 0)
			{
				if(pa.getB().p.y-p.y <= 0)
				{
					p.y = pa.getB().p.y;
					v.y = 0d;
				}
			}
			else if(v.y < 0)
			{
				if(p.y-pa.getB().p.y <= 0)
				{
					p.y = pa.getB().p.y;
					v.y = 0d;
				}
			}
			if(pa != null && p.x <= pa.getB().p.x)
			{
				v.x = 0d;
				pa.callForklift();
			}
		}
		else
		{
			if(v.y > 0)
			{
				if(p.y > 296)
				{
					v.set(20d, 0d);
				}
			}
			else if(v.y < 0)
			{
				if(p.y < 296)
				{
					v.set(20d, 0d);
				}
			}
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
}
