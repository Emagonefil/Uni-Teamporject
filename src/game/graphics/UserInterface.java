package game.graphics;

import com.jfoenix.controls.JFXButton;
import game.*;
import game.ai.AiController;
import game.entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    public static void background(Stage stage) {
        Main.c1.init();
        List<ClientLogic> AIs=new ArrayList<>();
        int numOfAI=4;
        for(int i=0;i<numOfAI;i++){
            ClientLogic ai;
            ai=new ClientLogic();
            ai.ServerId = 0;
            ai.id = i;
            Player p;
            p = (Player)Main.c1.getEntityByID(ai.id);
            p.name="AI_NO."+i;
            AIs.add(ai);
            (new AiController(ai,Main.c1)).start();
        }

        Stage windowStage = stage;
        Pane root = new Pane();
        StackPane holder = new StackPane();

        Canvas c = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        holder.getChildren().add(c);
        root.getChildren().add(holder);
        GraphicsContext gc = c.getGraphicsContext2D();
        c.setMouseTransparent(true);
        JFXButton b1 = new JFXButton("RETURN TO MENU");

        final ToggleButton toggle = new ToggleButton();
        toggle.setLayoutX(10);
        toggle.setLayoutY(10);
        root.getStylesheets().addAll(GameWindow.class.getResource("graphics/style.css").toExternalForm());

//        b1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                GameLoop.stop();
//                Main.isRunning = false;
//                System.out.println("I am here lalallalallalalalalal");
//                backToMenu(windowStage);
//            }
//        });
//        b1.setVisible(false);

        root.getChildren().addAll(b1,toggle);
        b1.setLayoutX((Constants.CANVAS_WIDTH - 230)/2);
        b1.setLayoutY((Constants.CANVAS_HEIGHT - 40)/2);
        BackgroundImage bkg = new BackgroundImage(Renderer.background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        holder.setBackground(new Background(bkg));

        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(Constants.CANVAS_WIDTH));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(Constants.CANVAS_HEIGHT));   //must match with the one in the controller
        root.getTransforms().add(scale);

        stage.getScene().setRoot(root);
        stage.setMaximized(true);
//        stage.show();
        GameLoop.start(gc, stage.getScene(), Main.c1);
    }



}
