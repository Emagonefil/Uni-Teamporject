package game;

import game.controller.InputManager;
import game.entity.*;
import game.entity.items.Item;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Iterator;
import java.util.List;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;

public class GameLoop {

    private static double currentGameTime;
    private static double oldGameTime;
    private static double deltaTime;
    private final static long startNanoTime = System.nanoTime();
//    private static List<Entity> entities;

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
                    gc.setFont(new Font("SERIF", 12));
                    gc.strokeText(""+e.getId(),e.getPosition().getX() -14 ,e.getPosition().getY() - 42);

                    double health = (((Player) e).getHealth()/1.6);
                    if(health < 20) {
                        gc.setFill(Color.RED);
                    } else if(health < 40) {
                        gc.setFill(Color.YELLOW);
                    } else if(health > 40){
                        gc.setFill(Color.DARKGREEN);
                    }
                    gc.fillRoundRect(e.getPosition().getX()-30 ,e.getPosition().getY() -37 ,health, 6,3,4);
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
                case "Item":
                	currentSprite = new Sprite(e,Renderer.healthPickup, ((Item) e).getWidth(), ((Item) e).getHeight(),1);
                	Renderer.playAnimation(currentSprite, (IRectangularEntity) e);
                	break;
            }


        }

        Player currentPlayer = (Player) client.getEntityByID(client.id);
        gc.setFill(Color.BLACK);
        if(currentPlayer!=null) {
            gc.fillText("x: " + currentPlayer.getPosition().getX(), 50,20);
            gc.fillText("y: " + currentPlayer.getPosition().getY(), 50,40);
        } else {
            gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
            gc.setFont(new Font("Press Start 2P", 80));
            gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
//            gc.drawImage(Renderer.gameOver,CANVAS_WIDTH/3.2, CANVAS_HEIGHT/2.2);
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
