package game.entity;

import game.Renderer;
import game.gui.Sprite;

public class Wall extends Entity {

	public Wall(float width, float height, Point position) {
		super(width, height, position, 0.0f);
		this.type="Wall";
	}
	public Wall(float width, float height, Point position,int id) {
		super(width, height, position, 0.0f);
		this.type="Wall";
		this.id=id;
	}


	public void draw() {
		Sprite s = new Sprite(this,Renderer.wall,this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}
	
}
