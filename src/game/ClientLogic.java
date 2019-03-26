package game;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import game.dao.UserDao;
import game.entity.*;

import game.entity.items.HealthPickup;
import game.entity.items.Item;
import game.entity.items.SpeedPickup;
import game.network.Command;
import game.network.Port;
import game.network.Room;
import game.network.client.*;
import game.network.Room;
import game.network.common.IPSearcher;

import javax.naming.ldap.SortKey;

import static game.Main.ud;

public class ClientLogic {
	public int id=0;
	public String name=Main.user.getUsername();
	Client c1=new Client();
	public List<Entity> Entities= new ArrayList<Entity>();
	public List<Player> diePlayer= new ArrayList<Player>();
	public int ServerId=0;
	List<String> Room = new ArrayList<String>();
	public List<Room> rooms = new ArrayList();
	ClientSender sender1= c1.getSender();
	private int myRoom;
	public int mapID;
	long[] freezetime={System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis()};
	public User user;
	public ServerLogic s1;
	public boolean singleFlag = true;//true 单人游戏 false
	public boolean addpointFlag =true;//Only one additional point mark can be added for each game.
	public boolean mallShow =false;

	/**
	 * init the client and start listening the port
	 */
	public void init() {
		c1 = new Client();
		diePlayer = new ArrayList<>();
		Entities = new ArrayList<>();
		ServerId = 0;
		sender1 = c1.getSender();

		c1.startReceiver(new Receivable() {
			@Override
			public void receive(Object o) {

				try {
						Entities = (List<Entity>) o;
//						Player you = (Player)getEntityByID(id);
//						you.name="YOU";

				}
				catch (Exception e){
//					String command= (String) o;

				}

			}

		});
	}

	/**
	 * list all players in entity list
	 */
	public void listPlayers(){
		System.out.println("Players:");
		Entity e;
		for(int i=0;i<Entities.size();i++){
			e =Entities.get(i);
			if(e.type.equals("Player")){
				System.out.println("id: "+e.getId()+" Pos: "+e.getPosition()+" Angle: "+e.getAngle()+" HP: "+((Player)e).getHealth());
			}
		}
	}

	/**
	 * let other modules get entity list
	 * @return current entity list
	 */
	public List<Entity> getEntities(){
		return this.Entities;
	}

	/**
	 * send commands to server
	 * @param c command
	 */
	public void sendCommands(String c) {
		if(id==0||ServerId==0)
			return;
		if(c.equals("Forward"))
			if(freezetime[0]+10<System.currentTimeMillis()) {
				sender1.send(Port.serverAddress, ServerId + "," + this.id + "," + c);
				freezetime[0]=System.currentTimeMillis();
			}
		if(c.equals("Backward"))
			if(freezetime[1]+10<System.currentTimeMillis()) {
				sender1.send(Port.serverAddress, ServerId + "," + this.id + "," + c);
				freezetime[1]=System.currentTimeMillis();
			}
		if(c.equals("RotateLeft"))
			if(freezetime[2]+10<System.currentTimeMillis()) {
				sender1.send(Port.serverAddress, ServerId + "," + this.id + "," + c);
				freezetime[2]=System.currentTimeMillis();
			}
		if(c.equals("RotateRight"))
			if(freezetime[3]+10<System.currentTimeMillis()) {
				sender1.send(Port.serverAddress, ServerId + "," + this.id + "," + c);
				freezetime[3]=System.currentTimeMillis();
			}
		if(c.equals("Shoot"))
			if(freezetime[4]+500<System.currentTimeMillis()) {
				sender1.send(Port.serverAddress, ServerId + "," + this.id + "," + c);
				freezetime[4]=System.currentTimeMillis();
			}
		//sender1.send(Port.serverAddress,ServerId+","+this.id+","+c);
		//System.out.println("Client sent: "+ServerId+","+this.id+","+c);
	}

	/**
	 * get data from room server and refresh the room list
	 */
	public void getRoomList(){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println(Command.roomList);
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			rooms=(List<Room>)in.readObject();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * send data to room server to create a new room
	 */
	public void createRoom(){
		try{
			int oldId=id;
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println(Command.roomCreate+name);
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			getRoomList();
			String t=(String) in.readObject();
			String[] sp = t.split(",");
			while(true){
				try {
					id = Integer.parseInt(sp[0]);
					break;
				}catch (Exception e){
					e.printStackTrace();
				}
			}

			myRoom=Integer.parseInt(sp[1]);


			leaveRoom(oldId);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * send data to room server to join a room
	 * @param roomNum the room's id that's going to join
	 */
	public void joinRoom(int roomNum){
		try{
			int oldId=id;
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println(Command.roomJoin+roomNum+","+name);
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			getRoomList();
			Object t=in.readObject();
			while(true){
				try {
					id = (int)t;
					myRoom=roomNum;
					break;
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			leaveRoom(oldId);
		}catch (Exception e){
		}
	}

	/**
	 * leave the current room
	 */
	public void leaveRoom(int id){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println(Command.roomLeave+id);
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * search a room by room's id
	 * @param id room's id
	 * @return the room or null
	 */
	public Room  findRoom(int id){
		for(int i=0;i<rooms.size();i++){
			if(rooms.get(i).getRoomId()==id)
				return rooms.get(i);
		}
		return null;
	}

	/**
	 * inform the room server to start the game
	 */
	public void startGame(){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,start"+myRoom+","+ Port.localIP);
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * get the current room that client is in
	 * @return room's id
	 */
	public int getMyRoom(){
		return myRoom;
	}

	/**
	 * search a specific entity in entity list by id
	 * @param id entity's id
	 * @return the entity or null
	 */
	public Entity getEntityByID(int id) {
		for(int i=0;i<Entities.size();i++) {
			if(Entities.get(i).id==id)
				return Entities.get(i);
		}
		return null;
	}

	public void buySomething(String something){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(singleFlag){
					if(mallShow){
						if(something.equals("Health")){

							if(user.getPoint()>=5){
								user.setPoint(user.getPoint()-5);
								if(ud.userUpdatePoint(user)) {
									Item i = new HealthPickup(new Point());
									i.effect((Player) s1.SearchEntityById(id));
									System.out.println("Purchase life success!");
								}
							}else{
								System.out.println("Not enough points!");
							}
						}
						if(something.equals("Speed")){
							if(user.getPoint()>=5){
								user.setPoint(user.getPoint()-5);
								if(ud.userUpdatePoint(user))
								{
									System.out.println("Purchase speed success!");
									Item i =new SpeedPickup(new Point());
									i.effect((Player)s1.SearchEntityById(id));
								}
							}else{
								System.out.println("Not enough points!");
							}
						}
					}
				}
			}
		}).start();
	}


	}


