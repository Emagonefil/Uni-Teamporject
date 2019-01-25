package game;

import javafx.scene.paint.Color;

public class Constants {

    public static int SCENE_WIDTH = 640;
    public static int SCENE_HEIGHT = 640;
    public static int CANVAS_WIDTH = 640;
    public static int CANVAS_HEIGHT = 640;
    public static String GAME_NAME = "Tanks";
    public static String GAME_VERSION = "  v 0.1";
    public static Color BACKGROUND_COLOR = Color.WHITE;

    public static enum GameStatus{
        Running,Paused,GameOver
    }
}
