package game.entity.collisions;

import java.util.ArrayList;
import java.util.Collections;

import game.entity.Point;


/**
 * An implementation of Seperating Axis Theorem (SAT)
 * 
 * This can detect whether any two convex polygons 
 * (more complex shapes must be made up of a few smaller convex polygons)
 * touch or not.
 * 
 * It also provides a minimum translation vector such that two colliding
 * objects are no longer touching (though this is not used within the project).
 * 
 * The basic steps for detecting if two shapes are touching are:
 * <ol>
 * <li> Find all unique normals to the faces of the two shapes given.
 * <li> These unique normals are then used as axis onto which we project the corners of the two shapes.
 * <li> We take the minimum and maximum corner projection for each shape. If these overlap then, in this axis, the shapes overlap.
 * <li> Repeat steps 2-3 for each axis (unique normal to the faces of the shapes) until we find a non-overlapping set of projections in which case they are not touching, or, until we run out of axis on which to test in which case they are touching
 * </ol>
 * @author callum
 *
 */
public class CollisionDetection {

	/**
	 * This takes two corners and gives a vector from corner1 to corner2
	 * 
	 * @param corner1 
	 * @param corner2 
	 * @return The vector connecting the two given points.
	 */
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

	/**
	 * This finds all unique normals to the given shapes edges.
	 * It is assumed that the corners are given in a 'circular' order
	 * 
	 * @param corners 
	 * @param axis
	 */
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
	
	/**
	 * This function will give the mtv i.e. the minimum amount & direction by which an overlapping
	 * object must be moved to no longer overlap.
	 * 
	 * Note: it takes corners such that any convex polygon can be checked for collision. Corners
	 * must be in a 'circular' order i.e. each corner has an edge connecting it to the corner before
	 * it in the array and an edge connecting it to the one after.
	 * 
	 * @param c1
	 * @param c2
	 * @return The minumum translation vector for the objects.
	 */

	public static Vector2d minimumTranslationVector(Point[] c1, Point[] c2) {
		ArrayList<Vector2d> axis = new ArrayList<Vector2d>();
		// Get the unique normals of each face to use as axis:
		getAxis(c1, axis);
		getAxis(c2, axis);
		
		Vector2d mtv = new Vector2d(Float.MAX_VALUE, Float.MAX_VALUE);

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
				//If they are not touching |mtv| = 0
				return new Vector2d();
			}
			
			float overlap = 1.0f; //Initial value 1 so error will still give collision
			float overlap1 = -(c2Max - c1Min);
			float overlap2 = (c1Max - c2Min);
			if (Math.abs(overlap1)<Math.abs(overlap2)) {
				overlap = overlap1;
			}else {
				overlap = overlap2;
			}
			
			if (overlap<mtv.getMagnitude()) {
				ax.scalarMult(overlap);
				mtv = ax;
			}
			
		}
		return mtv;
	}

	/**
	 * Given the corners of two convex polygons this will return true if the entities
	 * are touching and false otherwise
	 * 
	 * @param c1
	 * @param c2
	 * @return true if the entities are touching and false otherwise.
	 */
	public static boolean isTouching(Point[] c1, Point[] c2) {
		Vector2d result = minimumTranslationVector(c1, c2);
		//If overlap is > 0
		if (result.getMagnitude()>0) {
			return true;
		}else {
			return false;
		}
	}
}
