package game;

import game.audio.AudioPlayer;
import game.entity.*;
import game.entity.items.*;
import game.entity.collisions.*;
import game.maps.map;
import game.network.Port;
import game.network.server.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
public class ServerLogic {
	List<Entity> Entities=new ArrayList<Entity>();
	public List<Player> diePlayer= new ArrayList<Player>();
	List<String> Commands=new ArrayList<String>();
	Server server= new Server();
	Random ra = new Random();
	public int status=0;
	public int ServerId= 99999;
	map m=new map();
	public int mapID;
	AudioPlayer a = new AudioPlayer();

	/**
	 * init the whole server and create the map
	 */
	public void init() {
		initMap();
		this.status=1;
		System.out.println("ServerId: "+this.ServerId);
		this.status=2;
	}

	/**
	 * create the map
	 */
	public void initMap() {
		m.initMap(mapID);
		Entities.addAll(m.getMap());
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

	/**
	 * show the players that have joined the server
	 */
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

	/**
	 * get spare id that have not been used in entity list
	 * @return a random id that have not been used
	 */
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

	/**
	 * get entity's corner points to calculate collision
	 * @param e the entity
	 * @return points
	 */
	public Point[] getCorner(Entity e){
		if(e.type.equals("Player"))
			return ((Player)e).getCorners();
		if(e.type.equals("Wall"))
			return ((Wall)e).getCorners();
		if (e.type.equals("Bullet"))
			return ((Bullet)e).getCorners();
		if (e.type.equals("Item"))
			return ((Item)e).getCorners();
		return null;
	}

	/**
	 * check whether entity touched any other entity
	 * @param e the entity going to be checked
	 * @return the touched entity's id or 0
	 */
	public int checkCollision(Entity e){
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
			if(CollisionDetection.isTouching(p1,p2)) {
				if (e2.type.equals("Item")) {
					if (e.type.equals("Player")) {
						((Item) e2).effect((Player) e);
						if(e.id == Main.c1.id) {
							Main.audioPlayer.playPickItemSound();
						}
					}
					Entities.remove(e2);
					return 1;
				}
				return e2.id;
			}
		}
		return 0;
	}

	/**
	 * this funciton is used to add a player to entity list
	 * @param id player's id
	 * @return player's id
	 */
	public int addPlayer(int id,String name){
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
			w.name=id+" "+name;
			w.setPosition(new Point((float)x,(float)y));
			int l=checkCollision(w);
			//System.out.println(l);
			if (l== 0) {
				w.id = id;
				w.name = id+" "+name;
				Entities.add(w);
				break;
			}

		}

		RankService.getInstance().initPlayScore(w.id);
		listPlayers();
		return w.id;
	}

	/**
	 * this function is used to add a player to entity list without a random id
	 * @return player's id created randomly
	 */
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
			int l=checkCollision(w);
			//System.out.println(l);
			if (l== 0) {
				w.id =getSpareId();
				w.name = String.valueOf(w.id);
				Entities.add(w);
				break;
			}
		}

		listPlayers();
		RankService.getInstance().initPlayScore(w.id);
		return w.id;
	}

	/**
	 * it's used to search a specific entity in the entity list by id
	 * @param id the entity's id
	 * @return the entity or null
	 */
	public Entity SearchEntityById(int id) {
		for(int i=0;i<Entities.size();i++) {
			if(Entities.get(i).id==id)
				return Entities.get(i);
		}
		return null;
	}

	/**
	 * let bullets move on the screen
	 */
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

	/**
	 * this function is used to deal with commands sent from clients
	 */
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
								if (checkCollision(e1) != 0)
									e1.backwards();
								break;
							}
							case "Backward": {
								e1.backwards();
								if (checkCollision(e1) != 0)
									e1.forward();
								break;
							}
							case "RotateRight": {
								e1.rotateRight();
								if (checkCollision(e1) != 0)
									e1.rotateLeft();
								break;
							}
							case "RotateLeft": {
								e1.rotateLeft();
								if (checkCollision(e1) != 0)
									e1.rotateRight();
								break;
							}
							case "Shoot": {
								Bullet b = new Bullet(8, 8, new Point(e1.getPosition().getX(), e1.getPosition().getY()), e1.getAngle());
								//b.id = getSpareId();
								if (e1.id == Main.c1.id) {
									Main.audioPlayer.playShootSound();
								}
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
		createItems();
	}

	/**
	 * this function would create items randomly
	 * more items on the map, less possibility new items would be created
	 */
	public void createItems(){
		if(ra.nextInt(Entities.size()*50)==1)
			for(int i=0;i<50;i++){
				//Spawn health pickups
				HealthPickup h;
				double x=ra.nextInt((int)Constants.CANVAS_WIDTH/50)*50 ;
				double y=ra.nextInt((int)Constants.CANVAS_HEIGHT/50)*50 ;
				h = new HealthPickup(new Point((float)x, (float)y));
				h.id = getSpareId();
				if(checkCollision(h)==0) {
					Entities.add(h);
					break;
			}
		}
		if(ra.nextInt(Entities.size()*50)==1)
			for(int i=0;i<50;i++){
				//Spawn speed pickups
				SpeedPickup s;
				double x=ra.nextInt((int)Constants.CANVAS_WIDTH/50)*50 ;
				double y=ra.nextInt((int)Constants.CANVAS_HEIGHT/50)*50 ;
				s = new SpeedPickup(new Point((float)x, (float)y));
				s.id = getSpareId();
				if(checkCollision(s)==0) {
					Entities.add(s);
					break;
			}
		}
	}

	/**
	 * this function is used to send the entity list to clients
	 */
	public void broadcastEntities() {
		List<Entity> items=new ArrayList<>();
		for(int i=0;i<Entities.size();i++){
			Entity e1;
			e1=Entities.get(i);
			if(!e1.type.equals("Wall"))
				items.add(e1);

		}
		server.send(Port.mulitcastAddress,items);
		//List<PlayerScore> newRank = RankService.getInstance().rankList();
		//System.out.println("send："+System.currentTimeMillis());
	}

	/**
	 * close the server
	 */
	public void close(){
		server.close();
	}



}
