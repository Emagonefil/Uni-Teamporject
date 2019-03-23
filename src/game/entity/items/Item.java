package game.entity.items;
import game.entity.*;

public abstract class Item extends Entity {
	
	public Item(Point location) {
		super(30,30,location, 0.0f);
		this.type = "Item";
	}
	
    public abstract void effect(Player e);
}
