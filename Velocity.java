
public class Velocity
{
	Double x,y;
	public Velocity(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	public Velocity(Velocity v)
	{
		x = new Double(v.x);
		y = new Double(v.y);
	}
	public void set(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
}
