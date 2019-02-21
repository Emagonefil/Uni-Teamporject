package game.entity.items;
import game.entity.*;

public abstract class Item extends Entity {
    public abstract void effect(Player e);
}
