package game.graphics;

import game.entity.IEntity;
import javafx.geometry.Rectangle2D;
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

    public static Image gameOver;
    public static Image loading;
    public static Image volumeOn;
    public static Image volumeOff;
    public static Image musicOn;
    public static Image musicOff;
    public static Image hero;
    public static Image tank;
    public static Image tank1;
    public static Image tank2;
    public static Image tank3;
    public static Image tank4;
    public static Image tank5;
    public static Image tank6;
    public static Image tank7;
    static Image ghost1;
    static Image ghost2;
    static Image skull1;
    static Image skull2;
    static Image snake1;
    static Image snake2;
    public static Image wall;
    public static Image bullet;
    public static Image healthPickup;
    public static Image speedPickup;
    public static Image background;
    /***********新增star************/
    public static Image mallPanel;
    /***********新增end************/
    static {
        /***********新增star************/
        mallPanel = loadImage("Resources/image/shop.png");
        /***********新增end************/
        background = loadImage("Resources/image/bkg.png");
        gameOver = loadImage("Resources/image/game-over.gif");
        loading = loadImage("Resources/image/loading.gif");
        volumeOn = loadImage("Resources/image/v-on.png");
        volumeOff = loadImage("Resources/image/v-off.png");
        musicOn = loadImage("Resources/image/m-on.png");
        musicOff = loadImage("Resources/image/m-off.png");
        hero = loadImage("Resources/image/chubbyZombie.png");
        tank = loadImage("Resources/image/Tank.png");
        tank1 = loadImage("Resources/image/tank1.png");
        tank2 = loadImage("Resources/image/tank2.png");
        tank3 = loadImage("Resources/image/tank3.png");
        tank4 = loadImage("Resources/image/tank4.png");
        tank5 = loadImage("Resources/image/tank5.png");
        tank6 = loadImage("Resources/image/tank6.png");
        tank7 = loadImage("Resources/image/tank7.png");
        wall = loadImage("Resources/image/crate6.png");
//        ghost1 = loadImage("Resources/image/ghost1.png");
//        ghost2 = loadImage("Resources/image/ghost2.png");
        skull1 = loadImage("Resources/image/skull1.png");
//        skull2 = loadImage("Resources/image/skull2.png");
//        snake1 = loadImage("Resources/image/snake1.png");
//        snake2 = loadImage("Resources/image/snake2.png");andreea
        bullet = loadImage("Resources/image/bullet.png");
        healthPickup = loadImage("Resources/image/mushroom.png");
        speedPickup = loadImage("Resources/image/star.png");

    }

    public static Image[] tanks = {tank1,tank2,tank3,tank4,tank5,tank6,tank7};

    public static Image getTank(int tankModel) {
        return tanks[tankModel];
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
    
    public static void playAnimation(Sprite sprite, IEntity e) {
        GraphicsContext gc = GameWindow.getGraphicsContext();
        gc.save();
        rotate(gc, e.getAngle(), e.getPosition().getX(),e.getPosition().getY());
        gc.drawImage(sprite.spriteImage,e.getPosition().getX()-(e.getWidth()/2),e.getPosition().getY()-(e.getHeight()/2),
        		sprite.width*sprite.scale,sprite.height * sprite.scale);

        gc.restore();
    }
    public static void playAnimation(GraphicsContext gc, Sprite sprite, IEntity e) {
        gc.save();
        rotate(gc, e.getAngle(), e.getPosition().getX(),e.getPosition().getY());
        gc.drawImage(sprite.spriteImage,e.getPosition().getX()-(e.getWidth()/2),e.getPosition().getY()-(e.getHeight()/2),
                sprite.width*sprite.scale,sprite.height * sprite.scale);

        gc.restore();
    }

    public static void rotate(GraphicsContext gc, double angle, double x, double y) {
        Rotate r = new Rotate(angle,x,y);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());
    }
    //draw shop panel
    public static void drawMallPanel(int point) {
        GraphicsContext gc = GameWindow.getGraphicsContext();
        gc.save();
        gc.drawImage(mallPanel,CANVAS_WIDTH/2-mallPanel.getWidth()/2, CANVAS_HEIGHT/2-mallPanel.getHeight()/2);
        gc.restore();
        gc.setFont(new Font("Press Start 2P", 30));
        gc.setFill(Color.YELLOW);
        gc.fillText(point+"", CANVAS_WIDTH/2-30, CANVAS_HEIGHT/2-10);
    }

}
