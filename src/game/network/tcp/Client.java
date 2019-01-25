package game.network.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

public class Client {
	
	private Socket server;
	private PrintStream toServer = null;
	private ObjectInputStream fromServer = null;
	
	
	
	public Client(String ip, Integer port) {
		try {
			System.out.println("Connecting to Server....");
			server = new Socket(ip, port);
			System.out.println("Connected");
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new ObjectInputStream(server.getInputStream());
			System.out.println("Client is ready");
		} catch (UnknownHostException e) {
			System.out.println("Cannot connect to server");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//send String str to server
	public void send(String str) {
		toServer.println(str);
	}
	
	//return the object when receive something
	public Object receive() throws ClassNotFoundException, IOException{
		return fromServer.readObject();
	}
	
	
		
	
	
}
