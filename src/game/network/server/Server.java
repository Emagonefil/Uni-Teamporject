package game.network.server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import game.network.Port;

public class Server<T>{

	private DatagramSocket fromClient;
	private List<T> players;
	
	public Server() {
		try {
			fromClient = new DatagramSocket(Port.serverPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
	}
	
	
	public void sendBroadcast(String str){
		try {
			System.out.println("broadcast sent");
			DatagramSocket broadSocket = new DatagramSocket();
			DatagramPacket packet;
			
			String address = Port.boradAddress;
			int port = Port.boradcastPort;
			
			byte[] dataBytes = str.getBytes();
			packet = new DatagramPacket(dataBytes,dataBytes.length);
			packet.setAddress(InetAddress.getByName(address));
			packet.setPort(port);
			broadSocket.send(packet);
			
		}catch(Exception ignored){}
		
	}
	
	
	
	
}
