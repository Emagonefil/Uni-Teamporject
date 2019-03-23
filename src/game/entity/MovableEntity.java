package game.entity;

/**
 * This class is an extension of Entity and provides methods that can move and rotate an entity.
 * 
 * @author callum
 *
 */
public abstract class MovableEntity extends Entity {

	/**
	 * The amount by which this entity will be moved when forward() or backward() is called.
	 */
	private float speed;
	
	/**
	 * The amount of degrees that this entity will rotate when rotateRight() or rotateLeft() is called.
	 */
	private float rotationSpeed;
	
	/**
	 * The constructor for MovableEntity, takes values for all fields and sets the fields to the given values
	 * 
	 * @param width The width for the new MovableEntity
	 * @param height The height for the new MovableEntity
	 * @param position The position for the centre of the new MovableEntity
	 * @param angle The direction in which the new MovableEntity should point(in degrees from positive y)
	 * @param speed The speed with which this new MovableEntity should move
	 * @param rotationSpeed The speed that this MovableEntity should rotate
	 */
	public MovableEntity(float width, float height, Point position, float angle, float speed, float rotationSpeed) {
		super(width, height, position, angle);
		this.speed = speed;
		this.rotationSpeed = rotationSpeed;
	}


	/**
	 * This moves this entity forward, in the direction of the angle field by the distance of the speed field
	 */
	public void forward() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (speed * Math.cos(radAngle)));
		this.position.changeY((float) (speed * Math.sin(radAngle)));
	}

	/**
	 * This moves this entity backward, in the direction opposite to the angle field by the distance of the speed field
	 */
	public void backwards() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (-speed * Math.cos(radAngle)));
		this.position.changeY((float) (-speed * Math.sin(radAngle)));
	}

	/**
	 * Set a new value for this entities speed. This is used by the items in order to give a speed up 
	 * 
	 * @param s The new speed value.
	 */
	public void setSpeed(float s){
		this.speed=s;
	}
	
	/**
	 * Set a new value for the entities rotation speed. This is used by items in order to give a movement boost
	 *  
	 * @param s The new value for rotationSpeed.
	 */
	public void setRotationSpeed(float s) {
		this.rotationSpeed = s;
	}

	/**
	 * This is used to rotate the entity in a clockwise direction by rotationSpeed degrees.
	 */
	public void rotateRight() {
		this.angle = (this.angle + rotationSpeed) % 360;
	}

	/**
	 * This is used to rotate the entity in an anti-clockwise direction by rotationSpeed degrees.
	 */
	public void rotateLeft() {
		this.angle = (this.angle - rotationSpeed) % 360;
	}

	/**
	 * Returns the value of speed
	 * 
	 * @return this entities speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Returns the value of rotationSpeed
	 * 
	 * @return this entities rotationSpeed
	 */
	public float getRotationSpeed() {
		return rotationSpeed;
	}
}
