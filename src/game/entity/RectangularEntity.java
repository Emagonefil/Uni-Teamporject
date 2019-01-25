package game.entity;

import game.entity.MovableEntity;

public class RectangularEntity extends Entity implements MovableEntity {

	private RectangularEntity() {}
	private float speed;
	private float rotationSpeed;
	private float width;
	private float height;
	
	public RectangularEntity(float width, float height, Point position) {
		this.width = width;
		this.height = height;
		this.position = position;
		//Hardcoded at the moment can add more constructors later
		this.angle = 0.0f;
		this.speed = 0.5f;
	}
	
	public void getCorners() {
		
	}
	
	//Remember that the corners must be updated!
	
	@Override
	public void forward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backwards() {
		// TODO Auto-generated method stub

	}

	/*
	//This will not work without storing corners
	private void rotate(float amount) {
		Point center = this.position;
		for (Point corner:this.corners) {
			float tempX = corner.getX()-center.getX();
			float tempY = corner.getY()-center.getY();
			
			float rotX = (float) (tempX * Math.cos(amount) - tempY * Math.sin(amount));
			float rotY = (float) (tempX*Math.sin(amount) + tempY * Math.cos(amount));
			
			corner.setX(rotX + center.getX());
			corner.setY(rotY + center.getY()); 
		}
	}
	*/
	
	@Override
	public void rotateRight() {
		
	}

	@Override
	public void rotateLeft() {
		// TODO Auto-generated method stub

	}

}
