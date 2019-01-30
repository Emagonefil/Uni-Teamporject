package game.entity;

import game.Renderer;
import game.gui.Animations;
import game.gui.Sprite;

public class Player extends MovableRectangularEntity implements KillableEntity {

	// for drawing
	private Sprite currentSprite;
	private Animations playerAnimations;

	public Player(float width, float height, Point position) {
		super(width, height, position);
		this.health = 100;

		// for drawing
		playerAnimations = new Animations(this,Renderer.getHeroImg());
		currentSprite = playerAnimations.getSprite();
	}

	private int health;
	
	
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceHealth(int amount) {
		this.health -= amount;
	}

	@Override
	public int getHealth(int amount) {
		return this.health;
	}


	public void setSprite(Sprite s) {
		currentSprite = s;
	}

	@Override
	public void draw() {
		Renderer.playAnimation(currentSprite);
	}
}
