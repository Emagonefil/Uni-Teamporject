package game.entity;

import game.graphics.Renderer;
import game.graphics.Sprite;

/**
 * This class extends Entity and does not add any extra functionality
 * it simply sets type to be "Wall" and the correct wall sprite within
 * the constructor
 * 
 * @author callum
 *
 */
public class Wall extends Entity {

	/**
	 * Creates a wall with the given property values
	 *
	 * @param width The width of this wall
	 * @param height The height of this wall
	 * @param position The position of this wall
	 */
	public Wall(float width, float height, Point position) {
		super(width, height, position, 0.0f);
		this.type="Wall";
	}

	/**
	 * Creates a wall with the given property values
	 *
	 * @param width The width of the wall
	 * @param height the height of the wall
	 * @param position The position of the wall on the map
	 * @param id The id of this wall object
	 */
	public Wall(float width, float height, Point position,int id) {
		super(width, height, position, 0.0f);
		this.type="Wall";
		this.id=id;
	}

	/**
	 * Draws the sprite of the wall at this wall's position
	 * with the walls dimensions
	 */
	public void draw() {
		Sprite s = new Sprite(this,Renderer.wall,this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}
	
}
