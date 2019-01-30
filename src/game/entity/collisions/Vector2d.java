package game.entity.collisions;

public class Vector2d {
	private float x;
	private float y;

	public static float dot(Vector2d v1, Vector2d v2) {
		return (v1.getX() * v2.getX() + v1.getY() * v2.getY());
	}

	public Vector2d() {
		x = 0;
		y = 0;
	}

	public Vector2d(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d getNormal() {
		float magnitude = (float) Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
		float newY = this.y / magnitude;
		float newX = this.x / magnitude;
		return (new Vector2d(newY, -newX));
	}

	public boolean equals(Vector2d vec) {
		return (Math.abs(vec.getX() - this.getX()) < 0.001) && (Math.abs(vec.getY() - this.getY()) < 0.001);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
