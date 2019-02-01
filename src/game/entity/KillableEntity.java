package game.entity;

public interface KillableEntity {
	public abstract void die();

	public void reduceHealth(int amount);

	public int getHealth();
}
