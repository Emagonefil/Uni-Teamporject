package game.network.client;

import game.network.Port;

public class Client{
	
	private Integer sendingPort = Port.serverPort;

	
	private ClientSender sender;
	private ClientReceiver receiver;
	
	private boolean receOn = false;
	

	public Client() {
		sender = new ClientSender();
		System.out.println("111");

	}


	/**
	 * get a ClientSender that is used to send data to server
	 * @return the ClientSender of this client used to send data to server
	 * @see ClientSender
	 */
	public ClientSender getSender() {
		return sender;
	}

	/**
	 * start a ClientReceiver that automatically runs the receive method
	 * of the receivable class that passed in when receives data from server
	 * @param receivable Receivable class used to be called by ClientReceiver
	 * @see ClientReceiver
	 */
	public void startReceiver(Receivable receivable) {
		if(receOn) {
			System.out.println("Receiver has started already");
			return;
		}else {
			receiver = new ClientReceiver(receivable);
			new Thread(receiver).start();
			receOn = true;
			System.out.println("Receiver starts");
		}
	}

	/**
	 * close the current ClientReceiver that is running
	 */
	public void closeReceiver(){
	    if(null!=this.receiver)
		this.receiver.stop();
	    this.receiver = null;
		receOn = false;
	}

	/**
	 * let this client to join a specific room
	 * @param address the address of the room that the client wants to join in
	 */
	public void joinRoom(String address){
		this.receiver.join(address);
	}

	/**
	 *
	 */
	public void leaveRoom(){
		this.receiver.leave();
	}


//	public String getAddress(){
//	    return address+"@"+sendingPort;
//    }


    public void setInterface(String IP){
		this.receiver.setInterface(IP);
	}

}
