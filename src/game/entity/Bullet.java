package game.entity;

import game.graphics.Renderer;
import game.graphics.Sprite;

/**
 * This class is the Bullet class. The ServerLogic creates this when a Player shoots, pointing in the
 * same direction as the player. Then at each step of the game loop it recieves a forward command. 
 * Collision handling deals with the collision appropriately
 * 
 * @author callum
 *
 */
public class Bullet extends MovableEntity implements KillableEntity{
	
	public Bullet(float width, float height, Point position, float angle) {
		super(width, height, position, angle, 10.0f, 0.0f);
		this.type = "Bullet";

	}
	
	/**
	 * Who shot this Bullet. The Bullets owner does not take damage
	 * from this Bullet
	 */
	public int owner;
	
	/**
	 * How much damage this bullet should do.
	 */
	public int damage=10;

	@Override
	public void draw() {
		Sprite s = new Sprite(this, Renderer.bullet,this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}

	
	@Override
	public void reduceHealth(int amount) {}

	@Override
	public int getHealth() {
		return 0;
	}


}
