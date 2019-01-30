package game.gui;

import game.Constants;
import game.Renderer;
import game.entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Animations {
    Sprite sprite;
    double playSpeed;

    public Animations(Entity e, Image img) {
        playSpeed = 0.1;
        sprite = new Sprite(e,30, playSpeed, img, 3, 30,40,2,false);

    }

    public Sprite getSprite() {
        return sprite;
    }


}
