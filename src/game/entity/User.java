package game.entity;

/**
 * player class include password and username
 */
public class User {
	/**
	 * Username
	 */
	private String username;
	/**
	 * Password
	 */
	private String password;

	public int tankModel;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	private Integer ID;

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	/**
	 * points
	 */
	private Integer point;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTankModel() {
		return tankModel;
	}

	public void setTankModel(int tankModel) {
		this.tankModel = tankModel;
	}

	@Override
	public String toString() {
		return "User{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
	}
}
