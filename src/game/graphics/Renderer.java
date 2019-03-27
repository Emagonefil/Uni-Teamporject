package game.graphics;

import game.entity.IEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import java.io.File;
import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;


/**
 * The renderer is concerned with loading images necessary for the game
 * and providing methods to render these on the scree
 */
public class Renderer {

    /** The images used in the game*/
    public static Image loading;
    public static Image icon;
    public static Image tank1;
    public static Image tank2;
    public static Image tank3;
    public static Image tank4;
    public static Image tank5;
    public static Image tank6;
    public static Image tank7;
    public static Image wall;
    public static Image bullet;
    public static Image healthPickup;
    public static Image speedPickup;
    public static Image background;
    public static Image mallPanel;

    // Loading all the images from their paths
    static {
        mallPanel = loadImage("Resources/image/shop.png");
        background = loadImage("Resources/image/floor2.jpg");
        loading = loadImage("Resources/image/loading.gif");
        icon = loadImage("Resources/image/icon.png");
        tank1 = loadImage("Resources/image/tank1.png");
        tank2 = loadImage("Resources/image/tank2.png");
        tank3 = loadImage("Resources/image/tank3.png");
        tank4 = loadImage("Resources/image/tank4.png");
        tank5 = loadImage("Resources/image/tank5.png");
        tank6 = loadImage("Resources/image/tank6.png");
        tank7 = loadImage("Resources/image/tank7.png");
        wall = loadImage("Resources/image/crate6.png");
        bullet = loadImage("Resources/image/bullet.png");
        healthPickup = loadImage("Resources/image/mushroom.png");
        speedPickup = loadImage("Resources/image/star.png");

    }

    /** An array of images containing all the different tank models*/
    public static Image[] tanks = {tank1,tank2,tank3,tank4,tank5,tank6,tank7};

    /**
     * Gets the tank model as an integer and returns that imag for that particular model
     * @param tankModel The desired tank model
     * @return The image for the desired tank model
     */
    public static Image getTank(int tankModel) {
        return tanks[tankModel];
    }

    /**
     * Returns an image object given the image path
     * @param path The path to the image
     * @return The image object
     */
    public static Image loadImage(String path) {

        File file = new File(path);
        String imagePath = file.getAbsolutePath();

        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            imagePath=imagePath.replace('/', File.separatorChar);
            imagePath = imagePath.replace("\\", "\\\\");
        } else {
            // From Linux/Mac to Windows
            imagePath=imagePath.replace('\\', File.separatorChar);

        }
        imagePath="file:"+imagePath;

        return new Image(imagePath);
    }

    /**
     * Draws a sprite in the existing graphics context of the GameWindow
     * @param sprite The sprite to be drawn
     * @param e The respective entity to be drawn
     */
    public static void playAnimation(Sprite sprite, IEntity e) {
        // Gets the existing graphics context from the GameWindow
        GraphicsContext gc = GameWindow.getGraphicsContext();
        // Draws the sprite in that graphics context
        playAnimation(gc,sprite,e);
    }

    /**
     * Draws a sprite for an entity in a specified graphics context at any rotation angle
     * @param gc The graphics context to draw the sprite in
     * @param sprite The sprite to be drawn
     * @param e The respective entity to be drawn
     */
    public static void playAnimation(GraphicsContext gc, Sprite sprite, IEntity e) {
        gc.save();
        // Gets the angle, the x coordinate and the y coordinate of the entity
        // To know where and at what angle to draw the sprite
        rotate(gc, e.getAngle(), e.getPosition().getX(),e.getPosition().getY());
        // Draws the sprite at that position with
        gc.drawImage(sprite.spriteImage,e.getPosition().getX()-(e.getWidth()/2),e.getPosition().getY()-(e.getHeight()/2),
                sprite.width*sprite.scale,sprite.height * sprite.scale);

        gc.restore();
    }

    /**
     * Allows the rotation of sprites within a graphics context
     * @param gc The graphics context to work in
     * @param angle The angle to rotate the image to
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public static void rotate(GraphicsContext gc, double angle, double x, double y) {
        Rotate r = new Rotate(angle,x,y);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());
    }

    /**
     * Draws the shop panel
     * @param point The number of points the user has
     */
    public static void drawMallPanel(int point) {
        // Get the existing graphics context from the GameWindow
        GraphicsContext gc = GameWindow.getGraphicsContext();
        gc.save();
        // Draws the shop panel
        gc.drawImage(mallPanel,CANVAS_WIDTH/2-mallPanel.getWidth()/2, CANVAS_HEIGHT/2-mallPanel.getHeight()/2);
        gc.restore();
        // Displays the number of points the user has available
        gc.setFont(new Font("Press Start 2P", 30));
        gc.setFill(Color.YELLOW);
        gc.fillText(point+"", CANVAS_WIDTH/2-30, CANVAS_HEIGHT/2-10);
    }

}
