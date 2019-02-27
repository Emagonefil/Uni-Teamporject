package game.entity.items;

import game.entity.*;

public class HealthPickup extends Item {

    public HealthPickup(Point location) {
		super(location);
	}

	public void effect(Player e){
        //TO-DO add a set health method?
        e.reduceHealth(-25);
    }
}
