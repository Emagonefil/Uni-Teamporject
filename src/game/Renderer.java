package game;

import game.gui.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Rotate;

import java.io.File;



public class Renderer {

    public static Image hero;
    public static Image tank;
    static Image ghost1;
    static Image ghost2;
    static Image skull1;
    static Image skull2;
    static Image snake1;
    static Image snake2;
    public static Image wall;
    public static Image bullet;
    static {
        hero = loadImage("Resources/image/chubbyZombie.png");
        tank = loadImage("Resources/image/Tank.png");
        wall = loadImage("Resources/image/terrain4.png");
//        ghost1 = loadImage("Resources/image/ghost1.png");
//        ghost2 = loadImage("Resources/image/ghost2.png");
        skull1 = loadImage("Resources/image/skull1.png");
//        skull2 = loadImage("Resources/image/skull2.png");
//        snake1 = loadImage("Resources/image/snake1.png");
//        snake2 = loadImage("Resources/image/snake2.png");
        bullet = loadImage("Resources/image/bullet.png");
    }

    // return an Image object given the image path
    public static Image loadImage(String path) {
        File file = new File(path);
//        System.out.println("Loading Sprite sheet " + file.exists());
        String imagePath = file.getAbsolutePath();
//        System.out.println("Before Imagepath " + imagePath);
        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            imagePath=imagePath.replace('/', File.separatorChar);
            imagePath = imagePath.replace("\\", "\\\\");
        } else {
            // From Linux/Mac to Windows
            imagePath=imagePath.replace('\\', File.separatorChar);

        }
        imagePath="file:"+imagePath;
//        System.out.println("After Imagepath " + imagePath);

        return new Image(imagePath);
    }

    public static Image getHeroImage() {
        return hero;
    }

    public static void playAnimation(Sprite sprite, double angle,  float x, float y) {
        GraphicsContext gc = GameWindow.getGraphicsContext();
        playAnimation(gc, sprite.spriteImage, angle, x, y, sprite.width * sprite.scale, sprite.height * sprite.scale);
    }

    // draw an image on the screen given all relevant details
    public static void playAnimation(GraphicsContext gc, Image img, double angle, float x, float y, double width, double height) {
        drawRotatedImage(gc,img,angle,x,y);
    }

    public static void drawRotatedImage(GraphicsContext gc, Image img, double angle, float x, float y) {
        gc.save();
        rotate(gc, angle, x + img.getWidth() / 2, y + img.getHeight() / 2);
        gc.drawImage(img,x,y);
        gc.restore();
    }

    public static void rotate(GraphicsContext gc, double angle, double x, double y) {
        Rotate r = new Rotate(angle,x,y);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());
    }
}
