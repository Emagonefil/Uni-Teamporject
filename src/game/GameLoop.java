package game;

import game.controller.InputManager;
import game.entity.*;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static game.Constants.CANVAS_HEIGHT;
import static game.Constants.CANVAS_WIDTH;

public class GameLoop {

    private static double currentGameTime;
    private static double oldGameTime;
    private static double deltaTime;
    private final static long startNanoTime = System.nanoTime();
    private static int playerCount = 0;
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
        playerCount = 0;
        Player currentPlayer = (Player) client.getEntityByID(client.id);


        for (Entity e : client.getEntities()) {
            e.draw();

            if(e.type.equals("Player")) {
                gc.setFont(new Font("SERIF", 12));
                if(currentPlayer!=null) {
                    if (e.getId() == currentPlayer.getId()) {
                        gc.setStroke(Color.RED);
                    }
                }

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
                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(e.getPosition().getX()-30 ,e.getPosition().getY() -37 ,62, 6,3,4);
            }


        }
        gc.setFill(Color.BLACK);
        if(currentPlayer!=null) {
            gc.fillText("x: " + currentPlayer.getPosition().getX(), 50,20);
            gc.fillText("y: " + currentPlayer.getPosition().getY(), 50,40);
            if(playerCount == 1) {
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("Press Start 2P", 80));
                gc.fillText("YOU WON!", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
                stopGame(gc);
                return;
            }
        } else {
            gc.setFill(Color.BLACK);
//            System.out.println(Font.getFontNames());
            gc.setFont(new Font("Press Start 2P", 80));
            gc.fillText("GAME OVER", CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2);
            gc.drawImage(Renderer.wall,CANVAS_WIDTH/3.5, CANVAS_HEIGHT/2.2,200,50);
            stopGame(gc);
//            return;
//            gc.drawImage(Renderer.gameOver,CANVAS_WIDTH/3.2, CANVAS_HEIGHT/2.2);
        }


        
    }

    private static void stopGame(GraphicsContext gc) {
//        gc.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent t) {
//                try {
//                    Main.mainStage.getScene().setRoot(Main.getMenuScene());
//                    Main.isRunning = false;
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
    
    private static void drawCorners(GraphicsContext gc,Point[] corners, Color colour) {
    	gc.setFill(colour);
    	gc.fillRect(corners[0].getX(), corners[0].getY(), 3, 3);
        gc.fillRect(corners[1].getX(), corners[1].getY(), 3, 3);
        gc.fillRect(corners[2].getX(), corners[2].getY(), 3, 3);
        gc.fillRect(corners[3].getX(), corners[3].getY(), 3, 3);
    }



}
