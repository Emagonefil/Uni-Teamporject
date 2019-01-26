package goldenaxe.network.client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

import goldenaxe.network.Port;

public class Client{
	
	private final Integer sendingPort = Port.serverPort;
	private final Integer listenPort = Port.boradcastPort;
	private final String address = Port.serverAddress;
	
	private ClientSender sender;
	private ClientReceiver receiver;
	
	private boolean receOn = false;
	

	public Client() {
		sender = new ClientSender(address,sendingPort);
		System.out.println("Client ready");
	}
	

	
	public ClientSender getSender() {
		return sender;
	}
	
	public void startReceiver(Receivable r) {
		if(receOn) {
			System.out.println("Receiver has started already");
			return;
		}else {
			receiver = new ClientReceiver(listenPort,r);
			new Thread(receiver).start();
			receOn = true;
			System.out.println("Receiver starts");
		}
		
	}
	
	
	
}
