package game.graphics;

import com.jfoenix.controls.JFXButton;
import game.ClientLogic;
import game.Constants;
import game.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import java.net.URL;

/**
 * The Game Window is a class that allows switching from any scene to
 * a game scene. It draws the game Map and starts the game loop
 *
 * @author Andreea
 */
public class GameWindow {

    /** The canvas where the game is rendered */
    private static Canvas c;
    /** The graphics context for the canvas */
    private static GraphicsContext gc;
    /** The root node of the scene that */
    private static Pane root;
    /** The application stage */
    private static Stage windowStage;
    /** The return to menu button */
    private static JFXButton returnToMenu;
    /** The counter that track how many times the audio at the end of the game has played */
    public static int audioCount;
    /** The flag that tracks if the player is connected to the internet */
    public static boolean connection = true;


    /**
     * Starts a new game window that renders the maps and entities
     * @param stage The stage where to load the game window
     * @param client The client that runs the game
     */
    public static void start(Stage stage, ClientLogic client) {
        // Initialise audioCount to 0
        audioCount = 0;
        // Set the windowStage to the stage parameter
        windowStage = stage;
        // Initialise the pane
        root = new Pane();
        // Create a holder
        StackPane holder = new StackPane();

        // Initialise the canvas with the game dimensions
        c = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        holder.getChildren().add(c);
        root.getChildren().add(holder);
        gc = c.getGraphicsContext2D();
        c.setMouseTransparent(true);

        // Add style sheet to the pane
        root.getStylesheets().addAll(GameWindow.class.getResource("style.css").toExternalForm());

        // Initialise the return to menu button
        returnToMenu = new JFXButton("RETURN TO MENU");
        returnToMenu.setLayoutX((Constants.CANVAS_WIDTH - 230)/2);
        returnToMenu.setLayoutY((Constants.CANVAS_HEIGHT - 40)/2);
        returnToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Stop the game and the server
                Main.isRunning = false;
                // Change to the menu screen
                backToMenu(windowStage);
                // Clear the game window
                GameWindow.clear();
            }
        });
        // Hide the return to menu button until the player dies or wins
        returnToMenu.setVisible(false);

        // Add the elements to the screen
        root.getChildren().addAll(returnToMenu,Main.toggleMusic,Main.toggleSound);

        // Load and set the game background image, meaning the terrain
        BackgroundImage bkg = new BackgroundImage(Renderer.background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        holder.setBackground(new Background(bkg));
        stage.getScene().setRoot(root);

        // Make the application resizable so it adjusts to different screens
        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(Constants.CANVAS_WIDTH));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(Constants.CANVAS_HEIGHT));   //must match with the one in the controller
        root.getTransforms().add(scale);
        returnToMenu.getTransforms().add(scale);

        // Make the game fullscreen
        stage.setFullScreen(true);

        // Display the game
        stage.show();

        // Start the game loop that continuously renders the game
        GameLoop.start(gc,client);
    }

    /**
     * Sets the visibility of the return to menu button to the given boolean
     * @param val The boolean value to set the visibility of the button
     */
    public static void toggleBtn(boolean val) {
        returnToMenu.setVisible(val);
    }

    /**
     * Clears the game window and stops the game loop
     */
    public static void clear() {
        if (GameLoop.isRunning) {
            GameLoop.stop();
        }
    }

    /**
     * Changes the screen of the stage to the menu screen
     * @param stage The stage where the new screen should be displayed
     */
    public static void backToMenu(Stage stage){
        // Stop the game and the server
        Main.isRunning = false;
        try {
            // Get the menu screen
            Parent r = getMenuScene();
            // Replace the current screen with the menu screen
            stage.getScene().setRoot(r);
        } catch(Exception e) {
            System.out.println("Exception when going back to menu");
            e.printStackTrace();
        }
    }

    /**
     * Returns the menu screen node to be set to a stage
     * @return The menu screen node
     * @throws java.io.IOException If the file fails to load or presents mistakes
     */
    public static Parent getMenuScene() throws java.io.IOException {
        // Path to the fxml file containing the page content
        String sceneFile = "graphics/fxml/menu3.fxml";
        Parent root = null;
        URL url  = null;
        try
        {
            // Load the content from the file
            url  = Main.class.getResource( sceneFile );
            root = FXMLLoader.load( url );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }
        // Return the node
        return root;
    }

    /**
     * Returns the graphics context of the game window
     * @return The graphics context of the game window
     */
    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    /**
     * Returns the canvas of the game window
     * @return The canvas of the game window
     */
    public static Canvas getCanvas() {
        return c;
    }
}
