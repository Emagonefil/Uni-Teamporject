package game.network.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import goldenaxe.network.tcp.thread.ThreadPool;


public class Server {

	private ServerSocket serverSocket;
	private ThreadPool threadPool;
	
	
	public Server(Integer port) {
		try {
			System.out.println("Server Starting....");
			serverSocket = new ServerSocket(port);
			System.out.println("Server Started !");
			acceptClients();
			System.out.println("Clients are availabe to connect now");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void acceptClients() throws IOException {
		threadPool = new ThreadPool();
		while(true) {
			Socket client = serverSocket.accept();
			threadPool.newClient(client);
		}
		
	}
}
