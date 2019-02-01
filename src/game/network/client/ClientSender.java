package game.network.client;

import game.network.Port;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientSender{
		
	private DatagramSocket socket;
	private DatagramPacket packet;
//	private String address;
	private Integer port = Port.serverPort;
	
	public ClientSender() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}



	
	public void send(String address, String str) {
		try {
			byte[] buf = str.getBytes();
			packet = new DatagramPacket(buf,buf.length);
			packet.setAddress(InetAddress.getByName(address));
			packet.setPort(port);
			socket.send(packet);
		}catch(Exception ignored) {}
		
	}
	
	
	

}
