package game.ai;
import java.util.*;
import game.*;
import game.entity.*;

public class AiController {

	private ClientLogic c1;
	private List<Entity>entities;
	private AiPlayer aiPlayer;
	
	public AiController(AiPlayer aiPlayer) {
		
		this.aiPlayer = aiPlayer;
		c1 = new ClientLogic();
		c1.init();
		entities = c1.getEntities();
		
	}
	
	public void controlPlayer() {
		
	
		
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
		
		List<Entity>players = getPlayers();
		Player nearestPlayer = null;
		float x = aiPlayer.getPosition().getX();
		float y = aiPlayer.getPosition().getY();
		
		while(true) {
			
			
			nearestPlayer = (Player)nearest(players);
			
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
					
					c1.sendCommands("RotateRight");
				}
				else if(aiPlayer.getAngle()> 180-computeAngle(nearestPlayer.getPosition())) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
			
			else if(x > nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() < computeAngle(nearestPlayer.getPosition())) {
					
					c1.sendCommands("RotateRight");
				}
				
				else if(aiPlayer.getAngle() > computeAngle(nearestPlayer.getPosition())) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}
			
			else if(x < nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() < 180+computeAngle(nearestPlayer.getPosition())) {
					
					c1.sendCommands("RotateRight");
				}
				
				else if(aiPlayer.getAngle() > 180+computeAngle(nearestPlayer.getPosition())) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}
			
			else if(x < nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle()<180+(180-computeAngle(nearestPlayer.getPosition()))) {
					
					c1.sendCommands("RotateRight");
				}
				
				else if(aiPlayer.getAngle()>180+(180-computeAngle(nearestPlayer.getPosition()))) {
					
					c1.sendCommands("RotateLeft");
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
			
			if(e.type.equals("Player")) {
				
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
	
	
}
