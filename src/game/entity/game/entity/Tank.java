package game.entity;

public class Tank extends RectangularEntity implements KillableEntity {

	private int health;
	
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceHealth(int amount) {
		this.health -= amount;
	}

	@Override
	public int getHealth(int amount) {
		return this.health;
	}
}
