package game;
import game.entity.*;
import goldenaxe.network.server.*;
import java.math.*;
import java.util.regex.*;
import game.entity.*;
import java.util.*;
public class ServerLogic {
	List<Entity> Entities;
	List<String> Commands;
	Server server;
	public void init() {
		this.server = new Server();
		initMap();
	}
	public void initMap() {
		for(int i=0;i<(int)Math.random();i++) {
			this.Entities.add(new Player(10,10,new Point((float)Math.random(),(float)Math.random())));
		}
	}
	public Entity SearchEntityById(int id) {
		for(int i=0;i<Entities.size();i++) {
			if(Entities.get(i).getId()==id)
				return Entities.get(i);
		}
		return null;
	}
	public void dealCommmands() {
		this.Commands = server.getMoves();
		Iterator it1 = Commands.iterator();
		String[] arrs;
		Player e1;
		while(it1.hasNext()) {
			arrs = ((String)it1.next()).split(",");
			if((e1=(Player)SearchEntityById(Integer.parseInt(arrs[0])))!=null) {
				switch(arrs[1]) {
				case "Forward":e1.forward();
				case "Fackward":e1.backwards();
				case "RotateRight":e1.rotateRight();
				case "RotateLeft":e1.rotateLeft();
				case "Shoot": this.Entities.add(new Bullet(1,1,e1.getPosition()));
				}
			}
			else {
				if(arrs[1].equals("JoinServer")) {
					Entities.add(new Player(10,10,new Point()));
				}
			}
		}
		this.Commands = null;
	}
	public void broadcastEntities() {
		server.sendBroadcast(this.Entities);
	}
}
