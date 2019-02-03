package game;

import com.sun.prism.Graphics;
import game.gui.GameWindow;
import game.gui.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.File;



public class Renderer {

    static Image hero;
    static Image ghost1;
    static Image ghost2;
    static Image skull1;
    static Image skull2;
    static Image snake1;
    static Image snake2;
    static Image bullet;
    static {
        hero = loadImage("Resources/image/chubbyZombie.png");
//        ghost1 = loadImage("Resources/img/ghost1.png");
//        ghost2 = loadImage("Resources/img/ghost2.png");
//        skull1 = loadImage("Resources/img/skull1.png");
//        skull2 = loadImage("Resources/img/skull2.png");
//        snake1 = loadImage("Resources/img/snake1.png");
//        snake2 = loadImage("Resources/img/snake2.png");
        bullet = loadImage("Resources/image/bullet.png");
    }

    public static Image loadImage(String path) {
        File file = new File(path);
        System.out.println("Loading Sprite sheet " + file.exists());
        String imagePath = file.getAbsolutePath();
        System.out.println("Before Imagepath " + imagePath);

        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            imagePath = imagePath.replace('/', File.separatorChar);
            imagePath = imagePath.replace("\\", "\\\\");
        } else {
            // From Linux/Mac to Windows
            imagePath = imagePath.replace('\\', File.separatorChar);

        }
        imagePath="file:"+imagePath;
        System.out.println("After Imagepath " + imagePath);

        return new Image(imagePath);
    }


    public static Image getHeroImg() {
        return hero;
    }

    public static Image getBulletImg() {
        return bullet;
    }

    public static void playAnimation(Sprite sprite) {
        GraphicsContext gc = GameWindow.getGraphicsContext();
        playAnimation(gc, sprite.spriteImg, sprite.getYPosition(), sprite.getYPosition(), sprite.width * sprite.scale, sprite.height*sprite.scale);
    }

    public static void playAnimation(GraphicsContext gc, Image img, float x, float y, double width, double height) {
        gc.drawImage(img,x,y,width,height);
    }
}
