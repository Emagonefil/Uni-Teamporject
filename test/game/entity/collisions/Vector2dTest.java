package game.entity.collisions;

import org.junit.Before;
import org.junit.Test;

public class Vector2dTest {

	private boolean floatEqual(float f1, float f2) {
		// If diff is less than 0.001 consider equal
		if (Math.abs(f1 - f2) < 0.001f) {
			return true;
		}
		return false;
	}

	Vector2d v0;
	Vector2d v1;
	Vector2d v2;
	Vector2d v3;
	Vector2d v4;

	@Before
	public void setUp() {
		v0 = new Vector2d();
		v1 = new Vector2d(4.0f, 5.2f);
		v2 = new Vector2d(2.0f, 1.5f);
		v3 = new Vector2d(-1.5f, -5.0f);
		v4 = new Vector2d(0.0f, 2.0f);
	}

	@Test
	public void testDot() {
		assert (floatEqual(Vector2d.dot(v0, v1), 0.0f));
		assert (floatEqual(Vector2d.dot(v1, v2), 15.8f));
		assert (floatEqual(Vector2d.dot(v2, v3), -10.5f));
	}

	private boolean isOrthogonal(Vector2d v1, Vector2d v2) {
		return floatEqual(0.0f, Vector2d.dot(v1, v2));
	}

	@Test
	public void testGetNormal() {
		assert (isOrthogonal(v1, v1.getNormal()));
		assert (isOrthogonal(v2, v2.getNormal()));
		assert (isOrthogonal(v3, v3.getNormal()));
		assert (isOrthogonal(v4, v4.getNormal()));
	}

}
