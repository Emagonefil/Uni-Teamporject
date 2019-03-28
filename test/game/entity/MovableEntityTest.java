package game.entity;

import org.junit.Before;
import org.junit.Test;

public class MovableEntityTest {

	private MovableEntity e1;
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
		this.e1 = new Bullet(WIDTH, HEIGHT, new Point(15, 2), ANGLE);
	}
	
	
	@Test
	public void testForward() {
		e1.forward();
		
		Point expected = new Point(19.3301f, 4.5f);

		assert (floatEqual(expected.getX(), e1.getPosition().getX()));
		assert (floatEqual(expected.getY(), e1.getPosition().getY()));
	}

	@Test
	public void testBackwards() {
		e1.backwards();
		Point expected = new Point(10.6698f, -0.5f);
		System.out.println(e1.getPosition());
		assert (floatEqual(expected.getX(), e1.getPosition().getX()));
		assert (floatEqual(expected.getY(), e1.getPosition().getY()));
	}

}
