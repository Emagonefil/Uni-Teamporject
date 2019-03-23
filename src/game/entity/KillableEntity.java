package game.entity;

public interface KillableEntity {

	public void reduceHealth(int amount);

	public int getHealth();
}
