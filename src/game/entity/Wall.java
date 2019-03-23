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
