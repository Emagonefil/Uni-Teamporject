package game;
import game.entity.*;

import java.util.*;
import game.ServerLogic.*;
import game.ClientLogic.*;
// loop that runs continuously to update every component of the game
public class Main {
	public static void main(String args[]) {
		MultiPlayer(true);	
	}
	public static void SinglePlayer() {
		ServerLogic s1=new ServerLogic();
		s1.init();
	}
	public static void MultiPlayer(boolean iflocal) {
		int id;
		if(iflocal) {
			ServerLogic s1=new ServerLogic();
			s1.init();
		}
		ClientLogic c1 = new ClientLogic();
		c1.init();
		id=(int)System.currentTimeMillis();
		c1.sendCommands(String.valueOf(id)+",JoinServer");
	}

}