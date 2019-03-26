package game.ai;
import java.util.*;
import game.*;
import game.entity.*;
import game.entity.items.*;

/** The AiController class which responsible of controlling an AI player and extends built in java class Thread */

public class AiController extends Thread {

	private static int counter = 0;
	private int id;
	/** The clientLogic object c1 which is responsible of sending the commands of movements of the AI player */
	private ClientLogic c1;
	/** The clientLogic object c2 which is responsible of sending the commands of movements of the real player and it is used here just to get the entities */
	private ClientLogic c2;
	/** the list of the entities in the game */
	private List<Entity>entities;
	/** the aiPlayer object */
	private Player aiPlayer;
	/** the state of the AI player */
	private States state;
	/** the volatile boolean variable running which is true when the AI player is alive and false otherwise */ 
	private volatile boolean running = true;
	
	/** 
	 * Constructor
	 * @param c1 the object which is responsible of sending the commands for movements of the AI player to the server
	 * @param c2 the object which is responsible of sending the commands for movements of the real player to the server and it is used here just to get the entities
	 */
	public AiController(ClientLogic c1,ClientLogic c2) {
		counter++;
		this.id = counter;
		this.c1 = c1;
		this.c2 = c2;
		entities = c2.getEntities();
		state = States.ATTACK;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aiPlayer = myPlayer();

	}
	
	/**
	 * run method which is the override the run method in the Thread class and it runs until either of running or Main.running become false.
	 *  this method controls the behaviour of the AI as it has if statements to check in which state is the AI player currently and according to that it call attack or collectHP method
	 */
	public void run() {
		
		while(running&&Main.isRunning) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			if(state == States.ATTACK) {
				attack();
			}
			else if(state == States.LOOKFORHP) {
				
				collectHP();
			}
		}
		
		if(aiPlayer == null) {
			
			System.out.println("AI"+ id + " dead");
		}
		
