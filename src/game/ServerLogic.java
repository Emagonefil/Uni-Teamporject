package game;
import game.entity.*;
import goldenaxe.network.server.*;
import java.math.*;
import java.util.regex.*;
import game.entity.*;
import java.util.*;
public class ServerLogic {
	List<Entity> Entities=new ArrayList<Entity>();
	List<String> Commands=new ArrayList<String>();
	Server server= new Server();
	Random ra = new Random();
	private int status=0;
	public int ServerId= ra.nextInt()%99999+1;
	public void init() {
		this.server = new Server();
		initMap();
		Gap g1= new Gap(1);
		g1.run(this);
		this.status=1;
	}
	public void initMap() {
		for(int i=0;i<ra.nextInt()%20+10;i++) {
			Wall w;
			while(true){
				w=new Wall(10,10,new Point((float)Math.random()%635+5,(float)Math.random()%635+5));
				w.id=getSpareId();
				if(checkColision(w)==0)
					this.Entities.add(w);
			}
		}
	}
	public int getSpareId(){
		int id=ra.nextInt()%9999+1;
		while(true){
			int t=0;
			id=ra.nextInt()%9999+1;
			for(int i=0;i<Entities.size();i++) {
				System.out.println(i+" "+Entities.size());
				if(Entities.get(i).getId()==id){
					t=1;
					break;
				}
			}
			if(t==0)
				break;
		}
		return id;
	}
	public int checkColision(Entity e){
		Point p1= e.getPosition();
		Point p2;
		for(int i=0;i<Entities.size();i++) {
			Entity e2 = Entities.get(i);
			p2=e2.getPosition();
			if((p1.getX()-5)>=(p2.getX()-5)&&(p1.getX()-5)<=(p2.getX()+5)&&(p1.getY()-5)>=(p2.getY()-5)&&(p1.getY()-5)>=(p2.getY()+5))
				return e2.getId();
			if((p1.getX()+5)>=(p2.getX()-5)&&(p1.getX()+5)<=(p2.getX()+5)&&(p1.getY()-5)>=(p2.getY()-5)&&(p1.getY()-5)>=(p2.getY()+5))
				return e2.getId();
			if((p1.getX()-5)>=(p2.getX()-5)&&(p1.getX()-5)<=(p2.getX()+5)&&(p1.getY()+5)>=(p2.getY()-5)&&(p1.getY()+5)>=(p2.getY()+5))
				return e2.getId();
			if((p1.getX()+5)>=(p2.getX()-5)&&(p1.getX()+5)<=(p2.getX()+5)&&(p1.getY()+5)>=(p2.getY()-5)&&(p1.getY()+5)>=(p2.getY()+5))
				return e2.getId();
		}
		return 0;
	}
	public void addPlayer(int id){
		this.Entities.add(new Player(10,10,new Point((float)Math.random()%635+5,(float)Math.random()%635+5)));
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
			if(arrs[0].equals(this.ServerId))
			if((e1=(Player)SearchEntityById(Integer.parseInt(arrs[1])))!=null) {
				switch(arrs[2]) {
				case "Forward":e1.forward();
				case "Fackward":e1.backwards();
				case "RotateRight":e1.rotateRight();
				case "RotateLeft":e1.rotateLeft();
				case "Shoot": this.Entities.add(new Bullet(1,1,e1.getPosition()));
				}
			}
			else {
				if(arrs[1].equals("JoinServer")) {
					Player p=new Player(10,10,new Point());
					p.id=Integer.parseInt(arrs[0]);
					Entities.add(p);
				}
			}
		}
		this.Commands = null;
	}

	public void broadcastEntities() {
		server.sendBroadcast(this.Entities);
	}

	public static class Gap extends Thread {
		private Thread t;
		private int id;

		Gap(int tid) {
			this.id=tid;
		}

		public void run(ServerLogic s) {
			System.out.println("Server thread " + id + " is running");
			while (true) {
				try {
					if (s.status ==1){

					}else if(s.status == 2){
						s.dealCommmands();
					}
					Thread.sleep(10);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
