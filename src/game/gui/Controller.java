package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class Controller {
    @FXML
    private Text actiontarget;

    @FXML protected void handleSinglePlayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Rectangle rec = new Rectangle(10.5,10.5,40,30);
        Group root = new Group(rec);
        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    @FXML protected void handleMultiplayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("CreateRoom.fxml"));
        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();


    }

    @FXML protected void handleSettingsButtonAction(ActionEvent event) throws Exception {
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("settings.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    @FXML protected void handleBackButtonAction(ActionEvent event)throws Exception{

        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }
}