		else {
			
			System.out.println("AI"+ id + " is the winner");
		}
	}
	
	/**
	 * attack method is the method responsible for moving the AI player to the nearest opponent and shoot him 
	 * so first it finds the nearest player using helper methods to find the distance between players and methods to return the nearest player
	 * Then after finding the nearest player this method use helper methods to compute the angle between the AI player and the nearest player 
	 * Then it send the appropriate commands using c1.sendCommands depending on what is the current angle of the AI player and what is the angle between AI player and the nearest player
	 * The while loop will not stop until the AI player die (aiPlayer = null) or the AI player wins (players.size() = 0) 
	 * or when the state of the AI player changes to LOOKFORHP (when the health of the player less or equal 20 i.e aiPlayer.getHealth <=20)
	 */
	public void attack() {
		
		
		List<Entity>players = null;
		Player nearestPlayer = null;
		
		float x = 0;
		float y = 0;
		
		while(true) {
				
			
			updateEntities();
			updatePlayer();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			players = getPlayers();
			
			
			
			if(aiPlayer == null || players.size() == 0) {
				
				running = false; 
				break;
			}
			
			
			
			nearestPlayer = (Player)nearest(players);
			x = aiPlayer.getPosition().getX();
			y = aiPlayer.getPosition().getY();
			
			
			
			
			if(aiPlayer.getHealth()<=20) {
				
				state = States.LOOKFORHP;
				break;
			}
			
			
			
			if (aiPlayer.getAngle() >= 360) {
				
				aiPlayer.setAngle(aiPlayer.getAngle()-360);
			}
			
			if(aiPlayer.getAngle() < 0) {
				
				aiPlayer.setAngle(aiPlayer.getAngle()+360);
			}
			
			
			if (x == nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()) {
				
			
				if(aiPlayer.getAngle() > 270 || aiPlayer.getAngle() < 90) {
					
					//while()
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 270 && aiPlayer.getAngle() >= 90 ) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}	
				
			
			else if (x == nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
					
				
				if(aiPlayer.getAngle() > 90 && aiPlayer.getAngle() < 270) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 90 || aiPlayer.getAngle() >= 270) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			
			}
				
			
			else if (y == nearestPlayer.getPosition().getY() && x > nearestPlayer.getPosition().getX()) {
				
				
				if(aiPlayer.getAngle() < 360 && aiPlayer.getAngle() > 180) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 180) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
				
			else if (y == nearestPlayer.getPosition().getY() && x < nearestPlayer.getPosition().getX()) {
					
				if(aiPlayer.getAngle() < 180 && aiPlayer.getAngle() > 0) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() >= 180 && aiPlayer.getAngle() != 0 ) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			
			}
			
			
			else if (x > nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()){
				
				if(aiPlayer.getAngle() > 180+computeAngle(nearestPlayer.getPosition())+2) {
					
						c1.sendCommands("RotateLeft");
				}
				
				else if(aiPlayer.getAngle()< 180+computeAngle(nearestPlayer.getPosition())-2) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
			
			
			else if(x > nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() > 180-computeAngle(nearestPlayer.getPosition())+2) {
						
					c1.sendCommands("RotateLeft");
				}
				
				else if(aiPlayer.getAngle() < 180-computeAngle(nearestPlayer.getPosition())-2) {
						
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
			
			
			else if(x < nearestPlayer.getPosition().getX() && y > nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() > 360-computeAngle(nearestPlayer.getPosition())+2) {
						
					c1.sendCommands("RotateLeft");
					
				}
				
				else if(aiPlayer.getAngle() < 360-computeAngle(nearestPlayer.getPosition())-2) {
					
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
				
			}
			
			
			else if(x < nearestPlayer.getPosition().getX() && y < nearestPlayer.getPosition().getY()) {
				
				if(aiPlayer.getAngle() > computeAngle(nearestPlayer.getPosition())+2) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if(aiPlayer.getAngle()< computeAngle(nearestPlayer.getPosition())-2) {
						
					c1.sendCommands("RotateRight");
				}
				
				else {
					
					c1.sendCommands("Shoot");
				}
			}
			
			
			
			c1.sendCommands("Forward");
				
			
		}
	}
	
	/**
	 * This method is called when the state of the AI player is LOOKFORHP and it is responsible for collecting hp to increase the AI player's health
	 * so first it finds the nearest item using helper methods to find the distances between AI player and items and methods to return the nearest item
	 * Then after finding the nearest item this method use helper methods to compute the angle between the AI player and the nearest item
	 * Then it send the appropriate commands using c1.sendCommands depending on what is the current angle of the AI player and what is the angle between AI player and the nearest item
	 * The while loop will not stop until the AI player die (aiPlayer = null) or there is no items  (hp.size() = 0) 
	 * or when the state of the AI player changes to ATTACK (when the health of the player bigger than 20 i.e aiPlayer.getHealth > 20)
	 */
	private void collectHP(){
		
		List<Entity>hp = null;
		List<Entity>players = getPlayers();
		float x = 0;
		float y = 0;
		
		while(true) {
				
			
			updateEntities();
			updatePlayer();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			players = getPlayers();
			hp = getHP();
			
			
			if(aiPlayer == null || players.size() == 0) {
				
				running = false; 
				break;
			}
			
			
			
			
			Item nearestHP = (Item)nearest(hp);
			
			x = aiPlayer.getPosition().getX();
			y = aiPlayer.getPosition().getY();
			
			
			
			
			if(aiPlayer.getHealth()>20||hp.size() == 0) {
				
				state = States.ATTACK;
				break;
			}
			
			
			
			if (aiPlayer.getAngle() >= 360) {
				
				aiPlayer.setAngle(aiPlayer.getAngle()-360);
			}
			
			if(aiPlayer.getAngle() < 0) {
				
				aiPlayer.setAngle(aiPlayer.getAngle()+360);
			}
			
			
			if (x == nearestHP.getPosition().getX() && y > nearestHP.getPosition().getY()) {
				
			
				if(aiPlayer.getAngle() > 270 || aiPlayer.getAngle() < 90) {
					
					//while()
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 270 && aiPlayer.getAngle() >= 90 ) {
					
					c1.sendCommands("RotateRight");
				}
				
				
			}	
				
			
			else if (x == nearestHP.getPosition().getX() && y < nearestHP.getPosition().getY()) {
					
				
				if(aiPlayer.getAngle() > 90 && aiPlayer.getAngle() < 270) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 90 || aiPlayer.getAngle() >= 270) {
					
					c1.sendCommands("RotateRight");
				}
				
			
			}
				
			
			else if (y == nearestHP.getPosition().getY() && x > nearestHP.getPosition().getX()) {
				
				
				if(aiPlayer.getAngle() < 360 && aiPlayer.getAngle() > 180) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() < 180) {
					
					c1.sendCommands("RotateRight");
				}
				
				
				
			}
				
			else if (y == nearestHP.getPosition().getY() && x < nearestHP.getPosition().getX()) {
					
				if(aiPlayer.getAngle() < 180 && aiPlayer.getAngle() > 0) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if (aiPlayer.getAngle() >= 180 && aiPlayer.getAngle() != 0 ) {
					
					c1.sendCommands("RotateRight");
				}
				
				
			
			}
			
			
			else if (x > nearestHP.getPosition().getX() && y > nearestHP.getPosition().getY()){
				
				if(aiPlayer.getAngle() > 180+computeAngle(nearestHP.getPosition())+2) {
					
						c1.sendCommands("RotateLeft");
				}
				
				else if(aiPlayer.getAngle()< 180+computeAngle(nearestHP.getPosition())-2) {
					
					c1.sendCommands("RotateRight");
				}
				
				
				
			}
			
			
			else if(x > nearestHP.getPosition().getX() && y < nearestHP.getPosition().getY()) {
				
				if(aiPlayer.getAngle() > 180-computeAngle(nearestHP.getPosition())+2) {
						
					c1.sendCommands("RotateLeft");
				}
				
				else if(aiPlayer.getAngle() < 180-computeAngle(nearestHP.getPosition())-2) {
						
					c1.sendCommands("RotateRight");
				}
				
				
				
			}
			
			
			else if(x < nearestHP.getPosition().getX() && y > nearestHP.getPosition().getY()) {
				
				if(aiPlayer.getAngle() > 360-computeAngle(nearestHP.getPosition())+2) {
						
					c1.sendCommands("RotateLeft");
					
				}
				
				else if(aiPlayer.getAngle() < 360-computeAngle(nearestHP.getPosition())-2) {
					
					c1.sendCommands("RotateRight");
				}
				
				
				
			}
			
			
			else if(x < nearestHP.getPosition().getX() && y < nearestHP.getPosition().getY()) {
				
				if(aiPlayer.getAngle() > computeAngle(nearestHP.getPosition())+2) {
					
					c1.sendCommands("RotateLeft");
				}
				
				else if(aiPlayer.getAngle()< computeAngle(nearestHP.getPosition())-2) {
						
					c1.sendCommands("RotateRight");
				}
				
				
			}
			
			
			
			c1.sendCommands("Forward");
				
			
		}
		
	}
	

	/**
	 * this method finds the angle between a the nearest player and the AI player by defining new Point p1 which has the same x coordinate 
	 * of the nearest player and the same y coordinate of the AI player to convert the problem to a right angle problem
	 * Then we set the lengths of the triangle sides which is the distance between points where the hypothesis is the distance between the AI player and the nearest player
	 * and the side opposite to the angle between the players is the distance between the nearest player and the point p1
	 * Therefore we compute the distance by computing the sin inverse of the result of the division distance between the nearest player and point p1 over the hypothesis 
	 * @param position is the position of the nearest player to the AI player
	 * @return the angle between the AI player and the nearest player
	 */
	
	private float computeAngle(Point position) {
		
		Point p1 = new Point(position.getX(),aiPlayer.getPosition().getY());
		float distToPosition = distance(p1,position);
		float distBetweenPlayers = distance(aiPlayer.getPosition(),position);
		float sin0 = distToPosition/distBetweenPlayers;
		
		
		return (float) Math.toDegrees(Math.asin(sin0));
		
		
	}

	
	
	
	/**
	 * 
	 * @return the list of players in the game excluding the player which is controlled by this controller
	 */
	private List<Entity> getPlayers(){
		
		ArrayList<Entity>players = new ArrayList<Entity>();
		for(Entity e: entities) {
			
			if(e.type.equals("Player") && e.id != c1.id) {
				
				players.add((Player)e);
			}
		}
		
		return players;
	}
	
	/**
	 * 
	 * @return the list of items in the game 
	 */
	private List<Entity>getHP(){
		
		ArrayList<Entity>items = new ArrayList<Entity>();
		for(Entity e: entities) {
			
			if(e.type.equals("Item") ) {
				
				items.add((Item)e);
			}
		}
		
		return items;
		
	}
	
	/**
	 * this method is responsible of finding the nearest player or nearest item to the AI player by computing the distances between the AI player 
	 * and the players or items in the passed list and returns the nearest one
	 * @param entities is a list of specific entities in the game (e.g players)
	 * @return the nearest entity in passed list 
	 */
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
	
	/**
	 * this method is responsible of computing the distance between two points and it is used to compute the distance between the AI player
	 * and other player or the distance between the AI player and an item
	 * @param p1 is point 1
	 * @param p2 is point 2
	 * @return the distance between these points
	 */
	private float distance(Point p1, Point p2) {
		
		float p1x = p1.getX();
		float p1y = p1.getY();
		float p2x = p2.getX();
		float p2y = p2.getY();
		
		return (float) Math.sqrt(Math.pow(p1x-p2x, 2) + Math.pow(p1y-p2y, 2));
	}
	
	/**
	 * 
	 * @return the player which is controlled by this AI controller or return null if that player is not exist anymore(i.e died)
	 */
	private Player myPlayer() {
		
		updateEntities();
		for(Entity e:entities) {
			
			if(e.id == c1.id) {
				
				return (Player)e;
			}
		}
		
		return null;
		
	}
	
	
	/**
	 * updates the list of entities in the game
	 */
	private void updateEntities() {
		
		entities = c2.getEntities();
	}
	
	/**
	 * updates the player which is controlled by this AI controller 
	 */
	private void updatePlayer() {
		
		aiPlayer = myPlayer();
		
	}
	
}