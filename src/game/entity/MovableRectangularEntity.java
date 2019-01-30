package game.entity;

import game.entity.MovableEntity;

public class MovableRectangularEntity extends MovableEntity {

	//TO-DO Read player info from file and then delete some constructors
	
	//Using composition as multiple inheritance is not allowed
	RectangularEntity rec;

	public MovableRectangularEntity(float width, float height, Point position) {
		super(position);
		rec = new RectangularEntity(width, height, position);
	}

	public MovableRectangularEntity(float width, float height, Point position, float angle) {
		super(position, angle);
		rec = new RectangularEntity(width, height, position, angle);
	}
	
	public MovableRectangularEntity(float width, float height, Point position, float angle, float speed) {
		super(position, angle, speed);
		rec = new RectangularEntity(width, height, position, angle);
	}
	
	public MovableRectangularEntity(float width, float height, Point position, float angle, float speed, float rotationSpeed) {
		super(position, angle, rotationSpeed);
		rec = new RectangularEntity(width, height, position, angle);
	}

	// collision checking should be added to all movements

	public Point[] getCorners() {
		return rec.getCorners();
	}
	
	@Override
	public void forward(){
		super.forward();
		//We must update the position
		rec.setPosition(this.getPosition());
	}
	
	@Override
	public void backwards() {
		super.backwards();
		rec.setPosition(this.getPosition());
	}
	
	@Override
	public void rotateRight() {
		super.rotateRight();
		rec.setAngle(this.getAngle());
	}
	
	@Override
	public void rotateLeft() {
		super.rotateLeft();
		rec.setAngle(this.getAngle());
	}
}
