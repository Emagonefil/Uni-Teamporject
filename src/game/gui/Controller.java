package game.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import game.Constants;
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
import javafx.geometry.Pos;
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

        if(!Main.isRunning) {
            Main.MultiPlayer(primaryStage);
        }


        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        // Create List of Rooms
        ObservableList<String> roomIDs = FXCollections.<String>observableArrayList();
        
        // Create List of Users
        ObservableList<String> users = FXCollections.<String>observableArrayList();
        
        // Fill list of rooms
        if (!Main.c1.rooms.isEmpty()) {
            for (Room room : Main.c1.rooms) {
                roomIDs.add("" + room.roomId);
            }
        }

        // Add List of Rooms to a ListView Obj
        JFXListView<String> roomList = new JFXListView<String>();

        JFXListView<String> userList = new JFXListView<>();
        for (String roomID : roomIDs) {
            roomList.getItems().add(roomID);
        }

        roomList.getStyleClass().add("listview");
        userList.getStyleClass().add("listview");
        // When a room is selected from the list, display the users in that room
        roomList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String> () {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                userList.getItems().removeAll(users);
                users.removeAll();
                users.clear();
                for(Integer clientID : Main.c1.rooms.get(0).ClientId) {
                    users.add("" + clientID);
                }

                for (String clientID: users) {
                    userList.getItems().add(clientID);
                }
            }
        });

        // Create necessary Buttons
        JFXButton refresh = new JFXButton("Refresh List");
        JFXButton join = new JFXButton("Join Room");
        JFXButton create = new JFXButton("Create Room");
        JFXButton start = new JFXButton("Start Game");
        JFXButton back = new JFXButton("Back");
//        ActionEvent event1 = new ActionEvent(Even);

        refresh.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.c1.getRoomList();
                roomList.refresh();
            }
        });

        // When the back button is pressed
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t)  {
                roomList.getItems().clear();
                roomList.getItems().removeAll(roomIDs);
                roomList.refresh();
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("menu3.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle("Tanks");
                    primaryStage.setMaximized(true);

                    primaryStage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // When the join button is pressed
        join.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String room = roomList.getSelectionModel().getSelectedItem().toString();
                int room2 = Integer.parseInt(room);
                Main.c1.joinRoom(room2);
//                GameWindow newWindow = new GameWindow(primaryStage,Main.c1);
            }
        });

        create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Main.c1.createRoom();
                Main.c1.getRoomList();
                int sel = roomList.getSelectionModel().getSelectedIndex();
                if (sel >= 0 && sel < roomList.getItems().size()) {
                    roomList.setUserData(roomList.getItems().get(sel));
                    ObservableList<String> items = roomList.getItems();
                    roomList.setItems(null);
                    roomList.setItems(items);
                }
            }
        });

        // When the start button is pressed
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.c1.startGame();
                GameWindow newGame = new GameWindow(primaryStage,Main.c1);
            }
        });

        vbox.getStyleClass().add("vbox");

        vbox.getChildren().addAll(roomList,refresh,join,create,start,back,userList);
        primaryStage.getScene().setRoot(vbox);
        primaryStage.getScene().getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
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

        Parent root1 = FXMLLoader.load(getClass().getResource("menu3.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle("Tanks");
        primaryStage.setMaximized(true);

        primaryStage.show();

    }


}
