package game.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import game.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import game.network.Room;

import java.net.URL;
import java.util.*;


public class Controller {
    @FXML
    private Text actiontarget;

    public JFXListView roomList;
    public static AnimationTimer tim;

    @FXML
    protected void handleSinglePlayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        VBox loading = new VBox();
        Image img = Renderer.loading;
        ImageView imgv= new ImageView(img);
        loading.getChildren().add(imgv);
        primaryStage.getScene().setRoot(loading);
        primaryStage.show();

        Main.SinglePlayer(primaryStage);
    }

    @FXML
    protected void handleMultiplayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        if (!Main.isRunning) {
            Main.MultiPlayer(primaryStage);
        }

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        // Create necessary Buttons
        JFXButton refresh = new JFXButton("REFRESH ROOMS");
        JFXButton join = new JFXButton("JOIN ROOM");
        JFXButton create = new JFXButton("CREATE ROOM");
        JFXButton start = new JFXButton("START");
        JFXButton back = new JFXButton("BACK");
//        ActionEvent event1 = new ActionEvent(Even);

        start.setId("startBtn");

        roomList = getRoomsList();
        VBox titleImg = new VBox();
        titleImg.getStyleClass().add("titleImg");

        Label roomsLabel = new Label("ROOMS");
        roomsLabel.setId("roomsLabel");
//        JFXListView newList;

        Label gamestart = new Label("Game started, press start now");
        Room room = Main.c1.findRoom(Main.c1.getMyRoom());

        tim = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (room != null) {
                    if(room.ServerIp != null&&room.status == 2 && room.ServerIp != "") {
                        GameWindow.start(Main.mainStage,Main.c1);
                        stopTimer();
    //                    if (!vbox.getChildren().contains(gamestart)) {
    //                        vbox.getChildren().add(gamestart);
    //                    }
                    } //else {
//                    if (vbox.getChildren().contains(gamestart)) {
//                        vbox.getChildren().remove(gamestart);
//                    }
                }

//                if (vbox.getChildren().contains(roomList)) {
//                    if (Main.c1.getEntities() != null) {
//                        System.out.println(roomList.getGroupnode());
//                        vbox.getChildren().remove(roomList);
////                        Main.c1.getRoomList();
////                        roomList = getRoomsList();
//                        vbox.getChildren().add(roomList);
//                    }
//
//                }
            }
        };


        refresh.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vbox.getChildren().removeAll(titleImg, join, create, start, back,refresh, roomsLabel, roomList);

                Main.c1.getRoomList();
                roomList.refresh();
//                JFXListView newList = getRoomsList();
                roomList = getRoomsList();
//                roomList = newList;
                vbox.getChildren().addAll(titleImg, join, create, start, back, refresh, roomsLabel, roomList);
                primaryStage.getScene().setRoot(vbox);
                primaryStage.show();
//                vbox.getChildren().removeAll(titleImg, join, create, start, back,refresh, roomsLabel, roomList);
            }
        });

        // When the back button is pressed
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    roomList.getItems().clear();
                    roomList.refresh();
                    Main.isRunning = false;
                    if (GameLoop.isRunning) {
                        GameLoop.stop();
                    }

                    Parent root1 = FXMLLoader.load(getClass().getResource("menu3.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle("Tanks");
                    primaryStage.setMaximized(true);

                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // When the join button is pressed
        join.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int room = roomList.getSelectionModel().getSelectedIndex()+1;
//                int room2 = Integer.parseInt(room);
                Main.c1.joinRoom(room);
//                GameWindow newWindow = new GameWindow(primaryStage,Main.c1);
            }
        });

        create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Main.c1.createRoom();
//                Main.c1.getRoomList();
            }
        });

        // When the start button is pressed
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.c1.startGame();
//                GameWindow newGame = new GameWindow(primaryStage, Main.c1);
                GameWindow.start(primaryStage,Main.c1);
            }

        });
        vbox.getStyleClass().add("vbox");

        vbox.getChildren().addAll(titleImg, join, create, start, back, refresh, roomsLabel, roomList);
        primaryStage.getScene().setRoot(vbox);
        primaryStage.getScene().getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.show();

        tim.start();

    }

    public static void stopTimer() {
        tim.stop();
    }


    public static JFXListView getRoomsList() {
        // Create List of Rooms
        ObservableList<JFXListView> roomIDs = FXCollections.<JFXListView>observableArrayList();

        // Create List of Users
        ObservableList<String> users;

        // Add List of Rooms to a ListView Obj
        JFXListView<JFXListView> roomList = new JFXListView<JFXListView>();
        roomList.getStyleClass().add("custom-jfx-list-view");
        roomList.setId("list1");

        JFXListView<String> userList;
        Label idOfRoom;
        for (Room room : Main.c1.rooms) {
            users = FXCollections.observableArrayList();
            userList = new JFXListView<>();
            userList.getStyleClass().add("sublist");
//            roomList.getItems().

            for (int client : room.ClientId) {
                users.add(" PLAYER " + (room.ClientId.indexOf(client) + 1) + ": " + client + "");
            }
            for (String id : users) {
                userList.getItems().add(id);
            }
            idOfRoom = new Label(" ROOM " + room.getRoomId());
            userList.setGroupnode(idOfRoom);
            roomList.getItems().add(userList);
        }
        return roomList;
    }

    @FXML
    protected void handleSettingsButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("settings.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    @FXML
    protected void handleRankingsButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("Rankings.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    @FXML
    protected void handleQuitButtonAction(ActionEvent event) throws Exception {
        System.out.println("Quit Game");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void handleBackButtonAction(ActionEvent event) throws Exception {

        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("menu3.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }

}