package game.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class RoomServer {
    Random ra=new Random();
    private ServerSocket server;
    private List<Room> rooms;
    private BlockingQueue<Socket> waiting;

    private static final int listenPort = 9999;
    private static final int sendingPort = 9999;


    public RoomServer(){
        try{
            server = new ServerSocket(listenPort);
            rooms = new ArrayList<>();
            waiting = new LinkedBlockingDeque<>();
            run();
            System.out.println("main server started");
        }catch (Exception e){}

    }

    public void run(){
        new Thread(new acceptThread()).start();
        new Thread(new serviceThread()).start();
    }

    private Object dealCommand(Socket s,String command){
        System.out.println(s.getInetAddress());
        if(command.equalsIgnoreCase("Room,create")){
            return createRoom(s.getInetAddress().toString().substring(1));
        }else if(command.startsWith("Room,join")) {
            return joinRoom(Integer.parseInt(command.substring(9)));
        }
        else if(command.startsWith("Room,leave")) {
            return leaveRoom(Integer.parseInt(command.substring(10)));
        }
        else if(command.startsWith("Room,list")) {
            return rooms;
        }
        return null;
    }

    private class acceptThread implements Runnable{

        @Override
        public void run(){
            try{
                while(true){
                    Socket client = server.accept();
                    waiting.add(client);
                }
            }catch (Exception ignored){}
        }
    }

    private class serviceThread implements Runnable{

        BufferedReader fromClient;
        ObjectOutputStream toClient;

        @Override
        public void run(){
            try{
                while (true){
                    Socket s = waiting.take();
                    toClient = new ObjectOutputStream(s.getOutputStream());
                    fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    String command = fromClient.readLine();
                    Object obj = dealCommand(s,command);
                    toClient.writeObject(obj);
                }

            }catch (Exception e){}


        }
    }
    private Room  findRoom(int id){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).roomId==id)
                return rooms.get(i);
        }
        return null;
    }
    public int getSpareId(){
        for(int i=1;i<255;i++){
            for(int u=0;u<rooms.size();u++)
                if((rooms.get(u).roomId==i)){
                    break;
                }
            return i;
        }
        return 0;
    }
    public int getSpareClientId() {
        while (true) {
            int id = ra.nextInt(9999);
            boolean t=true;
            for (int u = 0; u < rooms.size(); u++) {
                Room r1= rooms.get(u);
                for (int i = 0; i<r1.ClientId.size();i++)
                    if(r1.ClientId.get(i)==id) {
                        t = false;
                        break;
                    }
                if(!t)
                    break;
            }
            if(t)
                return id;
        }
    }
    private int createRoom(String ip){
        Room r=new Room();
        r.roomId=getSpareId();
        r.ServerIp=ip;
        r.ClientId.add(getSpareClientId());
        rooms.add(r);
        return r.roomId;
    }
    private String joinRoom(Integer roomNum){
        Room r1=findRoom(roomNum);
        if(r1==null){

            return "0";
        }
        else {
            int id=getSpareClientId();
            r1.ClientId.add(id);
            return ""+id;

        }
    }
    private String leaveRoom(Integer cid){
        for(int u=0;u<rooms.size();u++){
                Room r=rooms.get(u);
                for(int i=0;i<r.ClientId.size();i++){
                    if(r.ClientId.get(i)==cid)
                        r.ClientId.remove(i);
                }
        }
        return "0";
    }

}
