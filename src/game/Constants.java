package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.awt.*;

/**
 * A class that stores important constants for the game
 */
public class Constants {

//    static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    public static double CANVAS_WIDTH = screenBounds.getWidth();
    public static double CANVAS_HEIGHT = screenBounds.getHeight();
//    public static int SCENE_WIDTH = gd.getDisplayMode().getWidth();
//    public static int SCENE_HEIGHT = gd.getDisplayMode().getHeight();
//    public static int CANVAS_WIDTH = 640;
//    public static int CANVAS_HEIGHT = 640;
    public static String GAME_NAME = "Tanks";
    public static String GAME_VERSION = "  v 0.1";
    public static Color BACKGROUND_COLOR = Color.WHITE;

    public static enum GameStatus{
        Running,Paused,GameOver
    }
}
