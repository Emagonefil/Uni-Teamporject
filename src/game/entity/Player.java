package game.entity;

import game.graphics.Renderer;
import game.graphics.Sprite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * This class is the entity that is controlled by the player/AI
 * it extends MovableEntity to add relevant features such as health
 * and ammunition
 * 
 * @author callum
 *
 */
public class Player extends MovableEntity implements KillableEntity{

	/**
	 * There are multiple possible sprites for tanks, chosen randomly when
	 * the tank is created. This field specifies which tank sprite this 
	 * entity has.
	 */
	private int tankModel = 1;
	
	/**
	 * This takes a path to a config file and generates a new Player objects with the
	 * properties specified within the file. If any properties are missing then their
	 * default values are used instead.
	 * 
	 * @param path The path to the desired player config file.
	 * @return The Player that has been created from the file.
	 * @throws IOException
	 */
	public static Player fromFile(String path) throws IOException {
		Properties configFile = new Properties();
		configFile.load(new FileInputStream(path));
		float width = Float.parseFloat(configFile.getProperty("width", "60"));
		float height = Float.parseFloat(configFile.getProperty("height", "36"));
		float x = Float.parseFloat(configFile.getProperty("xPos", "0"));
		float y = Float.parseFloat(configFile.getProperty("yPos", "0"));
		float angle = Float.parseFloat(configFile.getProperty("angle", "0"));
		float speed = Float.parseFloat(configFile.getProperty("speed", "5.0"));
		float rotationSpeed = Float.parseFloat(configFile.getProperty("rotationSpeed", "2.5"));
		int health = Integer.parseInt(configFile.getProperty("health", "100"));
		int ammo = Integer.parseInt(configFile.getProperty("ammo", "30"));

		return new Player(width, height, new Point(x, y), angle, speed, rotationSpeed, health, ammo);
	}
	
	/**
	 * Create a Player with the default properties
	 * This shouldn't be used in practice.
	 */
	public Player() {
		super(60.0f, 36.0f, new Point(0.0f, 0.0f), 0.0f, 1f, 1.0f);
		this.health = 100;
		this.ammo = 30;
		this.type="Player";
		Random rand = new Random();
		this.tankModel = rand.nextInt(7);
	}

	/**
	 * Create a Player with the given property values
	 * 
	 * @param width The width of the Player
	 * @param height The height of the Player
	 * @param position The position of the centre of the Player
	 * @param angle The angle in which the Player should start facing
	 * @param speed The amount by which the player moves in a single move command
	 * @param rotationSpeed The amount (in degrees) by which the Player rotates in a sing rotate command
	 * @param health This Player's starting health
	 * @param ammo This Player's starting ammunition
	 */
	public Player(float width, float height, Point position, float angle, float speed, float rotationSpeed, int health,
			int ammo) {
		super(width, height, position, angle, speed, rotationSpeed);
		this.health = health;
		this.ammo = ammo;
		this.type="Player";
		Random rand = new Random();
		this.tankModel = rand.nextInt(7);
	}

	/**
	 * This players health. This is checked when they get hit
	 * by a bullet and if it reaches 0 then the Player is removed
	 */
	private int health;
	
	/**
	 * This Player's ammunition, should be checked before the Player is allowed to shoot
	 */
	private int ammo;
	
	/**
	 * This Player's username
	 */
	public String name;

	@Override
	public void draw() {
		draw(this.tankModel);
	}

	public void draw(int tankModel) {
		Sprite s = new Sprite(this,Renderer.getTank(tankModel),this.getWidth(),this.getHeight(),1);
		Renderer.playAnimation(s,this);
	}

	/**
	 * Reduces this Player's health by the given amount. Used by collision handling
	 * when a bullet hits a Player.
	 * 
	 * @param amount The amount by which to reduce this Player's health
	 */
	@Override
	public void reduceHealth(int amount) {
		this.health -= amount;
	}

	/**
	 * Returns this Player's health
	 * @return this Player's health
	 */
	@Override
	public int getHealth() {
		return this.health;
	}

	/**
	 * Returns this Player's ammo
	 * @return This Player's ammo
	 */
	public int getAmmo() {
		return this.ammo;
	}

	/**
	 * This sets the ammo field to the given value
	 * 
	 * @param amount The new value for ammo
	 */
	public void setAmmo(int amount) {
		this.ammo = amount;
	}
	
	/**
	 * This returns the tank sprite number for this tank. This is used by the Renderer
	 * 
	 * @return The tank sprite number for this Player
	 */
	public int getTankModel() {
		return this.tankModel;
	}

	/**
	 * Changes the tank sprite for this Player to the given one
	 * 
	 * @param tankModel The new tank sprite number for this Player
	 */
	public void setTankModel(int tankModel) {
		this.tankModel = tankModel;
	}

	/**
	 * This gives an easy way to decrement a Player's ammo when they shoot
	 */
	public void shoot() {
		this.ammo -= 1;
	}



}
