package goldenaxe.network.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;


public class ClientReceiver implements Runnable{
	
	private static Integer listenPort;
	private Receivable renderer;
	private ObjectInputStream input;

	private static boolean flag = true;
	
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
			do {
				fromServer.receive(packet);
		        input = new ObjectInputStream(new ByteArrayInputStream(buf));
		        List list = (List) input.readObject();
				renderer.receive(list);
			}while(flag);
		}catch (Exception e) {}
	}

	public void stop(){
	    try {
            this.flag = false;
            input.close();
            System.out.println("Receiver closed");
        }catch (Exception e){}

	}
	
	
}
