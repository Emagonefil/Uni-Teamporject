package game.gui;

import game.ClientLogic;
import game.GameLoop;
import game.ServerLogic;
import game.entity.Entity;
import game.entity.Player;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.List;

import static game.Constants.*;

public class GameWindow {

    private static Canvas c;
    private static GraphicsContext gc;
    private static Group root;

    public GameWindow(Stage primaryStage, ClientLogic cl) {
        root = new Group();
        primaryStage.getScene().setRoot(root);
        c = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.getChildren().add(c);
        gc = c.getGraphicsContext2D();
        primaryStage.show();
        GameLoop.start(gc, primaryStage.getScene(), cl);


//
//        List<Entity> map=cl.getEntities();
//        for(int i=0;i<map.size();i++){
//            Entity e=map.get(i);
//            e.getPosition();
//        }

    }

    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static Canvas getCanvas() {
        return c;
    }

}