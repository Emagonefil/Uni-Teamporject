package game.entity;

import java.io.Serializable;

/**
 * This class is a helper class for 2d coordinates
 * (0,0) is the bottom left corner when displayed.
 * 
 * 
 * @author callum
 *
 */
public class Point implements Serializable {
	
	/**
	 * The x distance from the left edge of the
	 * screen
	 */
	private float x;
	
	/**
	 * The y distance from the bottom edge of the
	 * screen
	 */
	private float y;
	
	/**
	 * Create a Point object. Default position (0,0) is used
	 */
	public Point() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	/**
	 * Create a new Point object using the given x and y values
	 * 
	 * @param x The x value for the new Point
	 * @param y The y value for the new Point
	 */
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This gives the Euclidean distance between the two given Points
	 * 
	 * @param p1 The first point
	 * @param p2 The second point
	 * @return The Euclidean distance between the given points
	 */
	public static float distance(Point p1, Point p2) {
		return (float) Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY()-p1.getY()), 2));
	}
	
	/**
	 * Return the x value of this Point
	 * 
	 * @return This Point's x value
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Return the y value of this Point
	 * 
	 * @return This Point's y value
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Set a new x value for this Point
	 * 
	 * @param x The new x value
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Set a new y value for this Point
	 * 
	 * @param y The new y value
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Increase this Point's x value by the given amount
	 * 
	 * @param change The amount to increase x by
	 */
	public void changeX(float change) {
		this.x += change;
	}
	
	/**
	 * Increase this Point's y value by the given amount
	 *
	 * @param change The amount to increase y by
	 */
	public void changeY(float change) {
		this.y += change;
	}
	
	/**
	 * Overriden fron Object. Gives this Point in
	 * coordinate form e.g. "(0,0)"
	 */
	@Override
	public String toString() {
		return ("(" + getX() + ", " + getY() + ")");
	}
}
