package game.entity;

import game.Renderer;
import game.gui.Animation;
import game.gui.Sprite;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Player extends MovableRectangularEntity implements KillableEntity {

	private int tankType = 0;

	public static Player fromFile(String path) throws IOException {
		Properties configFile = new Properties();
		configFile.load(new FileInputStream(path));
		float width = Float.parseFloat(configFile.getProperty("width", "80"));
		float height = Float.parseFloat(configFile.getProperty("height", "40"));
		float x = Float.parseFloat(configFile.getProperty("xPos", "0"));
		float y = Float.parseFloat(configFile.getProperty("yPos", "0"));
		float angle = Float.parseFloat(configFile.getProperty("angle", "0"));
		float speed = Float.parseFloat(configFile.getProperty("speed", "5.0"));
		float rotationSpeed = Float.parseFloat(configFile.getProperty("rotationSpeed", "2.5"));
		int health = Integer.parseInt(configFile.getProperty("health", "100"));
		int ammo = Integer.parseInt(configFile.getProperty("ammo", "30"));

		return new Player(width, height, new Point(x, y), angle, speed, rotationSpeed, health, ammo);
	}
	
	public Player() {
		super(80.0f, 40.0f, new Point(0.0f, 0.0f), 0.0f, 1f, 1.0f);
		this.health = 100;
		this.ammo = 30;
		this.type="Player";
		Random r = new Random();
		this.tankType = r.nextInt(7);
	}

	public Player(float width, float height, Point position, float angle, float speed, float rotationSpeed, int health,
			int ammo) {
		super(width, height, position, angle, speed, rotationSpeed);
		this.health = health;
		this.ammo = ammo;
		this.type="Player";
		Random r = new Random();
		this.tankType = r.nextInt(7);
	}

	private int health;
	private int ammo;

	public void draw() {
		this.setImage(Renderer.getTank(tankType));
//		this.setImage(Renderer.tank7);
		Sprite s = new Sprite(this,this.getImage(),this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}

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
