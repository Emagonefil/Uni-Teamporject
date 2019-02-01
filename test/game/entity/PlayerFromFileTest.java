package game.entity;

public class PlayerFromFileTest {

	public static void main(String[] args) {
		try {
			Player p = Player.fromFile("/home/callum/Documents/TeamProject/goldenaxe/Resources/playerConfigs/example.player");
			System.out.println(p.getPosition());
			System.out.println(p.getCorners());
			System.out.println(p.getAngle());
			System.out.println(p.getHealth());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
