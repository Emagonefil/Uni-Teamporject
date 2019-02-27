package game.entity.items;
import game.entity.*;

public abstract class Item extends RectangularEntity {
	
	public Item() {
		super(30,30, new Point(0,0));
		this.type = "Item";
	}
	
	public Item(Point location) {
		super(30,30,location);
		this.type = "Item";
	}
	
    public abstract void effect(Player e);
}
