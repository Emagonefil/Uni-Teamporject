package game.network.udp.server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import goldenaxe.network.udp.Port;

public class Server {

	private DatagramSocket fromClient;
	
	public Server() {
		try {
			fromClient = new DatagramSocket(Port.serverPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		sendBroadcast();
	}
	
	
	private void sendBroadcast(){
		try {
			System.out.println("Start to sending information to all clients connected！");
			DatagramSocket broadSocket = new DatagramSocket();
			DatagramPacket packet;
			
			String address = Port.boradAddress;
			int port = Port.boradcastPort;
			
			Random r = new Random();
			
			while(true) {
				/*
				 * broadcast all players position
				 * to let clients to update
				 * 
				 */
				
				String str = "";
				for(int i=0;i<r.nextInt(10)+2;i++) {
					str += "a";
				}
				byte[] dataBytes = str.getBytes();
				packet = new DatagramPacket(dataBytes,dataBytes.length);
				packet.setAddress(InetAddress.getByName(address));
				packet.setPort(port);
				broadSocket.send(packet);
				TimeUnit.MILLISECONDS.sleep(7);
			}
		}catch(Exception ignored){}
		
	}
	
	
	
	
}
