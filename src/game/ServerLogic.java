package game;
import game.entity.*;
import game.entity.collisions.*;
import game.network.Port;
import game.network.server.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.*;
import java.util.regex.*;
import game.entity.*;
import java.util.*;
public class ServerLogic {
	List<Entity> Entities=new ArrayList<Entity>();
	public List<Player> diePlayer= new ArrayList<Player>();
	List<String> Commands=new ArrayList<String>();
	Server server= new Server();
	Random ra = new Random();
	public int status=0;
	public int ServerId= 99999;
	public void init() {
		initMap();
		this.status=1;
		System.out.println("ServerId: "+this.ServerId);
		this.status=2;
	}
	public void initMap() {
		int num=ra.nextInt(30)+30;
		for(int i=0;i<num;i++) {
			while (true) {
				Wall w;
				double x=ra.nextInt((int)Constants.CANVAS_WIDTH/40)*40 ;
				double y=ra.nextInt((int)Constants.CANVAS_HEIGHT/40)*40 ;
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
		Entity e2;
		float x=e.getPosition().getX();
		float y=e.getPosition().getY();
		if((x-20<=0)||(x+20>=Constants.CANVAS_WIDTH)||(y-20<=0)||(y+20>=Constants.CANVAS_HEIGHT))
			return -1;
		for(int i=0;i<Entities.size();i++) {
			e2 = Entities.get(i);
			if(e2.equals(e))
				continue;
			p2=getCorner(e2);
			if(CollisionDetection.isTouching(p1,p2))
				return e2.id;
		}
		return 0;
	}
	public int addPlayer(int id){
		Player w;
		while (true) {
			double x = ra.nextInt((int)Constants.CANVAS_WIDTH/40)*40;
			double y = ra.nextInt((int)Constants.CANVAS_HEIGHT/40)*40;
			try {

				File config = new File("Resources/playerConfigs/basic.player");
				w = Player.fromFile(config.getAbsolutePath());

			} catch (IOException e) {

				w = new Player();

			}
			w.setPosition(new Point((float)x,(float)y));
			int l=checkColision(w);
			//System.out.println(l);
			if (l== 0) {
				w.id = id;
				Entities.add(w);
				break;
			}
		}
		RankService.getInstance().initPlayScore(w.id);
		return w.id;
	}
	public int addPlayer(){
		Player w;
		while (true) {
			double x = ra.nextInt((int)Constants.CANVAS_WIDTH/40)*40;
			double y = ra.nextInt((int)Constants.CANVAS_HEIGHT/40)*40;
			try {

				File config = new File("Resources/playerConfigs/basic.player");
				w = Player.fromFile(config.getAbsolutePath());

			} catch (IOException e) {

				w = new Player();
			}
			w.setPosition(new Point((float)x,(float)y));
			int l=checkColision(w);
			//System.out.println(l);
			if (l== 0) {
				w.id =getSpareId();
				Entities.add(w);
				break;
			}
		}
		RankService.getInstance().initPlayScore(w.id);
		return w.id;
	}
	public Entity SearchEntityById(int id) {
		for(int i=0;i<Entities.size();i++) {
			if(Entities.get(i).id==id)
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
			if(e.getPosition().getX()<0||e.getPosition().getX()>Constants.CANVAS_WIDTH||e.getPosition().getY()<0||e.getPosition().getY()>Constants.CANVAS_HEIGHT) {
				//System.out.println(((Bullet)e).getPosition().getX()+","+((Bullet)e).getPosition().getY());
				Entities.remove(i);
				continue;
			}
			Entity e2;
			boolean live=true;
			for(int t=0;t<Entities.size();t++){
				e2=Entities.get(t);
				switch (e2.type) {
					case "Wall":
						if(CollisionDetection.isTouching(((Bullet)e).getCorners(),((Wall)e2).getCorners())) {
							Entities.remove(i);
							live = false;
						}
						break;
					case "Player":
						if(((Bullet)e).owner!=((Player)e2).id)
						if(CollisionDetection.isTouching(((Bullet)e).getCorners(),((Player)e2).getCorners())) {
							((Player) e2).reduceHealth(((Bullet) e).damage);
							RankService.getInstance().addPlayScore(((Bullet)e).owner, ((Bullet) e).damage);
							Entities.remove(i);
							live=false;
							if(((Player) e2).getHealth()<=0) {
								((Player) e2).die();
								diePlayer.add(((Player) e2));
								Entities.remove(e2);
							}
						}
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
			try {
				String cmd = (String) it1.next();
				//System.out.println(cmd);
				if(cmd==null)
					continue;
				arrs = cmd.split(",");
				if ((Integer.parseInt(arrs[0])) == this.ServerId) {
					e1 = (Player) SearchEntityById(Integer.parseInt(arrs[1]));
					if (e1 != null) {
						switch (arrs[2]) {
							case "Forward": {
								e1.forward();
								if (checkColision(e1) != 0)
									e1.backwards();
								break;
							}
							case "Backward": {
								e1.backwards();
								if (checkColision(e1) != 0)
									e1.forward();
								break;
							}
							case "RotateRight": {
								e1.rotateRight();
								if (checkColision(e1) != 0)
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
								Bullet b = new Bullet(5, 5, new Point(e1.getPosition().getX(), e1.getPosition().getY()));
								b.setAngle(e1.getAngle());
								//b.id = getSpareId();
								b.owner = e1.id;
								this.Entities.add(b);
								break;
							}
						}
						//listPlayers();
					}

				} else {
					System.out.println("invalid command");
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		this.Commands = null;
		moveBullets();
	}
	public Entity getEntityByID(int id) {
		for(int i=0;i<Entities.size();i++) {
			if(Entities.get(i).id==id)
				return Entities.get(i);
		}
		return null;
	}

	public void broadcastEntities() {
		server.send(Port.mulitcastAddress,Entities);
		//List<PlayerScore> newRank = RankService.getInstance().rankList();
		//System.out.println("发送："+System.currentTimeMillis());
	}

}
