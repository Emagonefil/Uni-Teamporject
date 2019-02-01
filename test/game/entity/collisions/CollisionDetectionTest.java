package game.entity.collisions;

import static org.junit.Assert.*;

import org.junit.Test;

import game.entity.Point;
import game.entity.RectangularEntity;

public class CollisionDetectionTest {

	//TO-DO add more testing.
	@Test
	public void testIsTouching() {
		RectangularEntity r1 = new RectangularEntity(10, 10, new Point(5, 5));
		RectangularEntity r2 = new RectangularEntity(10, 10, new Point(3,3));
		RectangularEntity r3 = new RectangularEntity(2, 2, new Point(100, 100));
		assert(CollisionDetection.isTouching(r1.getCorners(), r2.getCorners()));
		assert(!(CollisionDetection.isTouching(r1.getCorners(), r3.getCorners())));
	}

}
