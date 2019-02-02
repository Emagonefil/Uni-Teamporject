package game.entity;

import java.io.Serializable;

public class Point implements Serializable {
	private float x;
	private float y;
	
	//Shouldn't really be used
	public Point() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static float distance(Point p1, Point p2) {
		return (float) Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY()-p1.getY()), 2));
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void changeX(float change) {
		this.x += change;
	}
	
	public void changeY(float change) {
		this.y += change;
	}
	
	@Override
	public String toString() {
		return ("(" + getX() + ", " + getY() + ")");
	}
}
