package goldenaxe.network.server;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


import goldenaxe.network.Port;

public class Server<T>{

	private DatagramSocket fromClient;
	private List<String> movements = new ArrayList<String>();
	
	public Server() {
		try {
			fromClient = new DatagramSocket(Port.serverPort);
			run();
			System.out.println("Server started");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public Server(String address) {
		try {
			Port.serverAddress = address;
			fromClient = new DatagramSocket(Port.serverPort);
			run();
			System.out.println("Server started");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getMoves(){
		List<String> list = new ArrayList<String>(movements);
		movements.clear();
		return list;
	}
	
	public void sendBroadcast(List<? extends T> list){
		try {
			DatagramSocket broadSocket = new DatagramSocket();
			DatagramPacket packet;
			
			String address = Port.boradAddress;
			int port = Port.boradcastPort;
			
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(byteStream); 
			oo.writeObject(list);
			oo.close();
			byte[] buf = byteStream.toByteArray();
			
	        
	        packet = new DatagramPacket(buf,buf.length);
			packet.setAddress(InetAddress.getByName(address));
			packet.setPort(port);
			broadSocket.send(packet);
			System.out.println("broadcast sent");
			
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
