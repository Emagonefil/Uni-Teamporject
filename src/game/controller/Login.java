package game.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import game.Main;
import game.dao.UserDao;
import game.entity.User;
import game.graphics.UserInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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
    private  UserDao ud =new UserDao();

    public void handleLogin(ActionEvent actionEvent) throws Exception {
        getUser().setUsername(userName.getText());
        getUser().setPassword(passWord.getText());
        System.out.println("登录用户为："+ getUser().toString());
        if(ud.login(getUser())){
            Node node = (Node) actionEvent.getSource();

            toPage(node,"../graphics/menu3.fxml","Menu");

        }else{
            message.setText("Incorrect username or password!!");
        }
    }

    public void handleSignUp(ActionEvent actionEvent)throws Exception {

        Node node = (Node) actionEvent.getSource();
        toPage(node,"../graphics/sign.fxml","Regist");

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
            toPage(node,"../graphics/login.fxml","Login");
        }else{
            signMessage.setText("Entered passwords differ");
            return ;
        }
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        toPage(node,"../graphics/login.fxml","Login");
    }


    public void toPage( Node node ,String path,String title) throws IOException {
        Stage primaryStage = (Stage) node.getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getResource(path));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle(title);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    protected void test(ActionEvent event) throws Exception {
        UserInterface.background(Main.mainStage);
    }

}
