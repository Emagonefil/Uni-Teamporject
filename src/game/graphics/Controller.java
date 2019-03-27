package game.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import game.*;
import game.entity.User;
import game.network.Port;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import game.network.Room;

import java.util.*;


public class Controller {
    @FXML
    private Text actiontarget;
    private int number;

    public JFXListView roomList;
    public static AnimationTimer tim;
    public static Room room;

    @FXML
    private ToggleButton music;
    @FXML
    private ToggleButton sound;

    public void initialize() {
        Main.soundButtons(music,sound);
    }

//    public void initialize() {
//        UserInterface.init(Main.mainStage);
//    }
    @FXML
    protected void handleSinglePlayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
//        VBox loading = new VBox();
//        Image img = Renderer.loading;
//        ImageView imgv= new ImageView(img);
//        loading.getChildren().add(imgv);
//        primaryStage.getScene().setRoot(loading);
//        primaryStage.show();
////        System.out.println("fuck");
//        Thread.sleep(5000);
//        Main.gui.stop();

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(20);
//        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("vbox");
        Label title = new Label("CLASH OF TANKS");
        Label label = new Label("CHOOSE THE NUMBER OF AI OPPONENTS:");
        title.getStyleClass().add("title");
        title.setFont(new Font("Press Start 2P", 36));

        // SOUND BUTTONS
        final ToggleButton toggleMusic = new ToggleButton();
        toggleMusic.setLayoutX(10);
        toggleMusic.setLayoutY(10);
        final ToggleButton toggleSound = new ToggleButton();
        toggleSound.setLayoutX(60);
        toggleSound.setLayoutY(10);
        toggleSound.setId("toggleSound");
        toggleMusic.setId("toggleMusic");

        Main.soundButtons(toggleMusic, toggleSound);

        HBox soundHolder = new HBox();
        soundHolder.setSpacing(10);
        soundHolder.getChildren().addAll(toggleMusic,toggleSound);
        // COUNTER CHANGE
        HBox counter = new HBox();
        counter.setSpacing(10);
        counter.getStyleClass().add("hbox");
        number = Main.numOfAI;
        Text count = new Text("" + number);
        JFXButton minus = new JFXButton("-");
        JFXButton plus = new JFXButton("+");

        minus.setMaxWidth(40);
        plus.setMaxWidth(40);
        plus.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                number++;
                if (number > 40) {
                    number = 40;
                }
                count.setText("" + number);
            }
        });
        minus.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                number--;
                if (number < 1) {
                    number = 1;
                }
                count.setText("" + number);
            }
        });

        counter.getChildren().addAll(minus,count,plus);

        // BUTTONS
        JFXButton backBtn = new JFXButton("CANCEL");
        JFXButton start = new JFXButton("PLAY");
        start.setStyle("-fx-background-color: LIMEGREEN");

        HBox buttons = new HBox();
        buttons.getStyleClass().add("hbox");
        buttons.setSpacing(10);
        buttons.getChildren().addAll(backBtn,start);
        box.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // When the back button is pressed
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("fxml/menu3.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle(Constants.GAME_NAME);

                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Main.numOfAI = number;
                Main.SinglePlayer(primaryStage);

            }
        });




        box.getChildren().addAll(soundHolder, title,label,counter,buttons);

        primaryStage.getScene().setRoot(box);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();


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

        // Sound buttons
        final ToggleButton toggleMusic = new ToggleButton();
        toggleMusic.setLayoutX(10);
        toggleMusic.setLayoutY(10);
        final ToggleButton toggleSound = new ToggleButton();
        toggleSound.setLayoutX(60);
        toggleSound.setLayoutY(10);
        toggleSound.setId("toggleSound");
        toggleMusic.setId("toggleMusic");

        Main.soundButtons(toggleMusic, toggleSound);
        HBox soundHolder = new HBox();
        soundHolder.setSpacing(10);
        soundHolder.getChildren().addAll(toggleMusic,toggleSound);

        // Create necessary Buttons
        JFXButton refresh = new JFXButton("REFRESH ROOMS");
        JFXButton join = new JFXButton("JOIN ROOM");
        JFXButton create = new JFXButton("CREATE ROOM");
        JFXButton start = new JFXButton("START");
        JFXButton back = new JFXButton("BACK");
