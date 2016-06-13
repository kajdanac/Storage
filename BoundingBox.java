
public class BoundingBox
{
	Position p;
	Double width,heigth;
	public BoundingBox(Position p, Double width, Double heigth)
	{
		this.p = p;
		this.width = width;
		this.heigth = heigth;
	}
	public Boolean intersects(BoundingBox b)
	{
		if(p.x > b.p.x+b.width || p.y > b.p.y+b.heigth)
			return false;
		if(p.x+width < b.p.x || p.y+heigth < b.p.y)
			return false;
		return true;
	}
}
