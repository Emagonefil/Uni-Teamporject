package game.entity;

import java.awt.Point;
import java.io.Serializable;

public class Entity implements Serializable{
	
	public Entity(float a) {
		this.angle = a;
	}
	//Note: origin is bottom left corner.
	Point position;
	//0 degrees means pointing up i.e. towards positive y
	float angle;
	
	public float getAngle() {
		return angle;
	}
	
	public Point getPosition() {
		return position;
	}
}