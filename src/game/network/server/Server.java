package game.network.server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


import game.network.Port;

public class Server{

	/** socket for receiving from clients*/
	private DatagramSocket fromClient;
	/** socket for sending data to clients */
	private MulticastSocket multicastSocket;
	/** all actions were made by clients*/
	private List<String> movements = new ArrayList<>();

	/**
	 * default constructor
	 * open basic socket for future use
	 * @exception SocketException,UnknownHostException,java.io.IOException
	 */
	public Server() {
		try {
			fromClient = new DatagramSocket(Port.serverPort);
			multicastSocket = new MulticastSocket();
			multicastSocket.setInterface(InetAddress.getByName(Port.localIP));
			run();
			System.out.println("Server started");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get a list all the moves get from clients since last time call this method
	 * @return all the moves get from clients since last time call this method
	 */
	public List<String> getMoves(){
		List<String> list = new ArrayList<String>(movements);
		movements.clear();
		return list;
	}

	/**
	 * send data to a address using multicast
	 * @param addr the address of multicast
	 * @param obj the data for sending
	 * @exception IOException,UnknownHostException
	 */
	public void send(String addr,Object obj){
		try {
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

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * start receiving data from clients
	 * and add these moves of client into the list
	 * @exception IOException
	 */
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
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	/**
	 * close all the sockets and stream of this class
	 */
	public void close(){
		fromClient.close();
		multicastSocket.close();
		fromClient=null;
		multicastSocket=null;
		movements=null;
	}
	
}
