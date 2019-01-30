package game.ai;
import game.entity.*;

public class AiPlayer extends Player  {
	
	
	private Player player;
	private State state;
	private AiController controller;
	
	public AiPlayer(float width, float height, Point position,Player player) {
		
		super(width, height, position);
		this.state = State.ATTACK;
		this.controller = new AiController(this,player);
	}
	
	
	public State getState() {
		
		return state;
	}
	
	public void setState(State state) {
		
		this.state = state;
	}
	
	//this method should be called in the geme's main loop to update the position and the angle of the player
	public void update() {
		
		controller.controlPlayer();
	}
	
	
	

}
