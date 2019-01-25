package game.entity;

public class BreakableWall extends Wall implements KillableEntity{

	public BreakableWall(float width, float height, Point position) {
		super(width, height, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reduceHealth(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth(int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

}
