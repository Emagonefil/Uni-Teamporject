package game;

import game.controller.InputManager;
import game.entity.Entity;
import game.entity.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import java.util.Iterator;
import java.util.List;

import static game.Constants.*;

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

    public static void start(GraphicsContext gc, Scene s, ClientLogic cl) {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                oldGameTime = currentGameTime;
                currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);

                update(s,cl);
                render();
            }
        }.start();
    }

    public static void update(Scene s, ClientLogic cl) {
        InputManager.handlePlayerMovements(s,cl);
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity entity = it.next();
        }
    }

    public static void render() {
        for (Entity e : entities) {
            e.draw();
        }
    }



}
