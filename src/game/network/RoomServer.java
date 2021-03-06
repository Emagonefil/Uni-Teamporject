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
    /** random */
    Random ra=new Random();
    /** server socket for this server */
    private ServerSocket server;
    /** list of all rooms */
    private List<Room> rooms;
    /** a queue for all actions from clients */
    private BlockingQueue<Socket> waiting;
    /** the port this server is listening to */
    private static final int listenPort = 9999;

    /**
     * default constructor
     */
    public RoomServer(){
        try{
            server = new ServerSocket(listenPort);
            rooms = new ArrayList<>();
            waiting = new LinkedBlockingDeque<>();
            run();
            System.out.println("room server started");
        }catch (Exception e){}

    }

    /**
     * start the server
     * run all the threads needed
     */
    public void run(){
        new Thread(new acceptThread()).start();
        new Thread(new serviceThread()).start();
    }

    /**
     * deal the command sent from client
     * @param s the socket of client
     * @param command the command client sends
     * @return the object sends back to client
     */
    private Object dealCommand(Socket s,String command){
        if(command.startsWith(Command.roomCreate)){
            String name = command.substring(Command.roomCreate.length());
            return createRoom(name);
        }else if(command.startsWith(Command.roomJoin)) {
            String id_name = command.substring(Command.roomJoin.length());
            String[] array = id_name.split(",");
            return joinRoom(Integer.parseInt(array[0]),array[1]);
        }
        else if(command.startsWith(Command.roomLeave)) {
            return leaveRoom(Integer.parseInt(command.substring(Command.roomLeave.length())));
        }
        else if(command.startsWith(Command.roomList)) {
            return rooms;
        }
        else if(command.startsWith(Command.roomEnd)){
            int roomID = Integer.parseInt(command.substring(Command.roomEnd.length()));
            Room room = findRoom(roomID);
            if(null==room) return "???";
            rooms.remove(room);
            return "OK";
        }
        else if(command.startsWith(Command.roomStart)){
            String t = command.substring(Command.roomStart.length());
            String[] a=t.split(",");
            Room r = findRoom(Integer.parseInt(a[0]));
            if(r.status==1){
                r.status=2;
                r.ServerIp=a[1];
            }
            return "";
        }
        return null;

    }

    /**
     * the thread aceepting client connection
     */
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

    /**
     * the thread deals the commands
     */
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

    /**
     * find the room by room id
     * @param id the id of the room
     * @return the room
     */
    private Room  findRoom(int id){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getRoomId()==id)
                return rooms.get(i);
        }
        return null;
    }

    /**
     * get a spare id
     * @return spare id
     */
    public int getSpareId(){
        int id;
        boolean t=true;
        for(int i=1;i<255;i++){
            id=i;
            for(int u=0;u<rooms.size();u++)
                if((rooms.get(u).getRoomId()==id)){
                    t=false;
                    break;
                }
            if (t)
                return i;
            t=true;
        }
        return 0;
    }

    /**
     * generate a spare client id
     * @return spare client id
     */
    public int getSpareClientId() {
        while (true) {
            int id = ra.nextInt(9999);
            boolean t=true;
            for (int u = 0; u < rooms.size(); u++) {
                Room r1= rooms.get(u);
                String result=r1.Clients.get(id);
                if(result!=null){
                    t=false;
                    break;
                }
            }
            if(t)
                return id;
        }
    }

    /**
     * create a new room
     * @return the id of the new room
     */
    private String createRoom(String hostName){
        Room r=new Room();
        r.setRoomId(getSpareId());
        int id = getSpareClientId();
        r.Clients.put(id,hostName);
        r.status=1;
        rooms.add(r);
        return id+","+r.getRoomId();
    }

    /**
     * join room by room id
     * @param roomNum the id of room wants to join
     * @return the id of the room, 0 if room not found
     */
    private Integer joinRoom(Integer roomNum,String name){
        Room r1=findRoom(roomNum);
        if(r1==null){

            return 0;
        }
        else {
            int id=getSpareClientId();
            r1.Clients.put(id,name);
            return id;

        }
    }

    /**
     * leave the room by id
     * @param cid the id of the room
     * @return confirm message
     */
    private String leaveRoom(Integer cid){
        if(cid==0)
            return "0";
        for(int u=0;u<rooms.size();u++){
                Room r=rooms.get(u);
                System.out.println("r"+rooms.size());
            String sss = r.Clients.get(cid);
            if(sss!=null){
                r.Clients.remove(cid);
                if(r.Clients.size()==0) rooms.remove(r);
            }

        }
        return "0";
    }

}
