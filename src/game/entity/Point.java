package game.entity;

public class Point {
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
}
