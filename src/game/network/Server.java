import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

	
	public static AllPlanes allPlanes = new AllPlanes();
	
	public static void main(String[] args) {
		Random r = new Random();
		try {
			ServerSocket serverSocket = new ServerSocket(6140);
			while(true) {
				Socket socket = serverSocket.accept();
				new Thread(new ServerSender(socket)).start();
				new Thread(new ServerReceiver(socket)).start();
				allPlanes.add(socket.getPort(),new Plane(r.nextInt(700),r.nextInt(700)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class ServerReceiver implements Runnable{

		Socket socket = null;
		BufferedReader fromClient = null;
		
		public ServerReceiver(Socket socket) throws IOException {
			this.socket = socket;
			this.fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		
		@Override
		public void run() {
			String userInput;
			try {
				while(true) {
					userInput = fromClient.readLine();
					if(userInput.equals("forward")) {
						forward(socket);
					}else if(userInput.equals("backward")) {
						backward(socket);
					}else if(userInput.equals("left")) {
						left(socket);
					}else if(userInput.equals("right")) {
						right(socket);
					}
					
					
				}
			} catch (Exception e) {
				System.out.println(socket.getPort()+" logout");
				allPlanes.die(socket.getPort());
			}
			
		}
		
	}
	
	private static class ServerSender implements Runnable{
		
		Socket socket = null;
		ObjectOutputStream toClient = null;
		
		public ServerSender(Socket socket) throws IOException {
			this.socket = socket;
			this.toClient = new ObjectOutputStream(socket.getOutputStream());
		}
		
		public void run() {
			try {
				
				while(true) {
					toClient.writeObject(allPlanes.getAll());
					toClient.reset();
				}
			}catch(Exception e){
				
			}
		}
	}
	
	private static void forward(Socket socket) {
		Plane plane = allPlanes.getPlane(socket.getPort());
		plane.forward();
	}
	private static void backward(Socket socket) {
		Plane plane = allPlanes.getPlane(socket.getPort());
		plane.backward();
	}
	private static void left(Socket socket) {
		Plane plane = allPlanes.getPlane(socket.getPort());
		plane.left();
	}
	private static void right(Socket socket) {
		Plane plane = allPlanes.getPlane(socket.getPort());
		plane.right();
	}
	
}
