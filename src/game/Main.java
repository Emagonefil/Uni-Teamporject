package game;

import java.net.URL;
import java.util.*;

import game.ai.AiController;
import game.network.Room;
import game.network.RoomServer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// loop that runs continuously to update every component of the game
public class Main extends Application {
	public static ClientLogic c1 = new ClientLogic();
	public String username = "";
	boolean forward, backward, left, right, shoot;
	public static List<ClientLogic> AIs=new ArrayList<>();
	public static int numOfAI=7;
	public static void main(String args[]) {
		c1.init();
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		// set the window title
		primaryStage.setTitle(Constants.GAME_NAME + " " + Constants.GAME_VERSION);

		// set the window to be maximized (fullscreen)
		primaryStage.setMaximized(true);

		/*
		********************


		// create a vbox
		VBox vbox = new VBox();

		// set VBox spacing
		vbox.setSpacing(20);

		// set VBox alignment
		vbox.setAlignment(Pos.CENTER);

		// create a label for the name of the game
		Label label = new Label(Constants.GAME_NAME);

		// create a mute button
		// Button sound = new Button("Sound");

		// create a singleplayer button
		Button single = new Button("Singleplayer");

		// add event to start a singleplayer game when singleplayer is pressed
		single.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				SinglePlayer(primaryStage);
			}
		});

		// create a multiplayer button
		Button multi = new Button("Multiplayer");

		// add event to start a multiplayer game when singleplayer is pressed
		multi.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
//				HBox room = new HBox();
//				room.setSpacing(20);
//				Text title = new Text("Room");
//				TextField name = new TextField();
//
//				ListView<String> servers = new ListView<>();
//				ObservableList<String> items = FXCollections.observableArrayList (
//						"one", "two", "three", "four");
//				servers.setItems(items);
//
//				ListView<String> players = new ListView<>();
//
//
//				Button start = new Button("Start");
//				start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//					@Override
//					public void handle(MouseEvent mouseEvent) {
//						username = name.getCharacters().toString();
//						System.out.println("Username: " + username);
//						MultiPlayer(primaryStage);
//					}
//				});
//
//				room.getChildren().addAll(title,name,servers,players,start);
				try {

				} catch() {

				}

			}
		});

		// create a setting button
		Button settings = new Button("Settings");

		// add event to open settings when settings button is pressed
		settings.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// create content for the settings page
				try {
					Parent settings = FXMLLoader.load(getClass().getResource("gui/settings.fxml"));
					primaryStage.getScene().setRoot(settings);
					primaryStage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}

			}
		});

		// create a quit button
		Button quit = new Button("Quit Game");

		// add event to exit game when quit is pressed
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// gracefully stop the game
				System.out.println("Quit Game");
				Platform.exit();
				System.exit(0);
			}
		});
		vbox.getChildren().addAll(label,single,multi,settings,quit);
		********************************************************************
		*/

		// close application (stop all processes) when x button is pressed
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent windowEvent) {
				// gracefully stop the game
				System.out.println("Quit Game");
				Platform.exit();
				System.exit(0);
			}
		});

		// create a scene
		String sceneFile = "gui/menu copy.fxml";
		Parent root = null;
		URL url  = null;
		try
		{
			url  = getClass().getResource( sceneFile );
			root = FXMLLoader.load( url );
			System.out.println( "  fxmlResource = " + sceneFile );
		}
		catch ( Exception ex )
		{
			System.out.println( "Exception on FXMLLoader.load()" );
			System.out.println( "  * url: " + url );
			System.out.println( "  * " + ex );
			System.out.println( "    ----------------------------------------\n" );
			throw ex;
		}
		Scene scene = new Scene(root);

		// add style sheet to this scene
//		scene.getStylesheets().addAll(this.getClass().getResource("gui/style.css").toExternalForm());

		// add scene to stage and display stage
		primaryStage.setScene(scene);
		primaryStage.show();


		primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				switch (keyEvent.getCode()){
					case W:
					case UP: 	forward = true; break;
					case S:
					case DOWN:	backward = true; break;
					case A:
					case LEFT:	left = true; break;
					case D:
					case RIGHT:	right = true; break;
					case J:
					case SPACE:	shoot = true; break;
				}
			}
		});

		primaryStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				switch (keyEvent.getCode()){
					case W:
					case UP: 	forward = false; break;
					case S:
					case DOWN:	backward = false; break;
					case A:
					case LEFT:	left = false; break;
					case D:
					case RIGHT:	right = false; break;
					case J:
					case SPACE:	shoot = false; break;
				}
			}
		});

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				if(forward) c1.sendCommands("Forward");
				if(backward) c1.sendCommands("Backward");
				if(left) c1.sendCommands("RotateLeft");
				if(right) c1.sendCommands("RotateRight");
				if(shoot) c1.sendCommands("Shoot");
			}
		};
		timer.start();
	}

	public static void SinglePlayer(Stage stage) {
		serverGap s1=new serverGap();
		s1.start();
		c1.ServerId=s1.s1.ServerId;
		c1.id=s1.s1.addPlayer();
		ClientLogic ai;
		for(int i=0;i<numOfAI;i++){
			ai=new ClientLogic();
			ai.ServerId=s1.s1.ServerId;
			ai.id=s1.s1.addPlayer();
			AIs.add(ai);
			(new AiController(ai,c1)).start();
		}
		
		GameWindow newGame = new GameWindow(stage,c1);
	}




	public static void MultiPlayer(Stage stage) {
		serverGap s1=new serverGap();
		s1.start();
		RoomServer roomServer=new RoomServer();
		roomServer.run();
		c1.createRoom();
		c1.getRoomList();
		for(int i=0;i<c1.rooms.size();i++) {
			Room r1=c1.rooms.get(i);
			System.out.println("1:"+r1.roomId);
			for(int u=0;u<r1.ClientId.size();u++)
				System.out.println(":"+r1.ClientId.get(u));
		}
		System.out.println("init succeed");
		GameWindow newGame = new GameWindow(stage,c1);


	}

	public static class serverGap extends Thread {
		public ServerLogic s1=new ServerLogic();
		@Override
		public void run() {
			s1.init();
			System.out.println("Server thread " + c1.ServerId + " is running, controling bot "+c1.id);
			while (true) {
				try {
					s1.dealCommmands();
					if (s1.status == 2) {
						s1.broadcastEntities();
					}
					Thread.currentThread().sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}