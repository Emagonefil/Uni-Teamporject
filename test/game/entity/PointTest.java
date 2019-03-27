package game.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PointTest {

	private boolean floatEqual(float f1, float f2) {
		// If diff is less than 0.001 consider equal
		if (Math.abs(f1 - f2) < 0.001f) {
			return true;
		}
		return false;
	}
	
	Point p1;
	Point p2;
	Point p3;
	
	@Before
	public void setUp() {
		p1 = new Point();
		p2 = new Point(3.5f, 5.2f);
		p3 = new Point(-1.5f, -3.0f);
	}
	
	@Test
	public void testDistance() {
		//assert(floatEqual(6.2681f, ))
		assert(floatEqual(0.0f, Point.distance(p2, p2)));
	}

}
