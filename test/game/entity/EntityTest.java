package game.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {

	Entity e1;
	
	private boolean floatEqual(float f1, float f2) {
		// If diff is less than 0.001 consider equal
		if (Math.abs(f1 - f2) < 0.001f) {
			return true;
		}
		return false;
	}
	
	private boolean cornersEqual(Point[] c1, Point[] c2) {
		for (int i=0; i<c1.length;i++) {
			if (!(floatEqual(c1[i].getX(), c2[i].getX()) && floatEqual(c1[i].getY(), c2[i].getY()))) {
				return false;
			}
		}
		return true;
	}
	
	@Before
	public void setup() {
		e1 = new Wall(10.0f, 10.0f, new Point(30.0f, 50.0f));
	}
	
	@Test
	public void getCornersTest() {
		Point[] expectedCorners = new Point[4];
		expectedCorners[0] = new Point(25.0f, 55.0f);
		expectedCorners[1] = new Point(35.0f, 55.0f);
		expectedCorners[2] = new Point(35.0f, 45.0f);
		expectedCorners[3] = new Point(25.0f, 45.0f);
		
		assert(cornersEqual(expectedCorners, e1.getCorners()));
		
		e1.setAngle(360);
		assert(cornersEqual(expectedCorners, e1.getCorners()));
		
		e1.setAngle(90);
		System.out.println(e1.getCorners()[0]);
		System.out.println(e1.getCorners()[1]);
		System.out.println(e1.getCorners()[2]);
		System.out.println(e1.getCorners()[3]);
	}

}
