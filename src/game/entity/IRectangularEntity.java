package game.entity;

public interface IRectangularEntity extends IEntity {
	public float getWidth();
	public float getHeight();
	public Point[] getCorners();
}
