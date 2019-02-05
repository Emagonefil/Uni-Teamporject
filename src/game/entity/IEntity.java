package game.entity;

public interface IEntity {
	public float getAngle();
	public void setAngle(float angle);
	public Point getPosition();
	public void setPosition(Point position);
	public int getId();
	public void draw();
}
