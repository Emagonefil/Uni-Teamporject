package game.entity;

public class Bullet extends RectangularEntity implements KillableEntity{

	public Bullet(float width, float height, Point position) {
		super(width, height, position);
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
