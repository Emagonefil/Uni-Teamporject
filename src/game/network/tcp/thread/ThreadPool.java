package goldenaxe.network.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import goldenaxe.network.tcp.Movement;

public class ThreadPool {

	private final int timegap = 7;   // in miliseconds
	
	ExecutorService theThread;
	BlockingQueue<Movement> waiting;
	BlockingQueue<Movement> doing;
	
	
	public ThreadPool() {
		waiting = new LinkedBlockingQueue<>();
		doing = new LinkedBlockingQueue<>();
	}
	
	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool();
		pool.run();
	}
	
	//start a receiver thread for the new client
	public void newClient(Socket client) throws IOException {
		new Thread(new ServerReceiver(client,waiting)).start();
	}
	
	
	
	
	
	
	
	
	
	
	//Server starts sending
	public void run() {
		
//		scheduledThreadPool = Executors.newScheduledThreadPool(4);
//		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("k");
//			}
//		}, 0,timegap, TimeUnit.NANOSECONDS);
		
		theThread = Executors.newSingleThreadExecutor();
		theThread.execute(new Runnable() {
			
				@Override
				public void run() {
					Movement move;
					while(true) {
						try {
							if(!waiting.isEmpty()) {				//only send data to clients when there are things changed  
								doing = new LinkedBlockingQueue<>(waiting);
								waiting.clear();
								while(!doing.isEmpty()) {
									move = doing.take();
									System.out.println(move.getUser()+": "+move.getMove());
									
								}
								
							}
						
							Thread.sleep(timegap);;				//doing tasks with the time gap between
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
				
			});
		
	}
	
}
