package game.entity;

public class MovableEntity extends Entity {

	private float speed = 5.0f;
	private float rotationSpeed = 1.0f;

	public MovableEntity(Point position) {
		super(position);
	}

	public MovableEntity(Point position, float angle) {
		super(position, angle);
	}
	
	public MovableEntity(Point position, float angle, float speed){
		super(position, angle);
		this.speed = speed;
	}
	
	public MovableEntity(Point position, float angle, float speed, float rotationSpeed) {
		super(position, angle);
		this.speed = speed;
		this.rotationSpeed = rotationSpeed;
	}

	public void forward() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (speed * Math.sin(radAngle)));
		this.position.changeY((float) (speed * Math.cos(radAngle)));
	}

	public void backwards() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (-speed * Math.sin(radAngle)));
		this.position.changeY((float) (-speed * Math.cos(radAngle)));
	}

	public void rotateRight() {
		this.angle = (this.angle + rotationSpeed) % 360;
	}

	public void rotateLeft() {
		this.angle = (this.angle - rotationSpeed) % 360;
	}

	public float getSpeed() {
		return speed;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}
}
