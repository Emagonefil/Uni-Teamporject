package game.entity.collisions;

import java.util.ArrayList;
import java.util.Collections;

import game.entity.Point;

//An implementation of Seperating Axis Theorem (SAT)
//Shapes must be convex polygons! (More complex shapes should be
//constructed of multiple convex polygons)

public class CollisionDetection {

	private static Vector2d getVector(Point corner1, Point corner2) {
		float xDiff = corner2.getX() - corner1.getX();
		float yDiff = corner2.getY() - corner1.getY();
		return (new Vector2d(xDiff, yDiff));
	}

	private static boolean contains(Vector2d vec, ArrayList<Vector2d> vecs) {
		for (Vector2d vector : vecs) {
			if (vector.equals(vec)) {
				return true;
			}
		}
		return false;
	}

	// Note: it is assumed that corners are connected to the
	// corner before and after them in a circular fashion
	private static void getAxis(Point[] corners, ArrayList<Vector2d> axis) {
		for (int i = 0; i < corners.length; i++) {
			Vector2d normal;
			if (i == corners.length - 1) {
				normal = getVector(corners[i], corners[0]).getNormal();
			} else {
				normal = getVector(corners[i], corners[i + 1]).getNormal();
			}

			if (!(contains(normal, axis))) {
				axis.add(normal);
			}
		}
	}

	public static boolean isTouching(Point[] c1, Point[] c2) {
		ArrayList<Vector2d> axis = new ArrayList<Vector2d>();
		// Get the unique normals of each face to use as axis:
		getAxis(c1, axis);
		getAxis(c2, axis);

		for (Vector2d ax : axis) {
			float c1Min;
			float c1Max;
			float c2Min;
			float c2Max;

			ArrayList<Float> projections = new ArrayList<Float>();
			// Using dot with axis to project point onto axis
			for (Point p : c1) {
				Vector2d pointVector = new Vector2d(p.getX(), p.getY());
				projections.add(Vector2d.dot(pointVector, ax));
			}

			c1Min = Collections.min(projections);
			c1Max = Collections.max(projections);

			projections.clear();
			for (Point p : c2) {
				Vector2d pointVector = new Vector2d(p.getX(), p.getY());
				projections.add(Vector2d.dot(pointVector, ax));
			}
			
			c2Min = Collections.min(projections);
			c2Max = Collections.max(projections);
			
			if (c1Min>c2Max || c2Min>c1Max) {
				return false;
			}
		}
		return true;
	}

}
