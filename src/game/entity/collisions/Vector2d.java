package game.entity.collisions;

/**
 * This is a class for 2d Vectors. It is a helper class for collision detection
 * 
 * @author callum
 *
 */
public class Vector2d {
	/**
	 * This vector's x component
	 */
	private float x;
	
	/**
	 * This vector's y component
	 */
	private float y;

	/**
	 * This returns the dot product of the two given vectors
	 * 
	 * @param v1 
	 * @param v2
	 * @return The dot-product of the two given vectors
	 */
	public static float dot(Vector2d v1, Vector2d v2) {
		return (v1.getX() * v2.getX() + v1.getY() * v2.getY());
	}

	/**
	 * Create a Vector2d with magnitude 0
	 */
	public Vector2d() {
		x = 0;
		y = 0;
	}

	/**
	 * Create a Vector2d with the given x and y components
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2d(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the magnitude of this vector
	 * 
	 * @return This vectors magnitude
	 */
	public float getMagnitude() {
		return (float) Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
	}
	
	/**
	 * This gives the unit length normal to this vector.
	 * 
	 * @return
	 */
	public Vector2d getNormal() {
		float magnitude = this.getMagnitude();
		float newY = this.y / magnitude;
		float newX = this.x / magnitude;
		return (new Vector2d(newY, -newX));
	}

	/**
	 * This checks if the given vector is equal to this one (accounting for float inaccuracy)
	 * Used to ensure that normals are unique in collision detection.
	 * 
	 * @param vec The vector to check for equality with this one
	 * @return true if the two vectors are equal false otherwise
	 */
	public boolean equals(Vector2d vec) {
		return (Math.abs(vec.getX() - this.getX()) < 0.001) && (Math.abs(vec.getY() - this.getY()) < 0.001);
	}
	
	/**
	 * Multiply this vector by the given scalar value.
	 * 
	 * @param scalar The scalar value to multiply this by.
	 */
	public void scalarMult(float scalar) {
		this.x = this.x * scalar;
		this.y = this.y * scalar;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
