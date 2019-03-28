package game.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import game.Constants;
import game.Main;
import game.entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import java.io.IOException;

import static game.Main.ud;

public class Login extends Control {
    private User user= new User();
    @FXML
    JFXTextField userName;
    @FXML
    JFXPasswordField passWord;
    @FXML
    JFXTextField signUserName;
    @FXML
    JFXPasswordField signPassWord;
    @FXML
    JFXPasswordField signRepPassWord;
    @FXML
    Label message;
    @FXML
    Label signMessage;

    /** The mute music button */
    @FXML
    private ToggleButton music;
    /** The mute sound effects button */
    @FXML
    private ToggleButton sound;

    /**
     * Runs once when controller is initialised.
     * Adds event handlers to the sound buttons
     */
    public void initialize() {
        Main.soundButtons(music,sound);
    }


    public void handleLogin(ActionEvent actionEvent) throws Exception {
        getUser().setUsername(userName.getText());
        getUser().setPassword(passWord.getText());
        if(ud.login(getUser())){
            Node node = (Node) actionEvent.getSource();
            toPage(node,"../graphics/fxml/menu3.fxml","Menu");
        }else{
            message.setText("Incorrect username or password!!");
        }
    }

    public void handleSignUp(ActionEvent actionEvent)throws Exception {
        Node node = (Node) actionEvent.getSource();
        toPage(node,"../graphics/fxml/sign.fxml","Regist");
    }

    public void handleRegister(ActionEvent actionEvent) throws Exception {
        User user = new User();
        user.setUsername(signUserName.getText());
        if(ud.userIsExist(user)){
            signMessage.setText("User is exist!!");
            return ;
        }else if(signPassWord.getText().equals(signRepPassWord.getText())){
            user.setPassword(signPassWord.getText());
            ud.insertUser(user);
            signMessage.setText("Success!");

            Node node = (Node) actionEvent.getSource();
            toPage(node,"../graphics/fxml/login.fxml","Login");

        }else{
            signMessage.setText("Entered passwords differ");
            return ;
        }
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        toPage(node,"../graphics/fxml/login.fxml","Login");

    }


    public void toPage( Node node ,String path,String title) throws IOException {
        Stage primaryStage = (Stage) node.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource(path));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle(Constants.GAME_NAME);
        primaryStage.show();
    }

    /**
     * Returns the user
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user to the given parameter
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Exists the game
     * @param event The event that triggers this action
     */
    @FXML
    protected void quit(ActionEvent event) throws Exception {
        System.out.println("Quit Game");
        Platform.exit();
        System.exit(0);
    }

}
