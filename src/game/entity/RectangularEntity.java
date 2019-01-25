package game.entity;

public class RectangularEntity extends Entity {

	private RectangularEntity() {}
	private float width;
	private float height;
	
	public RectangularEntity(float width, float height, Point position) {
		this.width = width;
		this.height = height;
		this.position = position;
		//Hardcoded at the moment can add more constructors later
		this.angle = 0.0f;
	}
	
	private Point rotateCorner(Point corner, float angle) {
		float radAngle = (float) Math.toRadians(angle);
		Point center = this.position;
		
		float tempX = corner.getX()-center.getX();
		float tempY = corner.getY()-center.getY();
			
		float rotX = (float) (tempX * Math.cos(radAngle) - tempY * Math.sin(radAngle));
		float rotY = (float) (tempX*Math.sin(radAngle) + tempY * Math.cos(radAngle));
		
		Point result = new Point(rotX + center.getX(), rotY + center.getY());
		return result;
	}
	
	public Point[] getCorners() {
		Point[] corners = new Point[4];
		float point1X = this.position.getX() - (this.width/2);
		float point1Y = this.position.getY() + (this.height/2);
		corners[0] = rotateCorner(new Point(point1X, point1Y), this.angle);
		
		float point2X = this.position.getX() + (this.width/2);
		float point2Y = this.position.getY() + (this.height/2);
		corners[1] = rotateCorner(new Point(point2X, point2Y), this.angle);
		
		float point3X = this.position.getX() - (this.width/2);
		float point3Y = this.position.getY() - (this.height/2);
		corners[2] = rotateCorner(new Point(point3X, point3Y), this.angle);
		
		float point4X = this.position.getX() + (this.width/2);
		float point4Y = this.position.getY() - (this.height/2);
		corners[3] = rotateCorner(new Point(point4X, point4Y), this.angle);
		
		return corners;
	}

}
