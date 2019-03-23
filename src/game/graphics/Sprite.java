package game.graphics;

import game.entity.Entity;
import javafx.scene.image.Image;

/**
 * A sprite allows easy manipulation of images and is used to draw these in the renderer
 */
public class Sprite {

    public Image spriteImage;
    public double width;
    public double height;
    public double scale;

    public boolean hasImg;

    public Entity entity;

    public Sprite(Entity e, Image img, double width, double height, double scale) {
        super();
        this.spriteImage = img;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.entity = e;
    }

    public float getXPosition() {
        return entity.getPosition().getX();
    }

    public float getYPosition() {
        return entity.getPosition().getY();
    }

}
