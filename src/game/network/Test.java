package game.network;
import goldenaxe.network.server.Server;

import java.util.ArrayList;
import java.util.List;

import goldenaxe.network.client.Client;
import goldenaxe.network.client.ClientSender;
import goldenaxe.network.client.Receivable;

public class Test{
	static Server server1;
	static Server server2;
	static Client client1;
	static Client client2;
	
	public static void main(String[] args) throws Exception{
		
		/*
		 * only run one server or there would be a exception
		 * server1 only works on my laptop with the default ip of mine
		 * server2 changes the server address to localhost which you can use
		 * but make sure server is generated before client if using localhost
		 */
//		server1 = new Server<String>();
		server2 = new Server<String>("localhost");
		
		//client do not need parameters now
		client1 = new Client();
		client2 = new Client();
		
		//ClientSender performs the same way
		ClientSender sender1 = client1.getSender();
		sender1.send("test1");
		ClientSender sender2 = client2.getSender();
		sender2.send("test2");
		
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		
		/*
		 * sendBroadcast method of server would send the list to all clients that connects
		 * however the clients won't get this broadcast
		 * cause client has not start the receiver
		 */
		server2.sendBroadcast(list);
		
		/*
		 * Client.startReceiver needs a class that implements Receivable
		 * the receive method would be automatic called every time get the list from server
		 */
		client1.startReceiver(new Receivable() {

			@Override
			public void receive(List list) {
				for(Object s:list) {
					System.out.println(s);
				}
				
			}
			
		});
		
		//the list would be printed after started receiver
		server2.sendBroadcast(list);
		
		
	}
}
