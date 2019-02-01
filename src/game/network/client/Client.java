package game.network.client;

import game.network.Port;

public class Client{
	
	private Integer sendingPort = Port.serverPort;
	private Integer listenPort = Port.boradcastPort;
	private String address = Port.serverAddress;
	
	private ClientSender sender;
	private ClientReceiver receiver;
	
	private boolean receOn = false;
	

	public Client() {
		sender = new ClientSender();
		System.out.println("Client ready");
	}
	

	
	public ClientSender getSender() {
		return sender;
	}
	
	public void startReceiver(Receivable r) {
		if(receOn) {
			System.out.println("Receiver has started already");
			return;
		}else {
			receiver = new ClientReceiver(listenPort,r);
			new Thread(receiver).start();
			receOn = true;
			System.out.println("Receiver starts");
		}
		
	}

	public void changeServerIP(String address){
		Port.serverAddress = address;
		this.address = address;
	}
	public void closeReceiver(){
	    if(!(null==this.receiver))
		this.receiver.stop();
	    this.receiver = null;
		receOn = false;
	}
	

	public String getAddress(){
	    return address+"@"+sendingPort;
    }



}
