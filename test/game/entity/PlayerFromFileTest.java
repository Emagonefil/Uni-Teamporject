package game.entity;

public class PlayerFromFileTest {

	public static void main(String[] args) {
		Player p = Player.fromFile("/home/callum/Documents/TeamProject/goldenaxe/Resources/playerConfigs/example.player");
		System.out.println("Done");
		System.out.println(p.getPosition());
		System.out.println(p.getCorners());
		System.out.println(p.getAngle());
		System.out.println(p.getHealth());
	}
	
}
