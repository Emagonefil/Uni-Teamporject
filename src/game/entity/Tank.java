package game.entity;

public class Tank extends RectangularEntity implements KillableEntity {

	private int health;
	
	public Tank(Point[] corners, Point position) {
		super(corners, position);
		this.health = 100;
	}
	
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
