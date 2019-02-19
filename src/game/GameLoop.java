package game;

import game.controller.InputManager;
import game.entity.*;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.List;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;

public class GameLoop {

    private static double currentGameTime;
    private static double oldGameTime;
    private static double deltaTime;
    private final static long startNanoTime = System.nanoTime();
    private static List<Entity> entities;

    public static double getCurrentGameTime() {
        return currentGameTime;
    }

    public static double getDeltaTime() {
        return deltaTime*100;
    }

    public static void start(GraphicsContext gc, Scene scene, ClientLogic client) {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                oldGameTime = currentGameTime;
                currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);
                render(client, gc);
            }
        }.start();
    }

//    public static void update(Scene scene, ClientLogic client) {
//        InputManager.handlePlayerMovements(scene,client);
//    }

    public static void render(ClientLogic client, GraphicsContext gc) {
        Sprite currentSprite = null;
        for (Entity e : client.getEntities()) {

            switch (e.type) {

                case "Player":
                    currentSprite = new Sprite(e, Renderer.tank, ((Player) e).getWidth(), ((Player) e).getHeight(), 1);
                    Renderer.playAnimation(currentSprite, (IRectangularEntity) e);
                    //drawCorners(gc, ((IRectangularEntity) e).getCorners(), Color.GREEN);
                    gc.setFill(Color.GREEN);
                    gc.strokeText("name",e.getPosition().getX() ,e.getPosition().getY() - 15);
                    gc.fillRect(e.getPosition().getX() ,e.getPosition().getY() -10 ,60, 6);
                    break;
                case "Wall":
                    currentSprite = new Sprite(e, Renderer.wall, ((Wall) e).getWidth(), ((Wall) e).getHeight(), 1);
                    Renderer.playAnimation(currentSprite, (IRectangularEntity) e);
                    //drawCorners(gc, ((IRectangularEntity) e).getCorners(), Color.RED);
                    gc.setFill(Color.BLACK);
                    gc.getPixelWriter();

                    //gc.fillRect(e.getPosition().getX(),e.getPosition().getY(),((Wall) e).getWidth(), ((Wall) e).getHeight());

                    break;
                case "Bullet":
                    currentSprite = new Sprite(e, Renderer.bullet, ((Bullet) e).getWidth(), ((Bullet) e).getHeight(), 2);
                    Renderer.playAnimation(currentSprite, (IRectangularEntity) e);
                    break;
            }
//            if (currentSprite != null) {
//                Renderer.playAnimation(currentSprite, e.getAngle(), e.getPosition().getX(), e.getPosition().getY());
//                currentSprite = null;
//            }
        }
        
    }
    
    private static void drawCorners(GraphicsContext gc,Point[] corners, Color colour) {
    	gc.setFill(colour);
    	gc.fillRect(corners[0].getX(), corners[0].getY(), 3, 3);
        gc.fillRect(corners[1].getX(), corners[1].getY(), 3, 3);
        gc.fillRect(corners[2].getX(), corners[2].getY(), 3, 3);
        gc.fillRect(corners[3].getX(), corners[3].getY(), 3, 3);
    }


}
