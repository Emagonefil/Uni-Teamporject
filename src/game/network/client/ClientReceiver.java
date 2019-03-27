package game.network.client;

import game.Main;
import game.graphics.GameLoop;
import game.network.Port;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.io.*;


public class ClientReceiver implements Runnable{

	/** port this receiver should be listening */
	private static Integer listenPort=Port.boradcastPort;
	/** the render method would be called in future*/
	private Receivable renderer;
	/** stream for receiving */
	private ObjectInputStream input;
	/** flag for if this receiver should be running */
	private static boolean flag;
	/** socket for receiving packets from server*/
	private static MulticastSocket fromServer;
	/** the address string of the room*/
	private static String fromRoom;

	/**
	 * default constructor
	 * @param r Receivable class that would be called in future when receives data in future
	 * @exception IOException
	 */
	public ClientReceiver(Receivable r) {
		fromRoom = Port.mulitcastAddress;
		flag=true;
		try{
			fromServer = new MulticastSocket(listenPort);
		}catch (Exception e){
			e.printStackTrace();
		}
		this.renderer = r;
	}


	/**
	 * start to receiving data from server
	 * and call the render method of Receivable each time receives data
	 * @exception SocketException,UnknownHostException,ClassNotFoundException,IOException
	 */
	@Override
	public void run() {

		try {
			fromServer.setInterface(InetAddress.getByName(Port.localIP));
			fromServer.joinGroup(InetAddress.getByName(fromRoom));
			fromServer.setSoTimeout(3000);
			byte[] buf = new byte[4096];
			DatagramPacket packet = new DatagramPacket(buf,buf.length);
			System.out.println("Receiver starts");
			do {
				try{
					fromServer.receive(packet);
					GameLoop.connected();
					input = new ObjectInputStream(new ByteArrayInputStream(buf));
					Object obj = input.readObject();
					renderer.receive(obj);
				}
				catch (Exception e){
					if(Main.isRunning&&GameLoop.isRunning) GameLoop.disconnected();
				}

			}while(flag);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * stop receiving data
	 * @exception IOException
	 */
	public void stop(){
	    try {
            this.flag = false;
            fromServer.close();
            System.out.println("Receiver closed");
        }catch (Exception e){}

	}

	/**
	 * join a group of muticast
	 * @param groupAddress the address of the group
	 * @exception UnknownHostException
	 */
	public void join(String groupAddress){
		try{
			leave();
			fromRoom = groupAddress;
			fromServer.joinGroup(InetAddress.getByName(fromRoom));
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * leave the current multicast group
	 * @exception UnknownHostException
	 */
	public void leave(){
		try{
			fromServer.leaveGroup(InetAddress.getByName(fromRoom));
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * set the interface of this socket
	 * @param IP the string of the interface
	 * @exception UnknownHostException
	 */
	public void setInterface(String IP){
		try {
			stop();
			fromServer.setInterface(InetAddress.getByName(IP));

		}catch (Exception e){
			e.printStackTrace();
		}

	}
	
}
