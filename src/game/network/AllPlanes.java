import java.util.HashMap;

public class AllPlanes {

	private static HashMap<Integer,Plane> planes = new HashMap<Integer,Plane>();
	
	public void add(Integer port,Plane plane) {
		planes.put(port, plane);
	}
	
	public void die(Integer port) {
		planes.remove(port);
	}
	
	public Plane getPlane(Integer port) {
		return planes.get(port);
	}
	
	public HashMap getAll() {
		return planes;
	}
}
