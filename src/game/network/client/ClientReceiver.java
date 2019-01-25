package game.network.client;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import game.entity.Entity;
import game.network.Port;

public class ClientReceiver implements Runnable{
	
	private static Integer listenPort;
	private Receivable renderer;
	
	public ClientReceiver(Integer listenPort,Receivable r) {
		this.listenPort = listenPort;
		this.renderer = r;
	}

	@Override
	public void run() {
		try {
			DatagramSocket fromServer = new DatagramSocket(listenPort);
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf,buf.length);
			while(true) {
				fromServer.receive(packet);
		        
		        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(buf));
		        List<Entity> list = (List) input.readObject();
				renderer.receive(list);
			}
		}catch (Exception e) {}
	}
	
	
}
