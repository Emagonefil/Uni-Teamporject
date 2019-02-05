package game;

import game.controller.InputManager;
import game.entity.Bullet;
import game.entity.Entity;
import game.entity.Player;
import game.entity.Point;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

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
                render(client);
            }
        }.start();
    }

//    public static void update(Scene scene, ClientLogic client) {
//        InputManager.handlePlayerMovements(scene,client);
//    }

    public static void render(ClientLogic client) {
        Sprite currentSprite = null;
        for (Entity e : client.getEntities()) {

            switch (e.type){

                case "Player":currentSprite = new Sprite(e,Renderer.tank,40,50,2);break;
                case "Wall": currentSprite = new Sprite(e,Renderer.wall,40,40,1);break;
                case "Bullet":currentSprite = new Sprite(e,Renderer.bullet,5,5,2);break;
            }

            Renderer.playAnimation(currentSprite,e.getAngle(), e.getPosition().getX(),e.getPosition().getY());
        }

    }


}