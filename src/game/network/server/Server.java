package game.network.server;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


import game.network.Port;

public class Server{

	private DatagramSocket fromClient;
	private MulticastSocket multicastSocket;
	private List<String> movements = new ArrayList<>();
	
	public Server() {
		try {
			fromClient = new DatagramSocket(Port.serverPort);
			multicastSocket = new MulticastSocket();
			multicastSocket.setInterface(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
			run();
			System.out.println("Server started");
		} catch (Exception e) {
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
	public void close(){
		fromClient.close();
		multicastSocket.close();
	}
	public List<String> getMoves(){
		List<String> list = new ArrayList<String>(movements);
		movements.clear();
		return list;
	}

	public void setBroadcastPort(Integer port){

	}
	//send broadcast with list

	public void send(String addr,Object obj){
		try {
//			DatagramSocket broadSocket = new DatagramSocket();
//			MulticastSocket multicastSocket = new MulticastSocket();
//			multicastSocket.setInterface(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
			DatagramPacket packet;


			String address = addr;
			int port = Port.boradcastPort;

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(byteStream);
			oo.writeObject(obj);
			oo.close();
			byte[] buf = byteStream.toByteArray();


			packet = new DatagramPacket(buf,buf.length);
			packet.setAddress(InetAddress.getByName(address));
			packet.setPort(port);
			multicastSocket.send(packet);
//			broadSocket.send(packet);

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
						String move = new String(rece.getData(),0,rece.getLength());
						movements.add(move);
						//aSystem.out.println("Server got from client: "+move);
					}
				}catch(Exception ignored) {}
			}
			
		}).start();
	}
	
	
}
