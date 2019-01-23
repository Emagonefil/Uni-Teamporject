package goldenaxe.network.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import goldenaxe.network.tcp.Movement;

public class ServerReceiver implements Runnable{
	Socket client;
	BufferedReader fromClient;
	BlockingQueue<Movement> waiting;
	
	public ServerReceiver(Socket client,BlockingQueue<Movement> waiting) throws IOException {
		this.client = client;
		this.waiting = waiting;
		this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	
	@Override
	public void run() {
		String client_command;
		
			try {
				
				while(true) {
					client_command = fromClient.readLine();
				}
				
			} catch (IOException e) {
				System.out.println();
			}
		
		
	}

}
