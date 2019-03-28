package game.graphics;

import game.entity.Entity;
import javafx.scene.image.Image;

/**
 * A sprite allows easy manipulation of images and is used to draw these in the renderer
 */
public class Sprite {

    /** The image of the sprite */
    public Image spriteImage;
    /** The width of the sprite */
    public double width;
    /** The height of the sprite */
    public double height;
    /** The scale of the sprite */
    public double scale;
    /** The entity for the sprite */
    public Entity entity;

    /**
     * The constructor that creates a sprite with the given attributes.
     * @param e The entity of the sprite
     * @param img The image of the sprite
     * @param width The width of the sprite
     * @param height The height of the sprite
     * @param scale The scale of the sprite
     */
    public Sprite(Entity e, Image img, double width, double height, double scale) {
        super();
        this.spriteImage = img;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.entity = e;
    }

    /**
     * Returns the x coordinate of the position of the entity within the game Map
     * @return The x coordinate of the entity
     */
    public float getXPosition() {
        return entity.getPosition().getX();
    }

    /**
     * Returns the y coordinate of the position of the entity within the game Map
     * @return The y coordinate of the entity
     */
    public float getYPosition() {
        return entity.getPosition().getY();
    }

    /**
     * Returns the entity this sprite portrays
     * @return The entity for this sprite
     */
    public Entity getEntity() {
        return entity;
    }

}
