package game.entity;

import game.Renderer;
import game.gui.Animation;
import game.gui.Sprite;

public class Bullet extends MovableRectangularEntity implements KillableEntity{
	// for drawing
	private Sprite currentSprite;

	public Bullet(float width, float height, Point position) {
		super(width, height, position);
		this.type="Bullet";
		this.setSpeed(0.1f);

		// for drawing
		currentSprite = new Sprite(this,Renderer.bullet,10,10,2);
	}
	
	public Bullet(float width, float height, Point position, float angle) {
		super(width, height, position, angle);

		// for drawing
		currentSprite = new Sprite(this,Renderer.bullet,10,10,2);

	}
	public int damage=10;
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceHealth(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	// draw the bullet on screen
	@Override
	public void draw() {
		Renderer.playAnimation(currentSprite);
	}

}
