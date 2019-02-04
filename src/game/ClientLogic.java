package game;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import game.entity.*;

import game.network.Port;
import game.network.Room;
import game.network.client.*;
import game.network.Room;
public class ClientLogic {
	public int id;
	Client c1=new Client();
	public List<Entity> Entities= new ArrayList<Entity>();
	String RoomServerIp="192.168.191.1";
	public int ServerId;
	List<String> Room = new ArrayList<String>();
	Socket socket;
	List<Room> rooms;
	int myRoom;
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
				//System.out.println("接受："+System.currentTimeMillis());
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
		ClientSender sender1 = c1.getSender();
		sender1.send(Port.serverAddress,ServerId+","+this.id+","+c);
		System.out.println("Client sent: "+ServerId+","+this.id+","+c);
	}
	public void getRoomList(){
		try{
			socket=new Socket(Port.roomServerAddress,9999);
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
			socket=new Socket(Port.roomServerAddress,9999);
			PrintStream ps=new PrintStream(socket.getOutputStream());
			ps.println("Room,create");
			ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
			id=findRoom(((int)in.readObject())).ClientId.get(0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void joinRoom(int roomNum){
		try{
			socket=new Socket(Port.roomServerAddress,9999);
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
			socket=new Socket(Port.roomServerAddress,9999);
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
	}
