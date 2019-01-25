package game.network.tcp;

public class Movement {

	private Integer user;
	private String move;
	
	public Movement(Integer user,String move) {
		this.user = user;
		this.move = move;
	}
	
	
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	
	
}
