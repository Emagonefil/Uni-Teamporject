package game.network.server;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import game.entity.Entity;
import game.network.Port;

public class Server{

	private DatagramSocket fromClient;
	private List<String> movements = new ArrayList<String>();
	
	public Server() {
		try {
			fromClient = new DatagramSocket(Port.serverPort);
			run();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> getMoves(){
		List<String> list = new ArrayList<String>(movements);
		movements.clear();
		return list;
	}
	
	public void sendBroadcast(List<? extends Entity> list){
		try {
			System.out.println("broadcast sent");
			DatagramSocket broadSocket = new DatagramSocket();
			DatagramPacket packet;
			
			String address = Port.boradAddress;
			int port = Port.boradcastPort;
			
//			byte[] dataBytes = str.getBytes();
//			packet = new DatagramPacket(dataBytes,dataBytes.length);
//			packet.setAddress(InetAddress.getByName(address));
//			packet.setPort(port);
//			broadSocket.send(packet);
			
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(byteStream); 
			oo.writeObject(list);
			oo.close();
			byte[] buf = byteStream.toByteArray();
			
	        
	        packet = new DatagramPacket(buf,buf.length);
			packet.setAddress(InetAddress.getByName(address));
			packet.setPort(port);
			broadSocket.send(packet);
			System.out.println("broadcast finished");
			
		}catch(Exception ignored){}
		
	}
	
	 
	private void run() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					byte[] buf = new byte[1024];
					DatagramPacket rece = new DatagramPacket(buf,buf.length);
					while(true) {
						fromClient.receive(rece);
						String move = new String(buf,0,buf.length);
						movements.add(move);
						System.out.println("Server got from client: "+move);
					}
				}catch(Exception ignored) {}
			}
			
		}).start();
	}
	
	
}
