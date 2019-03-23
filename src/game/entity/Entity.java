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
	 * left corner of the map/screen
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
	 * This creates an entity with the given position and sets the angle to 0 degrees
	 * @param position The position for the centre of this new entity.
	 */
	public Entity(Point position) {
		this.position = position;
		this.angle = 0.0f;
	}

	/**
	 * This creates an entity with the given position and angle(in degrees)
	 * 
	 * @param position The starting position for the centre of this entity.
	 * @param angle The angle at which this new entity is facing (in degrees)
	 */
	public Entity(Point position, float angle) {
		this.position = position;
		this.angle = angle;
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
	 * sets the position of the centre of this entity to a new location
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * Returns this entities unique id
	 */
	public int getId() {
		return id;
	}

	public abstract void draw();

}
