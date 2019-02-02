package game;
import java.util.ArrayList;
import java.util.List;
import game.entity.*;

import game.network.Port;
import game.network.client.*;
public class ClientLogic {
	public int id;
	Client c1=new Client();
	public List<Entity> Entities= new ArrayList<Entity>();
	String RoomServerIp="192.168.191.1";
	public int ServerId;
	List<String> Room = new ArrayList<String>();
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
	public String searchRoom(){
		return "";
	}
	}
