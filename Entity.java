
public abstract class Entity
{
	volatile Position p;
	BoundingBox b;
	Velocity v;
	Long angle;
	Long angVel;
	public abstract void update(Double dt);
}
