package game;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;

public class GameWindow {

    private static Canvas c;
    private static GraphicsContext gc;
    private static Pane root;
    private static Stage windowStage;
    private static JFXButton b1;

    public static void start(Stage stage, ClientLogic client) {
        windowStage = stage;
        root = new Pane();
        StackPane holder = new StackPane();

        c = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        holder.getChildren().add(c);
        root.getChildren().add(holder);
        gc = c.getGraphicsContext2D();
        c.setMouseTransparent(true);
        b1 = new JFXButton("RETURN TO MENU");

        final ToggleButton toggle = new ToggleButton();
        toggle.setLayoutX(10);
        toggle.setLayoutY(10);
        root.getStylesheets().addAll(GameWindow.class.getResource("gui/style.css").toExternalForm());

        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameLoop.stop();
                Main.isRunning = false;
                System.out.println("I am here lalallalallalalalalal");
                backToMenu(windowStage);
            }
        });
        b1.setVisible(false);

        root.getChildren().addAll(b1,toggle);
        b1.setLayoutX((Constants.CANVAS_WIDTH - 230)/2);
        b1.setLayoutY((Constants.CANVAS_HEIGHT - 40)/2);
        BackgroundImage bkg = new BackgroundImage(Renderer.background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        holder.setBackground(new Background(bkg));
        stage.getScene().setRoot(root);

//        stage.show();
        GameLoop.start(gc, stage.getScene(), client);
    }

    public static void toggleBtn(boolean val) {
        b1.setVisible(val);
    }


    public static void backToMenu(Stage stage){
        Main.isRunning = false;
        try {
            Parent r = getMenuScene();
            stage.getScene().setRoot(r);
        } catch(Exception e) {
            System.out.println("Exception when going back to menu");
            e.printStackTrace();
        }

    }

    public static Parent getMenuScene() throws java.io.IOException {
        String sceneFile = "gui/menu3.fxml";
        Parent root = null;
        URL url  = null;
        try
        {
            url  = Main.class.getResource( sceneFile );
            root = FXMLLoader.load( url );
//			System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }
        return root;
    }


    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static Canvas getCanvas() {
        return c;
    }
}
