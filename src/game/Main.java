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
		//try{Thread.sleep(2000);}
//		catch (Exception e){
//			e.printStackTrace();
//		}

		while(true) {
			String s2=scanner.next();
			if(s2.equals("q")){
				c1.listBullets();
				c1.listPlayers();
				c1.listWalls();
				System.out.println(c1.Entities.size());
			}

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
					if (s1.status == 2) {
						s1.broadcastEntities();
					}
					Thread.currentThread().sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}