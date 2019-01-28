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
		if(iflocal) {
			ServerLogic s1=new ServerLogic();
			s1.init();
			Gap g1= new Gap(1);
		}
		ClientLogic c1 = new ClientLogic();
		c1.init();
	}	
	class Gap extends Thread {
	   private Thread t;
	   private int id;
	   
	   Gap(int tid) {
	      this.id=tid;
	   }
	   
	   public void run(ServerLogic s) {
		  System.out.println("Server thread " + String.valueOf(id)+" is running" );
	      try {
	         while(true)
	            s.dealCommmands();
	            Thread.sleep(50);
	         }
	      catch (InterruptedException e) {
	      }
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	}
}