package game.entity;

import game.Renderer;
import game.gui.Animation;
import game.gui.Sprite;

public class Bullet extends MovableRectangularEntity implements KillableEntity{

	public Bullet(float width, float height, Point position) {
		super(width, height, position);
		this.type="Bullet";
		this.setSpeed(10f);

	}
	
	public Bullet(float width, float height, Point position, float angle) {
		super(width, height, position, angle);


	}
	public int owner;
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

	@Override
	public void draw() {
		Sprite s = new Sprite(this, Renderer.bullet,this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}


}
