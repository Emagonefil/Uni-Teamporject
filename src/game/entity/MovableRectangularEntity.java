package game.entity;

import game.entity.MovableEntity;
import javafx.scene.image.Image;

public class MovableRectangularEntity extends MovableEntity implements IRectangularEntity {

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
		super(position, angle, speed, rotationSpeed);
		rec = new RectangularEntity(width, height, position, angle);
	}

	// Image constructors
	public MovableRectangularEntity(float width, float height, Point position, float angle, Image image) {
		super(position, angle,image);
		rec = new RectangularEntity(width, height, position, angle);
	}

	public MovableRectangularEntity(float width, float height, Point position, float angle, float speed, Image image) {
		super(position, angle, speed,image);
		rec = new RectangularEntity(width, height, position, angle, image);
	}

	public MovableRectangularEntity(float width, float height, Point position, float angle, float speed, float rotationSpeed, Image image) {
		super(position, angle, speed, rotationSpeed,image);
		rec = new RectangularEntity(width, height, position, angle, image);
	}

	// collision checking should be added to all movements

	public Point[] getCorners() {
		return rec.getCorners();
	}
	
	public float getWidth() {
		return rec.getWidth();
	}
	
	public float getHeight() {
		return rec.getHeight();
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
	
	@Override
	public void setPosition(Point pos) {
		super.setPosition(pos);
		rec.setPosition(pos);
	}
	
	@Override
	public void setAngle(float angle) {
		this.angle = angle;
		rec.setAngle(angle);
	}
	
}
