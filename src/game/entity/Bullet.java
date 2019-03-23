package game.entity;

import game.Renderer;
import game.gui.Sprite;

public class Bullet extends MovableEntity implements KillableEntity{
	
	public Bullet(float width, float height, Point position, float angle) {
		super(width, height, position, angle, 10.0f, 0.0f);
		this.type = "Bullet";

	}
	public int owner;
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
