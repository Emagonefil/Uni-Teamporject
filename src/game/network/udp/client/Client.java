package goldenaxe.network.udp.client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import goldenaxe.network.udp.Port;

public class Client {
	
	private final Integer sendingPort = Port.serverPort;
	private final Integer listenPort = Port.boradcastPort;
	private final String address = Port.serverAddress;
	
	public Client() {
		System.out.println("Client started");
	}
	
	
	
	public ClientSender run() {
		startReceiver();
		return getSender();
	}
	
	
	private ClientSender getSender() {
		return new ClientSender(address,sendingPort);
	}
	private void startReceiver() {
		new Thread(new ClientReceiver(listenPort)).start();
	}
	
}
