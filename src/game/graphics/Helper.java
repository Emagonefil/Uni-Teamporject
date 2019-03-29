package game.graphics;

import game.Constants;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * A helper class that holds reusable methods that might be needed by other various classes
 *
 * @author Andreea
 */
public class Helper {

    /**
     * Changes from the current screen to the given screen
     * @param event The event that triggers the action
     * @param cls The class the method is called from
     */
    public static void changeScreen(Event event, Class cls, String path) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        Parent root1 = FXMLLoader.load(cls.getResource(path));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();
    }
}
