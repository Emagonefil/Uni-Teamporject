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
		Gap g1= new Gap(1);
		g1.start();
	}
	public static void MultiPlayer(boolean iflocal) {
		int id;
		if(iflocal) {
			ServerLogic s1=new ServerLogic();
			s1.init();
			Gap g1= new Gap(1);
			g1.start();
		}
		ClientLogic c1 = new ClientLogic();
		c1.init();
		id=(int)System.currentTimeMillis();
		c1.sendCommands(String.valueOf(id)+",JoinServer");
	}	
	public static class Gap extends Thread {
	   private Thread t;
	   private int id;
	   
	   Gap(int tid) {
	      this.id=tid;
	   }
	   
	   public void run(ServerLogic s) {
		  System.out.println("Server thread " + String.valueOf(id)+" is running" );
         while(true) {
	        	 try {
		            s.dealCommmands();
		            Thread.sleep(50);
	        	 }
	        	 catch(Exception e){
	        		 e.printStackTrace();
	        	 }
	         }
	   }
	   
	   public void start () {
	      if (t == null) {
	         t = new Thread (this,String.valueOf(id));
	         t.start ();
	      }
	   }
	}
}