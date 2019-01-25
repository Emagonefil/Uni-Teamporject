package game.entity;

public class Tank extends RectangularEntity implements KillableEntity {

	public Tank(float width, float height, Point position) {
		super(width, height, position);
		this.health = 100;
	}

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
