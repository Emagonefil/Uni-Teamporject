package game;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import game.entity.*;

import game.network.Port;
import game.network.Room;
import game.network.client.*;
import game.network.Room;

import javax.naming.ldap.SortKey;

public class ClientLogic {
	public int id=0;
	Client c1=new Client();
	public List<Entity> Entities= new ArrayList<Entity>();
	public int ServerId=0;
	List<String> Room = new ArrayList<String>();
	public List<Room> rooms = new ArrayList();
	ClientSender sender1= c1.getSender();;
	private int myRoom;
	long[] freezetime={System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis()};
	public void init() {

		c1.startReceiver(new Receivable() {
			@Override
			public void receive(Object o) {
				try {
						Entities = (List<Entity>) o;

				}
				catch (Exception e){
//					String command= (String) o;

				}
			}

		});
	}
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
	public void listWalls(){
		System.out.println("Walls:");
		Entity e;
		for(int i=0;i<Entities.size();i++){
			e =Entities.get(i);
			if(e.type.equals("Wall")){
				System.out.println("id: "+e.getId()+" Pos: "+e.getPosition());
			}
		}
	}
	public void listBullets(){
		System.out.println("Bullets:");
		Entity e;
		for(int i=0;i<Entities.size();i++){
			e =Entities.get(i);
			if(e.type.equals("Bullet")){
				System.out.println("id: "+e.getId()+" Pos: "+e.getPosition()+" Angle: "+e.getAngle());
			}
		}
	}
	public List<Entity> getEntities(){
		return this.Entities;
	}
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
			if(freezetime[4]+1000<System.currentTimeMillis()) {
				sender1.send(Port.serverAddress, ServerId + "," + this.id + "," + c);
				freezetime[4]=System.currentTimeMillis();
			}
		//sender1.send(Port.serverAddress,ServerId+","+this.id+","+c);
		//System.out.println("Client sent: "+ServerId+","+this.id+","+c);
	}
	public void getRoomList(){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,list");
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			rooms=(List<Room>)in.readObject();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void createRoom(){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,create");
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			getRoomList();
			int t=(int) in.readObject();
			while(true){
				try {
					id = findRoom((t)).ClientId.get(0);
					break;
				}catch (Exception e){

				}
			}
			myRoom=t;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void joinRoom(int roomNum){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,join"+roomNum);
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			id=findRoom(((int)in.readObject())).ClientId.get(0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void leaveRoom(){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,leave");
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			myRoom=0;
			id=0;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public Room  findRoom(int id){
		for(int i=0;i<rooms.size();i++){
			if(rooms.get(i).roomId==id)
				return rooms.get(i);
		}
		return null;
	}
	public void startGame(){
		try{
			Socket socket=new Socket(Port.roomServerAddress,Integer.parseInt(Port.roomPort));
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,start"+myRoom+","+ InetAddress.getLocalHost().getHostAddress());
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public int getMyRoom(){
		return myRoom;
	}
	}
