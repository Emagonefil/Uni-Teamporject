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
		c1.sendCommands("Forward");
		c1.sendCommands("Backward");
		c1.sendCommands("RotateRight");
		c1.sendCommands("RotateLeft");
		c1.sendCommands("Shoot");
	}

}