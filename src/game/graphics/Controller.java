package game.graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import game.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import game.network.Room;

import java.util.*;

/**
 * The main controller for the game.
 * It dictates how elements on the main menu page and on the help page should act.
 *
 * @author Andreea
 */
public class Controller {

    /** The number of AIs in a singleplayer game */
    private int numberOfAIs;

    /** The visual representation of the list of rooms*/
    public JFXListView roomList;
    /** The timer that checks if the multiplayer game of your room has started
     * and start your game window when it does */
    public static AnimationTimer startGameTimer;
    /** The multiplayer game room */
    public static Room room;

    /** The mute sound buttons*/
    @FXML
    private ToggleButton music;
    @FXML
    private ToggleButton sound;

    /**
     * Runs once when the controller is initialised.
     * Adds event handlers to the sound buttons
     */
    public void initialize() {
        Main.soundButtons(music,sound);
    }

    /**
     * Starts a singleplayer game by switching to a game window
     * and launching the server
     * @param event The event that triggered this method
     * @throws Exception
     */
    @FXML
    protected void handleSinglePlayerButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        // Create the page that allows the user to select the number of AI opponents to have in the singleplayer game
        VBox box = new VBox();
        // Set the styling
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(20);
        box.getStyleClass().add("vbox");
        // Set the title
        Label title = new Label("CLASH OF TANKS");
        // Create a label with instructions
        Label label = new Label("CHOOSE THE NUMBER OF AI OPPONENTS:");
        title.getStyleClass().add("title");
        title.setFont(new Font("Press Start 2P", 36));

        // Create a holder for the sound buttons
        HBox soundHolder = new HBox();
        soundHolder.setSpacing(10);
        soundHolder.getChildren().addAll(Main.toggleMusic,Main.toggleSound);

        // Create a holder for the AI count selection
        HBox counter = new HBox();
        counter.setSpacing(10);
        counter.getStyleClass().add("hbox");

        // Get the default number of AIs from the Main class
        numberOfAIs = Main.numOfAI;
        Text count = new Text("" + numberOfAIs);

        // Create the minus and plus buttons
        JFXButton minus = new JFXButton("-");
        JFXButton plus = new JFXButton("+");
        minus.setMaxWidth(40);
        plus.setMaxWidth(40);

