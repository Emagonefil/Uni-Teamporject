package game.ai;
import java.util.*;
import game.entity.*;

public class AiController {

	private Player player;
	private AiPlayer aiPlayer;
	
	public AiController(AiPlayer aiPlayer,Player player) {
		
		this.aiPlayer = aiPlayer;
		this.player = player;
	}
	
	public void controlPlayer() {
		
		State state = aiPlayer.getState();
		
		switch(state) {
		
		case ATTACK:
			attack(player);
			break;
		case FLEE:
			
			break;
		case WANDER:
			
			
		}
	}
	
	
	public void attack(Player player) {
		
		
	}
	
	public void moveTo(Point position) {
		
		float angle = aiPlayer.getAngle();
		
		if (aiPlayer.getPosition().getX() ==  position .getX() && aiPlayer.getPosition().getY() == position.getY() ) {
			
			return;
		}
		
		else if (aiPlayer.getPosition().getX() == position.getX()) {
			
			if (aiPlayer.getPosition().getY() > position .getY()) {
				
				if(angle < 180) {
					
					aiPlayer.rotateRight();
				}
				
				else if (angle > 180 ) {
					
					aiPlayer.rotateLeft();
				}
			}
			
			else {
				
				if(angle > 180 && angle < 360 ) {
					
					aiPlayer.rotateRight();
				}
				
				else if (angle < 180 && angle > 0) {
					
					aiPlayer.rotateLeft();
				}
			}
			
			
		}
		
		else if (aiPlayer.getPosition().getY() == position.getY()) {
			
			if(aiPlayer.getPosition().getX() > position.getX()) {
				
				if(angle > 90 && angle < 270) {
					
					aiPlayer.rotateLeft();
				}
				
				else if (angle < 90) {
					
					aiPlayer.rotateRight();
				}
			}
			
			else {
				
				if(angle > 270 && angle < 360) {
					
					aiPlayer.rotateLeft();
				}
				
				else if (angle < 270) {
					
					aiPlayer.rotateRight();
				}
			}
		}
		
		else {
			
		}
		
		aiPlayer.forward();
		
	}
}
