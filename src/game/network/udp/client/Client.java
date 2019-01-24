package goldenaxe.network.udp.client;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Client {
	
	private Integer port;
	
	public Client(Integer port) {
		this.port = port;
	}
	
	
	public void run() {
		startSender();
		startReceiver();
	}
	
	
	private void startSender() {
		
	}
	private void startReceiver() {
		new Thread(new ClientReceiver()).start();
	}
}
