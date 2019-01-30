package game.entity;

import org.junit.Before;
import org.junit.Test;

public class MovableRectangularEntityTest {

	private MovableRectangularEntity testRectangle;
	private final float HEIGHT = 10.0f;
	private final float WIDTH = 5.0f;
	private final float ANGLE = 30.0f;
	
	private boolean floatEqual(float f1, float f2) {
		//If diff is less than 0.001 consider equal
		if (Math.abs(f1 - f2)<0.001f) {
			return true;
		}
		return false;
	}
	
	@Before
	public void setUp() {
		this.testRectangle = new MovableRectangularEntity(WIDTH, HEIGHT, new Point(15, 2), ANGLE);
	}
	
	@Test
	public void testForward() {
		testRectangle.forward();
		testRectangle.forward();
		testRectangle.forward();
		testRectangle.forward();
		
		
		//Check its moved in the right direction (roughly)
		assert(testRectangle.getPosition().getX()>15);
		assert(testRectangle.getPosition().getY()>2);
		
		//Check its moved by the right amount
		assert(floatEqual(2.0f, Point.distance(new Point(15, 2), testRectangle.getPosition())));
	}

	@Test
	public void testBackwards() {
		testRectangle.backwards();
		testRectangle.backwards();
		testRectangle.backwards();
		testRectangle.backwards();
		
		//Check its moved in the right direction (roughly)
		assert(testRectangle.getPosition().getX()<15);
		assert(testRectangle.getPosition().getY()<2);
				
		//Check its moved by the right amount
		assert(floatEqual(2.0f, Point.distance(new Point(15, 2), testRectangle.getPosition())));
	}

}
