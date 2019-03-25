package game.graphics;

import com.jfoenix.controls.JFXButton;
import game.*;
import game.entity.Player;
import game.entity.Point;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


public class UserInterface {
    private static Canvas c;
    private static GraphicsContext gc;
    private static Pane root;
    private static Stage windowStage;
    private static JFXButton b1;
    private static AnimationTimer timer;

    public static void background(Stage stage) {
        root = new Pane();
        StackPane holder = new StackPane();
        c = new Canvas(Constants.CANVAS_WIDTH,Constants.CANVAS_HEIGHT);
        holder.getChildren().add(c);
        root.getChildren().add(holder);
        gc = c.getGraphicsContext2D();

        Player p1 = new Player(100,50,new Point(20,120),0,5,1,100,100);
        Player p2 = new Player(100,50,new Point(220,220),0,5,1,100,100);
        Player p3 = new Player(100,50,new Point(20,320),0,5,1,100,100);
        Player p4 = new Player(100,50,new Point(220,420),0,5,1,100,100);
        Player p5 = new Player(100,50,new Point(20,520),0,5,1,100,100);
        Player p6 = new Player(100,50,new Point(220,620),0,5,1,100,100);
        Player p7 = new Player(100,50,new Point(20,720),0,5,1,100,100);

        Sprite t1 = new Sprite(p1, Renderer.tank1, 100,50,1);
        Sprite t2 = new Sprite(p2, Renderer.tank2, 100,50,1);
        Sprite t3 = new Sprite(p3, Renderer.tank3, 100,50,1);
        Sprite t4 = new Sprite(p4, Renderer.tank4, 100,50,1);
        Sprite t5 = new Sprite(p5, Renderer.tank5, 100,50,1);
        Sprite t6 = new Sprite(p6, Renderer.tank6, 100,50,1);
        Sprite t7 = new Sprite(p7, Renderer.tank7, 100,50,1);
        Sprite[] tanks= {t1,t2,t3,t4,t5,t6};
        stage.getScene().setRoot(root);
//        stage.setMaximized(true);

        stage.setWidth(Constants.CANVAS_WIDTH);
        stage.setHeight(Constants.CANVAS_HEIGHT);
        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(Constants.CANVAS_WIDTH));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(Constants.CANVAS_HEIGHT));   //must match with the one in the controller
        root.getTransforms().add(scale);
        stage.setMaximized(true);
        stage.setFullScreen(true);

        stage.show();

        timer = new AnimationTimer() {

            private long lastUpdate = 0 ;
            @Override
            public void handle(long l) {
//                if (l - lastUpdate >= 18_000_000) {
                    playTanks(tanks);
//                    lastUpdate = l ;
//                }
            }
        };

        VBox vbox = null;
        timer.start();
        try {
            vbox = FXMLLoader.load(UserInterface.class.getResource("fxml/menu3.fxml"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        root.getChildren().add(vbox);


    }

    public static void playTanks(Sprite[] t) {
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        for(Sprite tank : t) {
            if(tank.entity.getPosition().getX() >= Constants.CANVAS_WIDTH + 100) {
                tank.entity.getPosition().setX(-100);
            } else {
                tank.entity.getPosition().setX(tank.entity.getPosition().getX()+2);
            }
            Renderer.playAnimation(gc,tank,tank.entity);
        }
        gc.restore();
    }

    public static void login() {

    }

    public static void signup() {

    }

    public static void menu() {

    }

    public static void settings() {

    }




}
