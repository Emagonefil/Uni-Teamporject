package game.network;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import game.entity.Entity;
import game.network.client.Client;
import game.network.client.ClientSender;
import game.network.client.Receivable;
import game.network.server.Server;

public class Test{

	public static void main(String[] args) throws InterruptedException {
		//new server
		Server server = new Server();
		//server starts to receive messages from clients as it is generated
		
		
		/*
		 * 1. first way to generate Client class
		 */
		
		//client that only prints out all entities every time when receives it from server
		//write what you want in receive to change the functionality
//		Client client1 = new Client(new Receivable() {
//
//			@Override
//			public void receive(List<Entity> list) {
//				for(Entity e: list) {
//					System.out.println(e);
//				}
//			}
//			
//		});
		
		
		/*
		 * 2. second way to generate Client class
		 */
		// receTest if under the main method
		// this client prints out the length of the list every time
		Client client2 = new Client(new receTest());
		
		
		//ClientSender can send info to server
		ClientSender sender = client2.getSender();
		//send(String) sends to server the string
		sender.send("hello");
		
		
		List<Entity> list = new ArrayList<>();
		
		//able to add entities into list when there are generators for them
		list.add(new Entity());
		
		//this makes server send to all clients the list
		server.sendBroadcast(list);
		
		
		
	}
	
	
	private static class receTest implements Receivable{

		@Override
		public void receive(List<Entity> list) {
			System.out.println("the length of the list received: "+list.size());
			
		}
		
	}

	

}
