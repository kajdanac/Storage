
public class Position
{
	volatile double x,y;
	public Position(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	public Position(Position p)
	{
		x = new Double(p.x);
		y = new Double(p.y);
	}
	public void set(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	public void move(Double x, Double y)
	{
		this.x += x;
		this.y += y;
	}
}
