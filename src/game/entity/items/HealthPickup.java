package game.entity.items;

import game.entity.*;

public class HealthPickup extends Item {

    public void effect(Player e){
        //TO-DO add a set health method?
        e.reduceHealth(-10);
    }
}
