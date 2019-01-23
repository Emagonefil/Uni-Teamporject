import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Client extends JFrame implements KeyListener{

	static Client client;
	static HashMap<Integer,Plane> planes = new HashMap<Integer,Plane>();
	
	static Socket server = null;
	static PrintStream toServer = null;
	static ObjectInputStream fromServer = null;
	
	
	BufferedImage bg;
	BufferedImage plane;
	
	public void paint(Graphics g) {
		g.drawImage(bg,0,0,null);
		if(!planes.isEmpty()) {
			for(Map.Entry<Integer, Plane> m : planes.entrySet()) {
				Plane p = m.getValue();
				g.drawString(String.valueOf(m.getKey()), p.getX()+15, p.getY()-20);
				g.drawImage(plane, p.getX(),p.getY(),null);
			}
		}
	}
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		client = new Client();
		server = new Socket("192.168.191.1", 6140);
		
		toServer = new PrintStream(server.getOutputStream());
		fromServer = new ObjectInputStream(server.getInputStream());
		new Thread(new Receiver()).start();
	}
	
	public Client() {
		try {
			bg = ImageIO.read(new File("src/img/bg.jpg"));
			plane = ImageIO.read(new File("src/img/plane.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(this);
		this.setTitle("plane.io");
		
		this.setSize(bg.getWidth(),bg.getHeight());
		this.setVisible(true);
	}
	
	private static class Receiver implements Runnable{

		
		@Override
		public void run() {
			try {
				while(true) {
					planes = (HashMap<Integer, Plane>) fromServer.readObject();
					
					client.repaint();
					
				}
			}catch(Exception e) {
				
			}
			
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		
		if(i == KeyEvent.VK_UP) {
			toServer.println("forward");
		}
		if(i == KeyEvent.VK_DOWN){
			toServer.println("backward");
		}
		if(i == KeyEvent.VK_LEFT) {
			toServer.println("left");
		}
		if(i == KeyEvent.VK_RIGHT){
			toServer.println("right");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	


	
	
	
}
