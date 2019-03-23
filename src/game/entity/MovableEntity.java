package game.entity;

public abstract class MovableEntity extends Entity {

	private float speed;
	private float rotationSpeed;
	
	public MovableEntity(float width, float height, Point position, float angle, float speed, float rotationSpeed) {
		super(width, height, position, angle);
		this.speed = speed;
		this.rotationSpeed = rotationSpeed;
	}


	public void forward() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (speed * Math.cos(radAngle)));
		this.position.changeY((float) (speed * Math.sin(radAngle)));
	}

	public void backwards() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (-speed * Math.cos(radAngle)));
		this.position.changeY((float) (-speed * Math.sin(radAngle)));
	}

	public void setSpeed(float s){
		this.speed=s;
	}
	
	public void setRotationSpeed(float s) {
		this.rotationSpeed = s;
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
