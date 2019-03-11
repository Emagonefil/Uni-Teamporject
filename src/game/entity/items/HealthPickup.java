package game.entity.items;

import game.Renderer;
import game.entity.*;
import game.gui.Sprite;

public class HealthPickup extends Item {

    public HealthPickup(Point location) {
  		super(location);
  		
	}

	public void effect(Player e){
        //TO-DO add a set health method?
        e.reduceHealth(-25);
    }
	
	@Override
	public void draw() {
		Sprite s = new Sprite(this, Renderer.healthPickup,this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}
}
