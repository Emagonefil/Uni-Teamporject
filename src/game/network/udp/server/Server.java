package goldenaxe.network.udp.server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Server {

	DatagramSocket fromClient;
	
	public Server(Integer port) {
		try {
			fromClient = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private static void sendBroadcast(){
		try {
			System.out.println("Start to sending information to all clients connected！");
			DatagramSocket broadSocket = new DatagramSocket();
			DatagramPacket packet;
			Random r = new Random();
			
			while(true) {
				String str = "";
				for(int i=0;i<r.nextInt(10)+2;i++) {
					str += "a";
				}
				byte[] dataBytes = str.getBytes();
				packet = new DatagramPacket(dataBytes,dataBytes.length,InetAddress.getByName("192.168.191.255"),19999);
				broadSocket.send(packet);
				TimeUnit.MILLISECONDS.sleep(7);
			}
		}catch(Exception ignored){}
		
	}
	
	
	
}
