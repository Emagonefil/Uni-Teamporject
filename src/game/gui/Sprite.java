package game.gui;

import game.entity.Entity;
import javafx.scene.image.Image;

public class Sprite {

    public Image spriteImg;
    public double speed;
    public int numberOfFrames;
    public double width;
    public double height;
    public int scale;
    public int actualSize;
    public boolean reversePlay;

    public boolean hasImg;

    public Entity entity;

    public Sprite(Entity e , int actualSize, double speed, Image img, int numberOfFrames, double width, double height,
                  int scale, boolean leftToRight) {
        super();
        this.actualSize = actualSize;
        this.speed = speed;
        this.spriteImg = img;
        this.numberOfFrames = numberOfFrames;
        this.width = width;
        this.height = height;
        this.scale = scale;
        reversePlay = leftToRight;
        this.entity = e;
    }

    public float getXPosition() {
        return entity.getPosition().getX();
    }

    public float getYPosition() {
        return entity.getPosition().getY();
    }

}
