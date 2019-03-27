package game.network.client;

import game.network.Port;

public class Client{

	/** used for client to send data to server*/
	private ClientSender sender;
	/** used for client to receive data from server*/
	private ClientReceiver receiver;
	/** if receiver is on */
	private boolean receOn = false;

	/**
	 * default constructor
	 */
	public Client() {
		sender = new ClientSender();
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
		}
	}



	public boolean receiverExist(){
		return receOn;
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
	 * let the client leave the current room
	 */
	public void leaveRoom(){
		this.receiver.leave();
	}

	/**
	 * let the client set which interface is using
	 * @param IP the string of interface
	 */
    public void setInterface(String IP){
		this.receiver.setInterface(IP);
	}

}
