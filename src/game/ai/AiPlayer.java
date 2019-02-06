package game.ai;
import game.entity.*;

public class AiPlayer extends Player  {
	
	private State state;
	private AiController controller;
	
	public AiPlayer(float width, float height, Point position) {
		
		super(width, height, position);
		this.state = State.ATTACK;
		this.controller = new AiController(this);
		
		controller.controlPlayer();
	}
	
	
	public State getState() {
		
		return state;
	}
	
	public void setState(State state) {
		
		this.state = state;
	}
	
	
	
	

}
