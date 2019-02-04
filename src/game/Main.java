package game;
import com.sun.javafx.iio.common.PushbroomScaler;
import game.entity.*;

import java.util.*;
import game.ServerLogic.*;
import game.ClientLogic.*;
import game.network.Room;
import game.network.RoomServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// loop that runs continuously to update every component of the game
public class Main extends Application {
	public static ClientLogic c1 = new ClientLogic();

	public static void main(String args[]) {
		c1.init();
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		// set the window title
		primaryStage.setTitle(Constants.GAME_NAME + " " + Constants.GAME_VERSION);

		// set the window to be maximized (fullscreen)
		primaryStage.setMaximized(true);

		// create a vbox
		VBox vbox = new VBox();

		// set VBox spacing
		vbox.setSpacing(20);

		// set VBox alignment
		vbox.setAlignment(Pos.CENTER);

		// create a label for the name of the game
		Label label = new Label(Constants.GAME_NAME + " " + Constants.GAME_VERSION);

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
				MultiPlayer(primaryStage);
			}
		});

		// create a setting button
		Button settings = new Button("Settings");

		// add event to open settings when settings button is pressed
		settings.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// create content for the settings page
				VBox settingsBox = new VBox();

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

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				switch (keyEvent.getCode()){
					case W:c1.sendCommands("Forward");break;
					case S:c1.sendCommands("Backward");break;
					case A:c1.sendCommands("RotateLeft");break;
					case D:c1.sendCommands("RotateRight");break;
					case J:c1.sendCommands("Shoot");break;
				}
			}
		});
		vbox.getChildren().addAll(label,single,multi,settings,quit);

		// create a scene
		Scene scene = new Scene(vbox);

		// add style sheet to this scene
		scene.getStylesheets().addAll(this.getClass().getResource("gui/style.css").toExternalForm());

		// add scene to stage and display stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void SinglePlayer(Stage stage) {
		ServerLogic s1=new ServerLogic();
		s1.init();
		int id;
		Scanner scanner= new Scanner(System.in);
//		c1.init();
//		System.out.println("Set serverid:");
//		c1.ServerId=scanner.nextInt();
//		System.out.println("Set clientid:");
//		c1.id=scanner.nextInt();
//		System.out.println("init succeed");

		GameWindow newGame = new GameWindow(stage,c1);
	}
	public static void MultiPlayer(Stage stage) {
		Scanner scanner= new Scanner(System.in);
		serverGap s1=new serverGap();
		s1.start();
//		RoomServer roomServer=new RoomServer();
//		roomServer.run();
//		c1.createRoom();
//		c1.getRoomList();
//		for(int i=0;i<c1.rooms.size();i++) {
//			Room r1=c1.rooms.get(i);
//			System.out.println("1:"+r1.roomId);
//			for(int u=0;u<r1.ClientId.size();u++)
//				System.out.println(":"+r1.ClientId.get(u));
//		}
		System.out.println("init succeed");
		//try{Thread.sleep(2000);}
//		catch (Exception e){
//			e.printStackTrace();
//		}
		GameWindow newGame = new GameWindow(stage,c1);


	}

	public static class serverGap extends Thread {
		private Thread t;
		public int sid;
		public int cid;
		@Override
		public void run() {
			ServerLogic s1=new ServerLogic();
			s1.init();
			this.sid=s1.ServerId;
			this.cid=s1.getPlayerId();
			c1.ServerId=this.sid;
			c1.id=this.cid;
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