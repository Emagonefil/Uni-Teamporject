package game.entity;

import game.graphics.Renderer;
import game.graphics.Sprite;

/**
 * This class is the Bullet class. The ServerLogic creates this when a Player
 * shoots, pointing in the same direction as the player. Then at each step of
 * the game loop it recieves a forward command. Collision handling deals with
 * the collision appropriately
 * 
 * @author callum
 *
 */
public class Bullet extends MovableEntity {

	/**
	 * Creates a bullet object with the given properties
	 *
	 * @param width    The bullet width
	 * @param height   The bullet height
	 * @param position The bullet position
	 * @param angle    The bullet angle
	 */
	public Bullet(float width, float height, Point position, float angle) {
		super(width, height, position, angle, 5.0f, 0.0f);
		this.type = "Bullet";

	}

	/**
	 * Who shot this Bullet. The Bullets owner does not take damage from this Bullet
	 */
	public int owner;

	/**
	 * How much damage this bullet should do.
	 */
	public int damage = 10;

	/**
	 * Draws the bullet sprite at this bullet object position and angle using this
	 * bullet object's dimensions
	 */
	@Override
	public void draw() {
		Sprite s = new Sprite(this, Renderer.bullet, this.getWidth(), this.getHeight(), 1);
		Renderer.playAnimation(s, this);
	}

}