        // When the plus buttons is pressed
        plus.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                // Increase the number of AIs up to a maximum of 40
                numberOfAIs++;
                if (numberOfAIs > 40) {
                    numberOfAIs = 40;
                }
                // Display the value
                count.setText("" + numberOfAIs);
            }
        });

        // When the minus button is pressed
        minus.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                // Decrease the number of AIs to a minimum of 1
                numberOfAIs--;
                if (numberOfAIs < 1) {
                    numberOfAIs = 1;
                }
                // Display the value
                count.setText("" + numberOfAIs);
            }
        });

        // Add the buttons to the count holder
        counter.getChildren().addAll(minus,count,plus);

        // Create the play and the cancel buttons
        JFXButton backBtn = new JFXButton("CANCEL");
        JFXButton start = new JFXButton("PLAY");
        start.setStyle("-fx-background-color: LIMEGREEN");

        // Create a holder for the cancel and the play buttons
        HBox buttons = new HBox();
        buttons.getStyleClass().add("hbox");
        buttons.setSpacing(10);
        buttons.getChildren().addAll(backBtn,start);
        box.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // When the cancel button is pressed
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                // Return to the menu screen
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

        // When the play button is pressed
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                // Start a new singleplayer game with the selected number of AI opponents
                Main.numOfAI = numberOfAIs;
                Main.SinglePlayer(primaryStage);
            }
        });

        // Add all the content to the AI selection page
        box.getChildren().addAll(soundHolder, title,label,counter,buttons);

        // Display the page
        primaryStage.getScene().setRoot(box);
        primaryStage.setTitle(Constants.GAME_NAME);
        primaryStage.show();
    }


    /**
     * Opens the multiplayer lobby page and allows you to start a multiplayer game
     * @param event The event that triggers this action
     */
    @FXML
    protected void handleMultiplayerButtonAction(ActionEvent event) throws Exception {
        // Get the current stage
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();

        // If the game over is not already started, start it
        if (!Main.isRunning) {
            Main.MultiPlayer(primaryStage);
        }

        // Create a holder for te lobby page content
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vbox");

        // Create a holder for the sound buttons
        HBox soundHolder = new HBox();
        soundHolder.setSpacing(10);
        soundHolder.getChildren().addAll(Main.toggleMusic,Main.toggleSound);

        // Create the lobby page button
        // The button to refresh the room list
        JFXButton refresh = new JFXButton("REFRESH ROOMS");
        // The button to join a room
        JFXButton join = new JFXButton("JOIN ROOM");
        // The button to create a room
        JFXButton create = new JFXButton("CREATE ROOM");
        // The button to start a game
        JFXButton start = new JFXButton("START");
        // The button to go back to the main menu
        JFXButton back = new JFXButton("BACK");

        start.setStyle("-fx-background-color: LIMEGREEN");
        start.setId("startBtn");

        // Get the list of rooms
        roomList = getRoomsList();
        // Create the page title
        Label titleImg = new Label("CLASH OF TANKS");
        titleImg.getStyleClass().add("title");
        titleImg.setFont(new Font("Press Start 2P", 36));

        // Create the rooms label
        Label roomsLabel = new Label("ROOMS");
        roomsLabel.setId("roomsLabel");

        // Get the user room from the Main class
        room = Main.c1.findRoom(Main.c1.getMyRoom());

        // The timer that runs indefinitely to start the game window if the game starts
        startGameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateRoom();
                // If the user is in a room
                if (room != null) {
                    // And the game in that room has started
                    if(room.ServerIp != null && room.status == 2 && room.ServerIp != "") {
                        // Switch the user to the game window
                        GameWindow.clear();
                        GameWindow.start(Main.mainStage,Main.c1);
                        // Stop the timer
                        stopTimer();
                    }
                }
            }
        };

        // Refresh the lobby page
        refresh.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Use the helper method to refresh the page
                refreshLobby(vbox, soundHolder, titleImg, join, create, start, back, refresh, roomsLabel, primaryStage);
            }
        });

        // When the back button is pressed
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    // refresh the room list
                    roomList.getItems().clear();
                    roomList.refresh();

                    // Stop the server
                    Main.isRunning = false;
                    // Clear the game window
                    GameWindow.clear();

                    // Switch back to the menu screen
                    Helper.changeScreen(t,this.getClass(),"fxml/menu3.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // When the join button is pressed
        join.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Get the index of the selected room
                int room = roomList.getSelectionModel().getSelectedIndex()+1;
                // Set the room of your user to that room
                Main.c1.joinRoom(room);
                // Refresh the lobby page to see your username in that room
                refreshLobby(vbox, soundHolder, titleImg, join, create, start, back, refresh, roomsLabel, primaryStage);
            }
        });

        // When the create room button is pressed
        create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Create a new room
                Main.c1.createRoom();
                // Refresh the lobby page to see the new room
                refreshLobby(vbox, soundHolder, titleImg, join, create, start, back, refresh, roomsLabel, primaryStage);
            }
        });

        // When the start button is pressed
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Start a new game
                Main.c1.startGame();
                // Start the game window
                GameWindow.start(primaryStage,Main.c1);
            }

        });

        // Add all the content to the lobby page
        vbox.getChildren().addAll(soundHolder,titleImg, join, create, start, back, refresh, roomsLabel, roomList);
        primaryStage.getScene().setRoot(vbox);
        primaryStage.getScene().getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.show();

        // Start the timer that automatically starts your game
        startGameTimer.start();
    }

    /**
     * Reloads the lobby page
     * @param vbox The holder for all the page content
     * @param soundHolder The holder for the sound buttons
     * @param title The page title
     * @param join The join room button
     * @param create The create room button
     * @param start The start game button
     * @param back The return to the menu button
     * @param refresh The refresh page button
     * @param roomsLabel The rooms label
     * @param primaryStage The application stage
     */
    public void refreshLobby(VBox vbox, HBox soundHolder, Label title, JFXButton join, JFXButton create, JFXButton start, JFXButton back, JFXButton refresh, Label roomsLabel, Stage primaryStage) {

        // Remove all the content from the page
        vbox.getChildren().removeAll(soundHolder, title, join, create, start, back, refresh, roomsLabel, roomList);
        // Get the updated room list
        Main.c1.getRoomList();

        // Refresh the room list view
        roomList.refresh();
        roomList = getRoomsList();

        // Add all the reloaded content back into the page
        vbox.getChildren().addAll(soundHolder, title, join, create, start, back, refresh, roomsLabel, roomList);
        primaryStage.getScene().setRoot(vbox);

        //Display the page
        primaryStage.show();
    }

    /**
     * Updates the room to the current room of the client
     */
    public static void updateRoom() {
        room = Main.c1.findRoom(Main.c1.getMyRoom());
    }

    /**
     * Stops the refresh timer form running
     */
    public static void stopTimer() {
        startGameTimer.stop();
    }

    /**
     * Creates an updated list visualisation of all the rooms in the lobby
     * with their respective players
     * @return The rooms list visualisation
     */
    public static JFXListView getRoomsList() {
        // Create List of Rooms
        ObservableList<JFXListView> roomIDs = FXCollections.<JFXListView>observableArrayList();

        // Create List of Users
        ObservableList<String> users;

        // Add List of Rooms to a ListView Object
        JFXListView<JFXListView> roomList = new JFXListView<JFXListView>();
        roomList.getStyleClass().add("custom-jfx-list-view");
        roomList.setId("list1");

        // Create a list visualisation for the users in a room
        JFXListView<String> userList;
        Label idOfRoom;
        // For all the rooms, get the users in the room sublist
        for (Room room : Main.c1.rooms) {
            users = FXCollections.observableArrayList();
            userList = new JFXListView<>();
            userList.getStyleClass().add("sublist");

            // Add the users from the room to the list
            for (Map.Entry<Integer,String> client : room.Clients.entrySet()) {
                users.add(client.getValue());
            }

            // Add the ids of users to the list visualisation
            for (String id : users) {
                userList.getItems().add(id);
            }

            // If the game has started within a room, display that in the lobby
            if(room.status == 2) {
                idOfRoom = new Label(" ROOM " + room.getRoomId() + " - GAME STARTED");
            } else {
                idOfRoom = new Label(" ROOM " + room.getRoomId());
            }

            userList.setGroupnode(idOfRoom);
            // Add the sublist containing the users to the room section
            roomList.getItems().add(userList);
        }
        return roomList;
    }

    /**
     * Changes from the current screen to the settings page
     * @param event The event that triggers this action
     */
    @FXML
    protected void handleSettingsButtonAction(ActionEvent event) throws Exception {
        Helper.changeScreen(event,this.getClass(),"fxml/settings.fxml");
    }

    /**
     * Quits the application
     * @param event The event that triggers this action
     */
    @FXML
    protected void handleQuitButtonAction(ActionEvent event) throws Exception {
        System.out.println("Quit Game");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Switches from the current screen to the main menu screen
     * @param event The event that triggers this action
     */
    @FXML
    protected void handleBackButtonAction(ActionEvent event) throws Exception {
        String path = "fxml/menu3.fxml";
        Helper.changeScreen(event,this.getClass(),path);
    }

    /**
     * Switches from the current screen to the help page
     * @param event The event that triggers this action
     */
    @FXML
    protected void help(ActionEvent event) throws Exception {
        Helper.changeScreen(event,this.getClass(),"fxml/help.fxml");
    }


}