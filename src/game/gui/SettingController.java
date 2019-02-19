package game.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable{

    @FXML protected void handleBackButtonAction(ActionEvent event)throws Exception{

        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
    

