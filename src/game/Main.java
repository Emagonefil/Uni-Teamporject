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
		Scanner scanner= new Scanner(System.in);
		if(iflocal) {
			serverGap s1=new serverGap(1);
			s1.start();
		}
		ClientLogic c1 = new ClientLogic();
		c1.init();
		System.out.println("Set serverid:");
		c1.ServerId=scanner.nextInt();
		System.out.println("Set clientid:");
		c1.id=scanner.nextInt();
		System.out.println("init succeed");

		while(true) {
			String s2=scanner.next();
			c1.sendCommands(s2);
		}
	}
	public static class serverGap extends Thread {
		private Thread t;
		private int id;

		serverGap(int tid) {
			this.id=tid;
		}
		@Override
		public void run() {
			System.out.println("Server thread " + id + " is running");
			ServerLogic s1=new ServerLogic();
			s1.init();
			while (true) {
				try {
					s1.dealCommmands();
					Thread.currentThread().sleep(10);
					if (s1.status == 2) {
						s1.broadcastEntities();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}