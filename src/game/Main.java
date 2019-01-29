package game;
import game.entity.*;


import java.util.*;
import game.ServerLogic.*;
import game.ClientLogic.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;



// loop that runs continuously to update every component of the game
public class Main extends Application {
	public static void main(String args[]) {
		launch(args);
		//MultiPlayer(true);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle(Constants.GAME_NAME + " " + Constants.GAME_VERSION);
		primaryStage.setMaximized(true);

		// add the label, text field and button
		VBox vbox = new VBox();

		// set spacing
		vbox.setSpacing(20);

		// set alignment for the HBox
		vbox.setAlignment(Pos.CENTER);

		// create a label
		Label label = new Label(Constants.GAME_NAME + " " + Constants.GAME_VERSION);

		// create mute button
//		Button sound = new Button("Sound");

		// create single player button
		Button single = new Button("Single Player");
		// create multiplayer button
		Button multi = new Button("Multiplayer");
		// create settings button
		Button settings = new Button("Settings");
		// create quit button
		Button quit = new Button("Quit");

		// add event to exit game when quit is pressed
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Exit");
				System.exit(0);
			}
		});

		// add event to start single player when single player is pressed
		single.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				newGameWindow(primaryStage);
				SinglePlayer();
			}
		});

		// add event to start multiplayer when multiplayer is pressed
		multi.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				newGameWindow(primaryStage);
				MultiPlayer(true);
			}
		});

		// add event to open settings when settings is pressed



		vbox.getChildren().addAll(label,single,multi,settings,quit);
		// create a scene
		Scene scene = new Scene(vbox);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());


		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void newGameWindow(Stage primaryStage) {
		Group root = new Group();
		primaryStage.getScene().setRoot(root);

		primaryStage.show();
	}

	public static void SinglePlayer() {
		ServerLogic s1=new ServerLogic();
		s1.init();
		Gap g1= new Gap(1);
		g1.start();
	}

	public static void MultiPlayer(boolean iflocal) {
		int id;
		if(iflocal) {
			ServerLogic s1=new ServerLogic();
			s1.init();
			Gap g1= new Gap(1);
			g1.start();
		}
		ClientLogic c1 = new ClientLogic();
		c1.init();
		id=(int)System.currentTimeMillis();
		c1.sendCommands(String.valueOf(id)+",JoinServer");
	}

	public static class Gap extends Thread {
	   private Thread t;
	   private int id;
	   
	   Gap(int tid) {
	      this.id=tid;
	   }
	   
	   public void run(ServerLogic s) {
		  System.out.println("Server thread " + String.valueOf(id)+" is running" );
         while(true) {
	        	 try {
		            s.dealCommmands();
		            Thread.sleep(50);
	        	 }
	        	 catch(Exception e){
	        		 e.printStackTrace();
	        	 }
	         }
	   }
	   
	   public void start () {
	      if (t == null) {
	         t = new Thread (this,String.valueOf(id));
	         t.start ();
	      }
	   }
	}
}