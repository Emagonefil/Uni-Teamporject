
public abstract class KillableEntity extends Entity {
	private int health;
	
	public abstract void die();
	
	public void reduceHealth(int amount) {
		health -= amount;
	}
	
	public void getHealth(int amount) {
		
	}
	
}
