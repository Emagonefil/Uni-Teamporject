package game.entity;

import game.Renderer;
import game.gui.Animation;
import game.gui.Sprite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Player extends MovableRectangularEntity implements KillableEntity {

	public static Player fromFile(String path) throws IOException {
		Properties configFile = new Properties();
		configFile.load(new FileInputStream(path));

		float width = Float.parseFloat(configFile.getProperty("width"));
		float height = Float.parseFloat(configFile.getProperty("height"));
		float x = Float.parseFloat(configFile.getProperty("xPos"));
		float y = Float.parseFloat(configFile.getProperty("yPos"));
		float angle = Float.parseFloat(configFile.getProperty("angle"));
		float speed = Float.parseFloat(configFile.getProperty("speed"));
		float rotationSpeed = Float.parseFloat(configFile.getProperty("rotationSpeed"));
		int health = Integer.parseInt(configFile.getProperty("health"));
		int ammo = Integer.parseInt(configFile.getProperty("ammo"));

		return new Player(width, height, new Point(x, y), angle, speed, rotationSpeed, health, ammo);
	}

	public Player(float width, float height, Point position) {
		super(width, height, position, 0.0f, 0.5f, 1.0f);
		this.health = 100;
		this.ammo = 20;
		this.type="Player";
	}

	public Player(float width, float height, Point position, float angle, float speed, float rotationSpeed) {
		super(width, height, position, angle, speed, rotationSpeed);
		this.health = 100;
		this.ammo = 20;
	}

	public Player(float width, float height, Point position, float angle, float speed, float rotationSpeed, int health,
			int ammo) {
		super(width, height, position, angle, speed, rotationSpeed);
		this.health = health;
		this.ammo = ammo;
	}

	private int health;
	private int ammo;

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reduceHealth(int amount) {
		this.health -= amount;
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	public int getAmmo() {
		return this.ammo;
	}

	public void setAmmo(int amount) {
		this.ammo = amount;
	}

	// This is simply to easily increment ammo
	// there is nothing more in this part.
	public void shoot() {
		this.ammo -= 1;
	}

}
