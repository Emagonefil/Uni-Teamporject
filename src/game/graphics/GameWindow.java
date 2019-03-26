package game.graphics;

import com.jfoenix.controls.JFXButton;
import game.ClientLogic;
import game.Constants;
import game.Main;
import game.audio.AudioPlayer;
import game.entity.Entity;
import game.maps.map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;

/**
 * The Game Window is a class that allows switching from any scene to
 * a game scene. It draws the game map and starts the game loop
 */
public class GameWindow {

    private static Canvas c;
    private static GraphicsContext gc;
    private static Pane root;
    private static Stage windowStage;
    private static JFXButton b1;
    public static int audioCount;

    public static void start(Stage stage, ClientLogic client) {
        audioCount = 0;
        windowStage = stage;
        root = new Pane();
        StackPane holder = new StackPane();

        c = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        holder.getChildren().add(c);
        root.getChildren().add(holder);
        gc = c.getGraphicsContext2D();
        c.setMouseTransparent(true);
        b1 = new JFXButton("RETURN TO MENU");


        final ToggleButton toggleMusic = new ToggleButton();
        toggleMusic.setLayoutX(10);
        toggleMusic.setLayoutY(10);
        final ToggleButton toggleSound = new ToggleButton();
        toggleSound.setLayoutX(60);
        toggleSound.setLayoutY(10);
        root.getStylesheets().addAll(GameWindow.class.getResource("style.css").toExternalForm());

        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                GameLoop.stop();

                Main.isRunning = false;

                backToMenu(windowStage);
                GameWindow.clear();
            }
        });
        b1.setVisible(false);
        Main.soundButtons(toggleMusic, toggleSound);

        toggleSound.setId("toggleSound");
        toggleMusic.setId("toggleMusic");
        root.getChildren().addAll(b1,toggleMusic,toggleSound);
        b1.setLayoutX((Constants.CANVAS_WIDTH - 230)/2);
        b1.setLayoutY((Constants.CANVAS_HEIGHT - 40)/2);
        BackgroundImage bkg = new BackgroundImage(Renderer.background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        holder.setBackground(new Background(bkg));
        stage.getScene().setRoot(root);

        stage.setWidth(Constants.CANVAS_WIDTH);
        stage.setHeight(Constants.CANVAS_HEIGHT);
        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(Constants.CANVAS_WIDTH));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(Constants.CANVAS_HEIGHT));   //must match with the one in the controller
        root.getTransforms().add(scale);
        b1.getTransforms().add(scale);
        stage.setFullScreen(true);

        stage.show();

        GameLoop.start(gc, stage.getScene(), client);
    }



    public static void toggleBtn(boolean val) {
        b1.setVisible(val);
    }

    public static void clear() {
        if (GameLoop.isRunning) {
            GameLoop.stop();
        }
//        c = null;
//        gc = null;
//        windowStage = null;
//        root = null;
//        b1 = null;
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
        String sceneFile = "graphics/fxml/menu3.fxml";
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
