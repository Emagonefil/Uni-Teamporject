package game.ai;
import java.util.*;
import game.*;
import game.entity.*;

public class AiController extends Thread {

	private ClientLogic c1;
	private ClientLogic c2;
	private List<Entity>entities;
	private Player aiPlayer;
	
	public AiController(ClientLogic c1,ClientLogic c2) {
		
		this.c1 = c1;
		this.c2 = c2;
		c1.init();
		entities = c2.getEntities();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aiPlayer = myPlayer();
		
	}
	
	public void run() {
		
	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attack();
		/**State state = aiPlayer.getState();
		
		switch(state) {
		
		case ATTACK:
			
			attack();
			break;
		case FLEE:
			
			break;
		case WANDER:
			
			
		}**/
	}
	
	
	public void attack() {
		
		
		List<Entity>players = null;
		Player nearestPlayer = null;
		float x = 0;
		float y = 0;
		
		while(true) {
			
			updateEntities();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			players = getPlayers();
			nearestPlayer = (Player)nearest(players);
			
			 x = aiPlayer.getPosition().getX();
			 y = aiPlayer.getPosition().getY();
			
			if (aiPlayer.getAngle() >= 360) {
				
				aiPlayer.setAngle(aiPlayer.getAngle()-360);
			}
			
			if(aiPlayer.getAngle() < 0) {
				
				aiPlayer.setAngle(aiPlayer.getAngle()+360);
			}
			
			
			if (x == nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()) {
				
			
				if(aiPlayer.getAngle() < 180) {
					
					c1.sendCommands("RotateRight");
				}
				
				else if (aiPlayer.getAngle() > 180 ) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}	
				
			else if (x == nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
					
				
				if(aiPlayer.getAngle() > 180 && aiPlayer.getAngle() < 360 ) {
					
					c1.sendCommands("RotateRight");
				}
				
				else if (aiPlayer.getAngle() < 180 && aiPlayer.getAngle() > 0) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			
			}
				
			
			else if (y == nearestPlayer.getPosition().getY() && x > nearestPlayer.getPosition().getX()) {
				
				
				if(aiPlayer.getAngle() > 90 && aiPlayer.getAngle() < 270) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 90) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
				
			else if (y == nearestPlayer.getPosition().getY() && x < nearestPlayer.getPosition().getX()) {
					
				if(aiPlayer.getAngle() > 270 || aiPlayer.getAngle()<90) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 270) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			
			}
			
			else if (x > nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()){
				
				if(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						c1.sendCommands("RotateRight");
					}
				}
				else if(aiPlayer.getAngle()> 180-computeAngle(nearestPlayer.getPosition())) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						c1.sendCommands("RotateLeft");
					}
					
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
			
			else if(x > nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() < computeAngle(nearestPlayer.getPosition())) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						
						c1.sendCommands("RotateRight");
					}
					
				}
				
				else if(aiPlayer.getAngle() > computeAngle(nearestPlayer.getPosition())) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						
						c1.sendCommands("RotateLeft");
					}
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}
			
			else if(x < nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() < 180+computeAngle(nearestPlayer.getPosition())) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						
						c1.sendCommands("RotateRight");
					}
				}
				
				else if(aiPlayer.getAngle() > 180+computeAngle(nearestPlayer.getPosition())) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						
						c1.sendCommands("RotateLeft");
					}
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}
			
			else if(x < nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle()<180+(180-computeAngle(nearestPlayer.getPosition()))) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						
						c1.sendCommands("RotateRight");
					}
				}
				
				else if(aiPlayer.getAngle()>180+(180-computeAngle(nearestPlayer.getPosition()))) {
					
					while(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())) {
						
						c1.sendCommands("RotateLeft");
					}
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}
			
			else {
				
				c1.sendCommands("Shoot");
			}
			
			c1.sendCommands("Forward");
		}
	}
	
	private float computeAngle(Point position) {
		
		Point p1 = new Point(aiPlayer.getPosition().getX(),position.getY());
		float distToPosition = distance(p1,position);
		float distBetweenPlayers = distance(aiPlayer.getPosition(),position);
		float sin0 = distToPosition/distBetweenPlayers;
		
		
		return (float) Math.toDegrees(Math.asin(sin0));
		
		
	}
	
	
	
	
	private List<Entity> getPlayers(){
		
		ArrayList<Entity>players = new ArrayList<Entity>();
		for(Entity e: entities) {
			
			if(e.type.equals("Player") && e.id != c1.id) {
				
				players.add((Player)e);
			}
		}
		
		return players;
	}
	
	private Entity nearest(List <Entity> entities) {
		
		Entity nearest = entities.get(0);
		float distFromNearest = 0; 
		float dist = 0;
		for(Entity entity : entities) {
			
			distFromNearest =  distance(aiPlayer.getPosition(),nearest.getPosition());
			dist = distance(aiPlayer.getPosition(),entity.getPosition());
			
			if(dist< distFromNearest) {
				
				nearest = entity;
			}
			
		}
		
		return nearest;
	}
	
	private float distance(Point p1, Point p2) {
		
		float p1x = p1.getX();
		float p1y = p1.getX();
		float p2x = p2.getX();
		float p2y = p2.getY();
		
		return (float) Math.sqrt(Math.pow(p1x-p2x, 2) + Math.pow(p1y-p2y, 2));
	}
	
	private Player myPlayer() {
		
		updateEntities();
		for(Entity e:entities) {
			
			if(e.id == c1.id) {
				
				
				return (Player)e;
			}
			
		}
		
		return null;
		
	}
	
	private void updateEntities() {
		
		entities = c2.getEntities();
	}
	
	
}