//        ActionEvent event1 = new ActionEvent(Even);
        start.setStyle("-fx-background-color: LIMEGREEN");
        start.setId("startBtn");

        roomList = getRoomsList();
        Label titleImg = new Label("CLASH OF TANKS");
        titleImg.getStyleClass().add("title");
        titleImg.setFont(new Font("Press Start 2P", 36));

        Label roomsLabel = new Label("ROOMS");
        roomsLabel.setId("roomsLabel");
//        JFXListView newList;

        Label gamestart = new Label("Game started, press start now");
        room = Main.c1.findRoom(Main.c1.getMyRoom());

        tim = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateRoom();
                if (room != null) {
                    if(room.ServerIp != null&&room.status == 2 && room.ServerIp != "") {
//                        System.out.println("GAME WINDOW SHOULD START NOW"+ Port.mulitcastAddress);
//                        if(GameLoop.isRunning) {
//                            GameLoop.stop();
//                        }
                        GameWindow.clear();
                        GameWindow.start(Main.mainStage,Main.c1);
//                        System.out.println("GAME WINDOW SHOULD HAVE STARTED ALREADY"+ Port.mulitcastAddress);
                        stopTimer();
                    }
                }

            }
        };


        refresh.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vbox.getChildren().removeAll(soundHolder,titleImg, join, create, start, back,refresh, roomsLabel, roomList);

                Main.c1.getRoomList();
                roomList.refresh();
//                JFXListView newList = getRoomsList();
                roomList = getRoomsList();
//                roomList = newList;
                vbox.getChildren().addAll(soundHolder,titleImg, join, create, start, back, refresh, roomsLabel, roomList);
                primaryStage.getScene().setRoot(vbox);
                primaryStage.show();
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
//                    if (GameLoop.isRunning) {
//                        GameLoop.stop();
//                    }
                    GameWindow.clear();

                    Parent root1 = FXMLLoader.load(getClass().getResource("fxml/menu3.fxml"));
                    primaryStage.getScene().setRoot(root1);
                    primaryStage.setTitle(Constants.GAME_NAME);

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
                vbox.getChildren().removeAll(soundHolder,titleImg, join, create, start, back,refresh, roomsLabel, roomList);

                Main.c1.getRoomList();
                roomList.refresh();
//                JFXListView newList = getRoomsList();
                roomList = getRoomsList();
//                roomList = newList;
                vbox.getChildren().addAll(soundHolder,titleImg, join, create, start, back, refresh, roomsLabel, roomList);
                primaryStage.getScene().setRoot(vbox);
                primaryStage.show();
            }
        });

        create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Main.c1.createRoom();
//                Main.c1.getRoomList();
                vbox.getChildren().removeAll(soundHolder,titleImg, join, create, start, back,refresh, roomsLabel, roomList);

                Main.c1.getRoomList();
                roomList.refresh();
//                JFXListView newList = getRoomsList();
                roomList = getRoomsList();
//                roomList = newList;
                vbox.getChildren().addAll(soundHolder,titleImg, join, create, start, back, refresh, roomsLabel, roomList);
                primaryStage.getScene().setRoot(vbox);
                primaryStage.show();
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

        vbox.getChildren().addAll(soundHolder,titleImg, join, create, start, back, refresh, roomsLabel, roomList);
        primaryStage.getScene().setRoot(vbox);
        primaryStage.getScene().getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.show();

        tim.start();

    }



    public static void updateRoom() {
        room = Main.c1.findRoom(Main.c1.getMyRoom());
    }
    public static void stopTimer() {
        tim.stop();
//        System.out.println("TIMER STOPPED");
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

            for (Map.Entry<Integer,String> client : room.Clients.entrySet()) {
//                users.add(" PLAYER " + (room.ClientId.indexOf(client) + 1) + ": " + client + "");
                users.add(client.getValue());
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

        Parent root1 = FXMLLoader.load(getClass().getResource("fxml/settings.fxml"));

        primaryStage.setTitle(Constants.GAME_NAME);
        primaryStage.getScene().setRoot(root1);
        primaryStage.show();


//        primaryStage.show();
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

        Parent root1 = FXMLLoader.load(getClass().getResource("fxml/menu3.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();

    }

    @FXML
    protected void help(ActionEvent event) throws Exception {

        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        Parent root1 = FXMLLoader.load(getClass().getResource("fxml/help.fxml"));
        primaryStage.getScene().setRoot(root1);
        primaryStage.setTitle(Constants.GAME_NAME);

        primaryStage.show();

    }


}