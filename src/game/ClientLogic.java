package game;
import java.util.ArrayList;
import java.util.List;
import game.entity.*;

import goldenaxe.network.client.*;
public class ClientLogic {
	Client c1;
	List<Entity> Entities = new ArrayList<>();
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
		sender1.send(c);
	}
	
}
