package game.entity.items;

import game.graphics.Renderer;
import game.entity.*;
import game.graphics.Sprite;

public class SpeedPickup extends Item {

    public SpeedPickup(Point location) {
  		super(location);
	}

	public void effect(Player e){
		e.setSpeed(e.getSpeed()+0.75f);
		e.setRotationSpeed(e.getRotationSpeed()+0.5f);
    }
	
	@Override
	public void draw() {
		Sprite s = new Sprite(this, Renderer.speedPickup,this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}
}
