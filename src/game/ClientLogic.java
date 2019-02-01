package game;
import java.util.ArrayList;
import java.util.List;
import game.entity.*;

import goldenaxe.network.client.*;
public class ClientLogic {
	public int id;
	Client c1=new Client();
	public List<Entity> Entities= new ArrayList<Entity>();
	String ServerIp="127.0.0.1";
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
					String command= (String) o;

				}
			}
			
		});
	}
	public List<Entity> getEntities(){
		return this.Entities;
	}
	public void sendCommands(String c) {
		ClientSender sender1 = c1.getSender();
		sender1.send(ServerIp,ServerId+","+this.id+","+c);
		System.out.println("Client sent: "+ServerId+","+this.id+","+c);
	}
	public String searchRoom(){
		return "";
	}
	
}
