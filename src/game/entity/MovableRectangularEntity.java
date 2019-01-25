package game.entity;

import game.entity.MovableEntity;

public class MovableRectangularEntity extends RectangularEntity implements MovableEntity {
	
	private float speed;
	private float rotationSpeed;

	public MovableRectangularEntity(float width, float height, Point position) {
		super(width, height, position);
		//Hardcoded at the moment can add more constructors later
		this.angle = 0.0f;
		this.speed = 0.5f;
		this.rotationSpeed = 1.0f;
	}

	//collision checking should be added to all movements
	
	@Override
	public void forward() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (speed * Math.sin(radAngle)));
		this.position.changeY((float) (speed*Math.cos(radAngle)));
	}

	@Override
	public void backwards() {
		float radAngle = (float) Math.toRadians(this.angle);
		this.position.changeX((float) (-speed * Math.sin(radAngle)));
		this.position.changeY((float) (-speed*Math.cos(radAngle)));
	}
	
	@Override
	public void rotateRight() {
		this.angle = (this.angle + rotationSpeed)%360;
	}

	@Override
	public void rotateLeft() {
		this.angle = (this.angle - rotationSpeed)%360;
	}
	
}
