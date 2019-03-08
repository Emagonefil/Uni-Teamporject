package game.entity;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Entity implements IEntity, Serializable {
	// Note: origin is bottom left corner.
	Point position;
	// REMEBER:ANGLE IS IN DEGREES
	// 0 degrees means pointing up i.e. towards positive y
	float angle;
	public int id;
	public String type;
	public float width;
	public float height;
	public Image image;
	// Dont use this
	public Entity() {
		this.position = new Point(0.0f, 0.0f);
		this.angle = 0.0f;
	}

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
	
	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	public void draw() {
		System.out.println("must override draw method for " +  this.getClass().getName());
	}

}
