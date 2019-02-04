package game;
import game.entity.*;

import java.util.*;
import game.ServerLogic.*;
import game.ClientLogic.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// loop that runs continuously to update every component of the game
public class Main extends Application {

	public static void main(String args[]) {
//		MultiPlayer(true);
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
				MultiPlayer(true,primaryStage);
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
		ClientLogic c1 = new ClientLogic();
//		c1.init();
//		System.out.println("Set serverid:");
//		c1.ServerId=scanner.nextInt();
//		System.out.println("Set clientid:");
//		c1.id=scanner.nextInt();
//		System.out.println("init succeed");

		GameWindow newGame = new GameWindow(stage,c1);
	}
	public static void MultiPlayer(boolean iflocal, Stage stage) {
		int id;
		Scanner scanner= new Scanner(System.in);
		if(iflocal) {
			serverGap s1=new serverGap(1);
			s1.start();
		}
		ClientLogic c1 = new ClientLogic();
		c1.init();
		System.out.println("Set serverid:");
		c1.ServerId=scanner.nextInt();
		System.out.println("Set clientid:");
		c1.id=scanner.nextInt();
		System.out.println("init succeed");
		//try{Thread.sleep(2000);}
//		catch (Exception e){
//			e.printStackTrace();
//		}
		GameWindow newGame = new GameWindow(stage,c1);

		while(true) {
			String s2=scanner.next();
			if(s2.equals("q")){
				c1.listBullets();
				c1.listPlayers();
				c1.listWalls();
				System.out.println(c1.Entities.size());
			}

			c1.sendCommands(s2);
		}
	}
	public static class serverGap extends Thread {
		private Thread t;
		private int id;

		serverGap(int tid) {
			this.id=tid;
		}
		@Override
		public void run() {
			System.out.println("Server thread " + id + " is running");
			ServerLogic s1=new ServerLogic();
			s1.init();

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