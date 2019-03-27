package game;

import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.*;

import game.ai.AiController;
import game.audio.AudioPlayer;
import game.controller.Login;
import game.dao.UserDao;
import game.entity.Player;
import game.entity.User;
import game.graphics.GameWindow;
import game.graphics.Renderer;
import game.network.Port;
import game.network.Room;
import game.network.RoomServer;
import game.network.common.IPSearcher;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// loop that runs continuously to update every component of the game
public class Main extends Application {
	public static AudioPlayer audioPlayer = new AudioPlayer();
	public static User user;
	public static Stage mainStage;
	public static boolean isRunning = false;
	public static ClientLogic c1;
	public String username = "";
	public static RoomServer roomServer=new RoomServer();
	public static UserDao ud = new UserDao();


	boolean forward, backward, left, right, shoot;
	public static List<ClientLogic> AIs=new ArrayList<>();
	public static int numOfAI=8;

	public static boolean  startFlag =false;//can not purchase before game start

	public static void main(String args[]) {
		roomServer.run();
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
		primaryStage.getIcons().add(Renderer.icon);
		primaryStage.setTitle(Constants.GAME_NAME);


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
		String sceneFile = "graphics/fxml/login.fxml";
		Parent root = null;
		URL url  = null;
		try
		{
			url  = getClass().getResource( sceneFile );
			FXMLLoader fxmlLoader = new FXMLLoader(url);
			root = fxmlLoader.load();
			Login loginController = fxmlLoader.getController();
			setUser(loginController.getUser());
//			System.out.println( "  fxmlResource = " + sceneFile );
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

		primaryStage.setScene(scene);
		primaryStage.setWidth(Constants.CANVAS_WIDTH);
		primaryStage.setHeight(Constants.CANVAS_HEIGHT);

		primaryStage.setFullScreen(true);
		primaryStage.show();
		audioPlayer.playBackgroundMusic();

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
					case K:if(startFlag){
						c1.mallShow = !c1.mallShow; break;
					}
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
					case H:	 if(startFlag){
						c1.buySomething("Health"); break;
					}
					case M:	if(startFlag){
						c1.buySomething("Speed"); break;
					}
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
				if(shoot) {
					c1.sendCommands("Shoot");
//					audioPlayer.playShootSound();
				}
			}
		};
		timer.start();
	}

	public static void SinglePlayer(Stage stage){
		try {
			Port.localIP = InetAddress.getLocalHost().getHostAddress();
			Port.mulitcastAddress = "230.0.1.1";
			Port.serverAddress = "127.0.0.1";
		}catch (Exception e){
			e.printStackTrace();
		}
		c1=null;
		c1=new ClientLogic();
		c1.init();
		Random r = new Random();
		c1.mapID=r.nextInt(2)+1;
		isRunning = true;


		serverGap s1=new serverGap();
		s1.start();
//		p.name=user.getUsername();//player name
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
			p = (Player)s1.s1.SearchEntityById(ai.id);
			p.name="AI_NO."+i+1;
			AIs.add(ai);
			(new AiController(ai,c1)).start();
		}
		c1.ServerId=s1.s1.ServerId;
		c1.id=s1.s1.addPlayer();
		c1.diePlayer=s1.s1.diePlayer;
		c1.s1=s1.s1;
		c1.mallShow=false;
		c1.user=user;
		startFlag = true;
		Player p = (Player)s1.s1.SearchEntityById(c1.id);
		p.name="YOU";
		GameWindow.start(stage,c1);
	}

	public static void MultiPlayer(Stage stage) {
		Port.localIP = IPSearcher.goldenaxeAddress();
		c1 = null;
		c1 = new ClientLogic();
		System.out.println(c1.c1);
		c1.singleFlag=false;
		c1.getRoomList();
		waitForGame w1=new waitForGame(stage);
		w1.start();

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
						c1.init();
						c1.mapID=r.mapID;
						isRunning = true;
						System.out.println(r.ServerIp);
						System.out.println(Port.localIP);

						if (r.ServerIp.equals(Port.localIP)) {
							serverGap s1 = new serverGap();
							s1.s1.mapID=r.mapID;
							s1.mode=2;
							s1.start();
							s1.s1.ServerId = c1.getMyRoom();


							Thread.sleep(1000);
							for(Map.Entry<Integer,String> ids:r.Clients.entrySet()){
								s1.s1.addPlayer(ids.getKey(),ids.getValue());
							}

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
			s1.mapID=c1.mapID;
			s1.init();
			System.out.println("Server thread " + s1.ServerId+ " is running");
			int t=0;
			while (true) {
				if(!Main.isRunning)
					break;
				try {
					s1.dealCommmands();
					if (s1.status == 2&&t==3) {
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
			System.out.println("Server Thread "+s1.ServerId + " stopped");
			s1=null;
			
		}
	}


	public static void soundButtons(ToggleButton toggleMusic, ToggleButton toggleSound) {
		int musicVolume;
		if((int) Main.audioPlayer.getMusicVolume() == 0) {
			musicVolume = 60;
		} else {
			musicVolume = (int)Main.audioPlayer.getMusicVolume();
		}

		int soundVolume;
		if((int)Main.audioPlayer.getSoundEffectVolume() == 0) {
			soundVolume = 60;
		} else {
			soundVolume = (int)Main.audioPlayer.getSoundEffectVolume();
		}

		if(Main.audioPlayer.getMusicVolume() == 0) {
			toggleMusic.setSelected(true);
		} else {
			toggleMusic.setSelected(false);
		}
		if(Main.audioPlayer.getSoundEffectVolume() == 0) {
			toggleSound.setSelected(true);
		} else {
			toggleSound.setSelected(false);
		}
		toggleMusic.setOnAction(event -> {
			if(toggleMusic.isSelected()) {
				Main.audioPlayer.muteBackgroundMusic();
			} else {
				Main.audioPlayer.setMusicVolume(musicVolume);
			}
		});

		toggleSound.setOnAction(event -> {
			if(toggleSound.isSelected()) {
				Main.audioPlayer.muteSoundEffect();
			} else {
				Main.audioPlayer.setSoundEffectVolume(soundVolume);
			}
		});
	}
}