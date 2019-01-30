package game;
import java.util.ArrayList;
import java.util.List;
import game.entity.*;

import goldenaxe.network.client.*;
public class ClientLogic {
	public int id;
	Client c1;
	List<Entity> Entities= new ArrayList<Entity>();
	String ServerIp="127.0.0.1";
	List<String> Room = new ArrayList<String>();
	public void init() {
		c1 = new Client();
		c1.startReceiver(new Receivable() {

			@Override
			public void receive(List list) {
					Entities = list;
			}
			
		});
	}
	public List<Entity> getEntities(){
		return this.Entities;
	}
	public void sendCommands(String c) {
		ClientSender sender1 = c1.getSender();
		sender1.send(ServerIp,c);
	}
	public String searchRoom(){
		return "";
	}
	
}
