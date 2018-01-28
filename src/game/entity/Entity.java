package game.entity;

public class Entity {
	//Note: origin is bottom left corner.
	Point position;
	//REMEBER:ANGLE IS IN DEGREES
	//0 degrees means pointing up i.e. towards positive y
	float angle;
	
	private Entity() {}
	
	public Entity(Point position) {
		this.position = position;
		this.angle = 0.0f;
	}
	
	public Entity(Point position, float angle) {
		this.position = position;
		this.angle = angle;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public Point getPosition() {
		return position;
	}
}
