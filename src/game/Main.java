package game;

import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.*;

import game.ai.AiController;
import game.controller.Login;
import game.entity.Player;
import game.entity.User;
import game.network.Port;
import game.network.Room;
import game.network.RoomServer;
import game.network.client.Receivable;
import game.network.common.IPSearcher;
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
	private static User user;
	public static Stage mainStage;
	public static boolean isRunning = false;
	public static ClientLogic c1 = new ClientLogic();
	public String username = "";
	boolean forward, backward, left, right, shoot;
	public static List<ClientLogic> AIs=new ArrayList<>();
	public static int numOfAI=4;


	public static void main(String args[]) {
//		Port.localIP = IPSearcher.goldenaxeAddress();
		try {
			Port.localIP = InetAddress.getLocalHost().getHostAddress();
			System.out.println(Port.localIP);
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("localIP: "+Port.localIP);
		launch(args);
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Main.user = user;
	}

	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		// set the window title
		primaryStage.setTitle(Constants.GAME_NAME + " " + Constants.GAME_VERSION);

		// set the window to be maximized (fullscreen)
		primaryStage.setMaximized(true);

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
		String sceneFile = "gui/login.fxml";
		Parent root = null;
		URL url  = null;
		try
		{
			url  = getClass().getResource( sceneFile );
			FXMLLoader fxmlLoader = new FXMLLoader(url);
			root = fxmlLoader.load();
			Login loginController = fxmlLoader.getController();
			setUser(loginController.getUser());
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

	public static void SinglePlayer(Stage stage){
//		Port.localIP=IPSearcher.goldenaxeAddress();
		try {
			Port.localIP = InetAddress.getLocalHost().getHostAddress();
		}catch (Exception e){
			e.printStackTrace();
		}
		c1.init();
		isRunning = true;

		serverGap s1=new serverGap();
		s1.start();
//		p.name=user.getUsername();//玩家名
		try{
			Thread.sleep(500);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		for(int i=0;i<numOfAI;i++){
			ClientLogic ai;
			ai=new ClientLogic();
			ai.ServerId=s1.s1.ServerId;
			ai.id=s1.s1.addPlayer();
			Player p;
			p = (Player)s1.s1.getEntityByID(ai.id);
			p.name="AI_NO."+i;
			AIs.add(ai);
			(new AiController(ai,c1)).start();
		}
		c1.ServerId=s1.s1.ServerId;
		c1.id=s1.s1.addPlayer();
		c1.diePlayer=s1.s1.diePlayer;
		Player p = (Player)s1.s1.getEntityByID(c1.id);
		p.name="YOU";
		GameWindow newGame = new GameWindow(stage, c1);

	}





	public static void MultiPlayer(Stage stage) {
		//if you are the room server, run these codes
		Port.localIP = IPSearcher.goldenaxeAddress();
//		c1.close();
		c1 = null;
		c1 = new ClientLogic();
//		c1.restartReciver();
//		c1.init();
		System.out.println(c1.c1);
		RoomServer roomServer=new RoomServer();
		roomServer.run();

		c1.getRoomList();
		//c1.createRoom();
		waitForGame w1=new waitForGame(stage);
		w1.start();
//		GameWindow newGame = new GameWindow(stage,c1);

	}

	public static class waitForGame extends Thread{
		Stage stage;
		waitForGame(Stage stage){
			this.stage=stage;
		}
		@Override
		public void run() {
			int t=0;
			Room r=new Room();
			while(true) {
				try {
					if(isRunning)
						break;
					Thread.sleep(100);
					c1.getRoomList();
					//Thread.sleep(100);
					r = c1.findRoom(c1.getMyRoom());
					if(r==null)
						continue;
					//System.out.println(r.status+" "+r.ServerIp + " " );
					if (r.ServerIp != null&&r.status == 2 && r.ServerIp != "") {
						Port.mulitcastAddress = r.RoomIP;
						System.out.println("sadgaskldfmoijgoiehigdf"+Port.mulitcastAddress);
//						c1.close();
						c1.init();
						isRunning = true;
						if (r.ServerIp.equals(Port.localIP)) {
							serverGap s1 = new serverGap();
							s1.mode=2;
							s1.start();
							s1.s1.ServerId = c1.getMyRoom();
							System.out.println(r.ClientId);
							Thread.sleep(1000);
							for (int id : r.ClientId) {
								s1.s1.addPlayer(id);

//								p.name=user.getUsername();//玩家名
//								p.name = String.valueOf(id);

							}
							System.out.println(r.ServerIp);
							c1.ServerId = c1.getMyRoom();

							Port.serverAddress = r.ServerIp;
						}

						else if(r.status == 2){
							c1.ServerId = c1.getMyRoom();
							Port.serverAddress = r.ServerIp;
//							GameWindow newGame = new GameWindow(stage,c1);
						}
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class serverGap extends Thread {
		public ServerLogic s1=new ServerLogic();
		public int mode = 1;
		@Override
		public void run() {
			s1.init();
			System.out.println("Server thread " + s1.ServerId+ " is running");
			int t=0;
			while (true) {
				if(!Main.isRunning)
					break;
				try {
					s1.dealCommmands();
					if (s1.status == 2&&t==6) {
						s1.broadcastEntities();
						t=0;
					}
					Thread.currentThread().sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				t++;
			}
			if(mode==2){
				try{
					Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
					PrintStream ps=new PrintStream(socket.getOutputStream());
					ps.println("Room,endGame"+s1.ServerId);
					ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			s1.close();
			s1=null;
			System.out.println("Server Thread "+s1.ServerId + " stopped");
		}
	}
}