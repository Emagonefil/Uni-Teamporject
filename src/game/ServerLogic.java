package game;
import game.entity.*;
import game.entity.collisions.*;
import game.network.Port;
import game.network.server.*;

import java.io.Serializable;
import java.math.*;
import java.util.regex.*;
import game.entity.*;
import java.util.*;
public class ServerLogic {
	List<Entity> Entities=new ArrayList<Entity>();
	List<String> Commands=new ArrayList<String>();
	Server server= new Server();
	Random ra = new Random();
	public int status=0;
	public int ServerId= ra.nextInt(99999)+1;
	public void init() {
		initMap();
		this.status=1;
		System.out.println("ServerId: "+this.ServerId);
		this.status=2;
	}
	public void initMap() {
		int num=ra.nextInt(30)+10;
		for(int i=0;i<num;i++) {
			while (true) {
				Wall w;
				double x=ra.nextInt(635) + 5;
				double y=ra.nextInt(635) + 5;
				w = new Wall(40, 40, new Point((float)x, (float) y));
				int l=checkColision(w);
				//System.out.println(l);
				if (l== 0) {
					w.id = getSpareId();
					Entities.add(w);
					break;
				}
			}
		}
		for(int i=0;i<2;i++) {
			while (true) {
				Player w;
				double x=ra.nextInt(635) + 5;
				double y=ra.nextInt(635) + 5;
				w = new Player(40, 40, new Point((float)x, (float) y));
				int l=checkColision(w);
				//System.out.println(l);
				if (l== 0) {
					w.id = getSpareId();
					Entities.add(w);
					break;
				}
			}
		}
		listPlayers();
	}
	public int getPlayerId(){
		Entity e;
		for(int i=0;i<Entities.size();i++){
			e =Entities.get(i);
			if(e.type.equals("Player")){
				return ((Player)e).id;
			}
		}
		return 0;
	}
	public void listPlayers(){
		System.out.println("Server info");
		Entity e;
		for(int i=0;i<Entities.size();i++){
			e =Entities.get(i);
			if(e.type.equals("Player")){
				System.out.println("id: "+e.getId()+" Pos: "+e.getPosition()+" Angle: "+e.getAngle());
			}
		}
	}
	public int getSpareId(){
		int id=ra.nextInt(9999)+1;
		if (Entities.size()!=0)
		while(true){
			int t=0;
			id=ra.nextInt(9999)+1;
			for(int i=0;i<Entities.size();i++) {
				//System.out.println(i+" "+Entities.size()+" "+id+" "+Entities.get(i).getId());
				if(Entities.get(i).getId()==id){
					t=1;
					break;
				}
			}
			//System.out.println(t);
			if(t==0)
				break;
		}
		return id;
	}
	public Point[] getCorner(Entity e){
		if(e.type.equals("Player"))
			return ((Player)e).getCorners();
		if(e.type.equals("Wall"))
			return ((Wall)e).getCorners();
		if (e.type.equals("Bullet"))
			return ((Bullet)e).getCorners();
		return null;
	}
	public int checkColision(Entity e){
		Point[] p1= getCorner(e);
		Point[] p2;
		for(int i=0;i<Entities.size();i++) {
			Entity e2 = Entities.get(i);
			p2=getCorner(e2);
			if(CollisionDetection.isTouching(p1,p2))
				return e2.id;
			else
				return 0;

		}
		return 0;
	}
	public void addPlayer(int id){
		this.Entities.add(new Player(10,10,new Point((float)ra.nextInt(635)+5,(float)ra.nextInt(635)+5)));
	}
	public Entity SearchEntityById(int id) {
		for(int i=0;i<Entities.size();i++) {
			if(Entities.get(i).getId()==id)
				return Entities.get(i);
		}
		return null;
	}
	public void moveBullets(){
		Entity e;
		for(int i=0;i<Entities.size();i++) {
			e=Entities.get(i);
			if(e.type.equals("Bullet"))
				((Bullet)e).forward();
			else
				continue;
			if(e.getPosition().getX()<0||e.getPosition().getX()>640||e.getPosition().getY()<0||e.getPosition().getY()>650) {
				System.out.println(((Bullet)e).getPosition().getX()+","+((Bullet)e).getPosition().getY());
				Entities.remove(e);
				continue;
			}
			Entity e2;
			boolean live=true;
			for(int t=0;t<Entities.size();t++){
				e2=Entities.get(t);
				switch (e2.type) {
					case "Wall":
						if(CollisionDetection.isTouching(((Bullet)e).getCorners(),((Wall)e2).getCorners()))
							Entities.remove(e);
						live=false;
						break;
					case "Player":
						if(CollisionDetection.isTouching(((Bullet)e).getCorners(),((Player)e2).getCorners())) {
							((Player) e2).reduceHealth(((Bullet) e).damage);
							Entities.remove(e);
						}
						live=false;
						break;
				}
				if(!live)
					break;
			}

		}
	}
	public void dealCommmands() {
		this.Commands = server.getMoves();
		Iterator it1 = Commands.iterator();
		String[] arrs;
		Player e1;
		while(it1.hasNext()) {
			String cmd=(String)it1.next();
			System.out.println(cmd);
			arrs = cmd.split(",");
			if((Integer.parseInt(arrs[0]))==this.ServerId){
				e1=(Player)SearchEntityById(Integer.parseInt(arrs[1]));
				if(e1!=null) {
					switch(arrs[2]) {
					case "Forward": {
						e1.forward();
						if (checkColision(e1) != 0)
							e1.backwards();
						break;
					}
					case "Backward":{
						e1.backwards();
						if (checkColision(e1)!=0)
							e1.forward();
						break;
					}
					case "RotateRight":{
						e1.rotateRight();
						if (checkColision(e1)!=0)
							e1.rotateLeft();
						break;
					}
					case "RotateLeft": {
						e1.rotateLeft();
						if (checkColision(e1) != 0)
							e1.rotateRight();
						break;
					}
					case "Shoot": {
						Bullet b = new Bullet(1, 1,new Point(e1.getPosition().getX(),e1.getPosition().getY()));
						b.setAngle(e1.getAngle());
						b.id = getSpareId();
						this.Entities.add(b);
						break;
					}
					}
					//listPlayers();
				}
				else {
					if(arrs[2].equals("JoinServer")) {
						Player p=new Player(10,10,new Point());
						p.id=Integer.parseInt(arrs[1]);
						Entities.add(p);
					}
				}

			}
			else {
				System.out.println("invalid command");
			}
		}
		this.Commands = null;
		moveBullets();
	}

	public void broadcastEntities() {
		server.send(Port.clientAddress,Entities);
		//System.out.println("发送："+System.currentTimeMillis());
	}

}
