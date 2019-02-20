package game.network.client;

import game.network.Port;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;


public class ClientReceiver implements Runnable{
	
	private static Integer listenPort=Port.boradcastPort;
	private Receivable renderer;
	private ObjectInputStream input;
	private static boolean flag = true;
	private static MulticastSocket fromServer;
	private static String fromRoom = Port.mulitcastAddress;
	
	public ClientReceiver(Receivable r) {
		try{
			fromServer = new MulticastSocket(listenPort);
		}catch (IOException e){
			e.printStackTrace();
		}
//		this.listenPort = listenPort;
		this.renderer = r;
	}



	@Override
	public void run() {
		try {
			//DatagramSocket fromServer = new DatagramSocket(listenPort);
			fromServer.setInterface(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
			fromServer.joinGroup(InetAddress.getByName(fromRoom));
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

	public void join(String groupAddress){
		try{
			leave();
			fromRoom = groupAddress;
			fromServer.joinGroup(InetAddress.getByName(fromRoom));
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public void leave(){
		try{
			fromServer.leaveGroup(InetAddress.getByName(fromRoom));
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	
	
}
