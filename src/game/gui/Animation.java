package game.gui;

import game.entity.Entity;
import javafx.scene.image.Image;

public class Animation {

    Sprite sprite;
    double playSpeed;

    public Animation(Entity e, Image image) {
        playSpeed = 0.1;
        sprite = new Sprite(e, image, 30, 40,2);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
