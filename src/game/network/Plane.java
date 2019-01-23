import java.io.Serializable;

public class Plane implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private int x;
	private int y;
	
	public Plane(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	public void forward() {
		int i = this.getY();
		this.setY(i-2);
	}
	public void backward() {
		int i = this.getY();
		this.setY(i+2);
	}
	public void left() {
		int i = this.getX();
		this.setX(i-2);
	}
	public void right() {
		int i = this.getX();
		this.setX(i+2);
	}
}
