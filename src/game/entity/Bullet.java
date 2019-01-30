package game.entity;

import game.Renderer;
import game.gui.Animations;
import game.gui.Sprite;

public class Bullet extends MovableRectangularEntity implements KillableEntity{

	private Sprite currentSprite;
	private Animations animations;

	public Bullet(float width, float height, Point position) {
		super(width, height, position);
		// for drawing
		animations = new Animations(this,Renderer.getBulletImg());
		currentSprite = animations.getSprite();
	}
	
	public Bullet(float width, float height, Point position, float angle) {
		super(width, height, position, angle);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceHealth(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth(int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void draw() {
		Renderer.playAnimation(currentSprite);
	}

}
