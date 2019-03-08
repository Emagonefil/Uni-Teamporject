package game.entity;

import game.Renderer;
import game.gui.Sprite;

public class Wall extends RectangularEntity {

	public Wall(float width, float height, Point position) {
		super(width, height, position);
		// TODO Auto-generated constructor stub
		this.type="Wall";

	}


	public void draw() {
		this.setImage(Renderer.wall);
		Sprite s = new Sprite(this, this.getImage(),this.getWidth(),this.getHeight(),1.2);
		Renderer.playAnimation(s,this);
	}
	
}
