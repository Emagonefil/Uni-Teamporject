package game.network.client;

import game.network.Port;

import java.net.*;
import java.io.*;

public class ClientSender{

	/** socket for sending to server*/
	private DatagramSocket socket;
	/** packet is used to be sent to server*/
	private DatagramPacket packet;
	/** the port that is used to send to server*/
	private Integer port = Port.serverPort;

	/**
	 * default constructor
	 * @exception SocketException
	 */
	public ClientSender() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/**
	 * send a String to an address using udp
	 * @param address the address of receiver
	 * @param str the string needs to be sent
	 * @exception UnknownHostException,IOException
	 */
	public void send(String address, String str) {
		try {
			byte[] buf = str.getBytes();
			packet = new DatagramPacket(buf,buf.length);
			packet.setAddress(InetAddress.getByName(address));
			packet.setPort(port);
			socket.send(packet);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
