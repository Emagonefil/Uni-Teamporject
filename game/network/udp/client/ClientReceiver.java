package game.network.udp.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import game.network.udp.Port;

public class ClientReceiver implements Runnable{
	
	private static Integer listenPort;
	
	public ClientReceiver(Integer listenPort) {
		this.listenPort = listenPort;
	}

	@Override
	public void run() {
		try {
			DatagramSocket fromServer = new DatagramSocket(listenPort);
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf,buf.length);
			while(true) {
				fromServer.receive(packet);
				String command = new String(packet.getData(),0,packet.getLength());
				System.out.println(command);
				
				//TODO   
				//Renderer after get infomation from server
			}
		}catch (Exception e) {}
	}
	
	
}
