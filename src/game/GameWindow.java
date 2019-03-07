package game;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow {

    private static Canvas c;
    private static GraphicsContext gc;
    private static Pane root;

    public GameWindow(Stage stage, ClientLogic client) {
        root = new Pane();
        StackPane holder = new StackPane();

        c = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        holder.getChildren().add(c);
        root.getChildren().add(holder);
        gc = c.getGraphicsContext2D();

        holder.setId("gameWindow");
        holder.getStylesheets().addAll(this.getClass().getResource("gui/style.css").toExternalForm());

        stage.getScene().setRoot(root);

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
