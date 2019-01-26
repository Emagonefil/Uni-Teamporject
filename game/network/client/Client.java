package game.network.client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

import game.network.Port;
import game.entity.Entity;

public class Client {
	
	private final Integer sendingPort = Port.serverPort;
	private final Integer listenPort = Port.boradcastPort;
	private final String address = Port.serverAddress;
	
	private ClientSender sender;
	private ClientReceiver receiver;
	
	private List<Entity> players;

	public Client(Receivable r) {
		sender = new ClientSender(address,sendingPort);
		receiver = new ClientReceiver(listenPort,r);
		startReceiver();
		System.out.println("Client ready");
	}
	

	
	public ClientSender getSender() {
		return sender;
		
	}
	
	
	private void startReceiver() {
		new Thread(receiver).start();
	}
	
	public List<Entity> getPlayers() {
		return players;
	}
	
	
}
