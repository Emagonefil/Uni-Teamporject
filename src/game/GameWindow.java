package game;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameWindow {

    private static Canvas c;
    private static GraphicsContext gc;
    private static Group root;

    public GameWindow(Stage stage, ClientLogic client) {
        root = new Group();
        stage.getScene().setRoot(root);
        c = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        root.getChildren().add(c);
        gc = c.getGraphicsContext2D();
        stage.show();
        GameLoop.start(gc, stage.getScene(), client);
    }

    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static Canvas getCanvas() {
        return c;
    }
}
