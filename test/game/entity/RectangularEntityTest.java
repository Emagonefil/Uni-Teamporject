package game.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RectangularEntityTest {

	private RectangularEntity testRectangle;
	private final float HEIGHT = 10.0f;
	private final float WIDTH = 5.0f;
	private final float ANGLE = 30.0f;
	private final Point POSITION = new Point(15, 2);
	
	private boolean floatEqual(float f1, float f2) {
		//If diff is less than 0.001 consider equal
		if (Math.abs(f1 - f2)<0.001f) {
			return true;
		}
		return false;
	}
	
	@Before
	public void setUp() {
		this.testRectangle = new RectangularEntity(WIDTH, HEIGHT, POSITION, ANGLE);
	}
	
	@Test
	public void testGetCorners() {
		Point[] corners = testRectangle.getCorners();
		//Check dimensions
		assert(floatEqual(WIDTH, Point.distance(corners[0], corners[1])));
		assert(floatEqual(WIDTH, Point.distance(corners[2], corners[3])));
		assert(floatEqual(HEIGHT, Point.distance(corners[0], corners[2])));
		assert(floatEqual(HEIGHT, Point.distance(corners[1], corners[3])));
		
		//At the moment I have manually checked the corner positions.
		//I can't think of a way to test it automatically without re-writing
		//the function here.
		/*
		System.out.println(corners[0]);
		System.out.println(corners[1]);
		System.out.println(corners[2]);
		System.out.println(corners[3]);
		*/
	}

}
