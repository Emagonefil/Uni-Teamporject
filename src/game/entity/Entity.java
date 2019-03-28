package game.entity;

import java.io.Serializable;

/**
 * This is the base class for all entities within the game (Player, Wall, Bullet etc.)
 * objects of type Entity should not be created rather, its sub entities should be used.
 * 
 * 
 * @author callum
 */
public abstract class Entity implements IEntity, Serializable {
	/**
	 * The position of the centre of this entity relative to the bottom
	 * left corner of the Map/screen
	 */
	Point position;
	
	/**
	 * The angle in which the Entity is facing in degrees. 0 degrees is
	 * pointing in the positive y direction.
	 */
	float angle;
	
	/**
	 * All entities have a unique id which is used in order to identify each entity.
	 */
	public int id;
	
	/**
	 * This is a string which is used by the collision handling in order to determine
	 * the intended affect of a collision. This may be "Player", "Wall", "Bullet" or "Item"
	 * and is set within the relevant classes constructor.
	 */
	public String type;
	
	/**
	 * The width of the Entity, in the scale of the grid position
	 */
	private float width;
	
	/**
	 * The height of the Entity, in the scale of the grid position
	 */
	private float height;

	/**
	 * This creates an entity with the given width, height, position and angle(in degrees)
	 * 
	 * @param width The width of the new Entity.
	 * @param height The height of the new Entity.
	 * @param position The starting position for the centre of this entity.
	 * @param angle The angle at which this new entity is facing (in degrees)
	 */
	public Entity(float width, float height, Point position, float angle) {
		this.position = position;
		this.angle = angle;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the value of angle for this entity
	 */
	public float getAngle() {
		return angle;
	}
	
	/**
	 * sets the angle for this entity to the given value
	 * @param angle The new angle which this entity is facing (in degrees)
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * returns the position of the centre of this entity.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * sets the position of the centre of this entity to the given location
	 * 
	 * @param position The new position for the centre of this Entity
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * This function takes a corner and rotates it about the centre of this
	 * entity by the given angle in degrees.
	 * 
	 * @param corner The unrotated corner of this entity
	 * @param angle The amount of degrees by which this corner is to be rotated about the centre
	 * @return
	 */
	private Point rotateCorner(Point corner, float angle) {
		float radAngle = (float) Math.toRadians(angle);
		Point center = this.position;
		
		float tempX = corner.getX()-center.getX();
		float tempY = corner.getY()-center.getY();
		
		float rotX = (float) (tempX * Math.cos(radAngle) - tempY * Math.sin(radAngle));
		float rotY = (float) (tempX*Math.sin(radAngle) + tempY * Math.cos(radAngle));
		
		Point result = new Point(rotX + center.getX(), rotY + center.getY());
		return result;
	}
	
	/**
	 * This uses this entities width, height, position and angle in order to return an array
	 * containing the coordinates of this entities corners in the order: top-left, top-right,
	 * bottom-right, bottom-left. This order is chosen for ease of use with the collision 
	 * detection where a 'circular' order is required.
	 * 
	 * @return The coordinates of this entities corners in the order: top-left, top-right,
	 * bottom-right, bottom-left
	 */
	public Point[] getCorners() {
		Point[] corners = new Point[4];
		float point1X = this.position.getX() - (this.width/2);
		float point1Y = this.position.getY() + (this.height/2);
		corners[0] = rotateCorner(new Point(point1X, point1Y), this.angle);
		
		float point2X = this.position.getX() + (this.width/2);
		float point2Y = this.position.getY() + (this.height/2);
		corners[1] = rotateCorner(new Point(point2X, point2Y), this.angle);
		
		float point4X = this.position.getX() + (this.width/2);
		float point4Y = this.position.getY() - (this.height/2);
		corners[2] = rotateCorner(new Point(point4X, point4Y), this.angle);
		
		float point3X = this.position.getX() - (this.width/2);
		float point3Y = this.position.getY() - (this.height/2);
		corners[3] = rotateCorner(new Point(point3X, point3Y), this.angle);
		
		return corners;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}

	/**
	 * Returns this entities unique id
	 */
	public int getId() {
		return id;
	}

	/**
	 * This is overwritten by each non-abstract subclass to have a 
	 */
	public abstract void draw();

}
