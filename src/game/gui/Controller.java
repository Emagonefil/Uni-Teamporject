package game.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import game.GameWindow;
import game.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import game.network.Room;


public class Controller {
    @FXML
    private Text actiontarget;

    @FXML protected void handleSinglePlayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();
        Main.SinglePlayer(primaryStage);
    }

    @FXML protected void handleMultiplayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

//        Parent root = FXMLLoader.load(getClass().getResource("CreateRoom.fxml"));
//        primaryStage.getScene().setRoot(root);
//        primaryStage.setTitle("Tanks");
//        primaryStage.setMaximized(true);
//
//        primaryStage.show();
        Main.MultiPlayer(primaryStage);
        VBox vbox = new VBox();

        ObservableList<String> roomIDs = FXCollections.<String>observableArrayList();
        if (!Main.c1.rooms.isEmpty()) {
            for (Room room : Main.c1.rooms) {
                roomIDs.add("" + room.roomId);
            }
        }

//        ListView<String> list = new ListView<>(roomIDs);
        JFXListView<String> list = new JFXListView<String>();
        for (String roomID : roomIDs) {
            list.getItems().add(roomID);
        }
//        list.setPrefHeight(200);

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String> () {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> clients = FXCollections.<String>observableArrayList();
                for(Integer clientID : Main.c1.rooms.get(0).ClientId) {
                    clients.add("" + clientID);
                }
                JFXListView<String> cls = new JFXListView<>();
                for (String clientID: clients) {
                    cls.getItems().add(clientID);
                }
                cls.setPrefHeight(200);
                vbox.getChildren().add(cls);
            }
        });

        JFXButton join = new JFXButton("Join Room");
        JFXButton create = new JFXButton("Create Room");
        JFXButton back = new JFXButton("Back");
//        ActionEvent event1 = new ActionEvent(Even);
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t)  {

                list.getItems().clear();
                list.getItems().removeAll(roomIDs);
                list.refresh();
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("menu copy.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle("Tanks");
                    primaryStage.setMaximized(true);

                    primaryStage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });

        join.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int room = list.getSelectionModel().getSelectedIndex();
                Main.c1.joinRoom(room);
//                GameWindow newWindow = new GameWindow(primaryStage,Main.c1);
            }
        });

        create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {


                Main.c1.getRoomList();
                int sel = list.getSelectionModel().getSelectedIndex();
                if (sel >= 0 && sel < list.getItems().size()) {
                    list.setUserData(list.getItems().get(sel));
                    ObservableList<String> items = list.getItems();
                    list.setItems(null);
                    list.setItems(items);
                }
            }
        });

        vbox.getChildren().addAll(list,join,create,back);
        primaryStage.getScene().setRoot(vbox);
        primaryStage.show();

    }


    private <T> void forceListRefreshOn(JFXListView<T> lsv) {
        ObservableList<T> items = lsv.getItems();
        lsv.setItems(null);
        lsv.setItems(items);
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

    @FXML protected void handleRankingsButtonAction(ActionEvent event) throws Exception {
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("Rankings.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    @FXML protected void handleQuitButtonAction(ActionEvent event) throws Exception {
        System.out.println("Quit Game");
        Platform.exit();
        System.exit(0);
    }

    @FXML protected void handleBackButtonAction(ActionEvent event)throws Exception{

        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("menu copy.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }

    @FXML protected void handleLoadRoomButtonAction(ActionEvent event)throws Exception{
        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

//        Parent root1 = FXMLLoader.load(getClass().getResource("menu copy.fxml"));
//        primaryStage.getScene().setRoot(root1);
//        primaryStage.setTitle("Tanks");
//        primaryStage.setMaximized(true);
//
//        primaryStage.show();
//        Main.MultiPlayer(primaryStage);
        GameWindow newGame = new GameWindow(primaryStage,Main.c1);
    }

    @FXML protected void handleNewRoomButtonAction(ActionEvent event)throws Exception{

        Node node = (Node)event.getSource();
        Stage primaryStage = (Stage)node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("menu copy.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }

}
