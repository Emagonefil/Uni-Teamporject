package game.network.client;

import game.network.Port;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


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
			fromServer.setSoTimeout(100);
			byte[] buf = new byte[4096];
			DatagramPacket packet = new DatagramPacket(buf,buf.length);
			do {
				try{
					fromServer.receive(packet);
					//System.out.println(packet.getLength());
					input = new ObjectInputStream(new ByteArrayInputStream(buf));
					Object obj = input.readObject();
					renderer.receive(obj);
				}
				catch (Exception e){
				}
			}while(flag);
		}catch (Exception e) {
		}
	}

	public void stop(){
	    try {
            this.flag = false;
            input.close();
            System.out.println("Receiver closed");
        }catch (Exception e){}

	}
	
	
}
