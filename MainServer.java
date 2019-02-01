import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class MainServer {

    DatagramSocket mainServer;
    Rooms rooms;

    private static final int listenPort = 7777;
    private static final int sendingPort = 9999;


    public MainServer(){
        try{
            rooms = new Rooms();
            mainServer = new DatagramSocket(listenPort);
            run();
            System.out.println("main server started");
        }catch (Exception e){}

    }

    public void run(){
        new Thread(new MainThread()).start();
    }

    private void dealCommand(DatagramPacket rece){
        String command = new String(rece.getData(),0,rece.getLength());
        InetAddress address = rece.getAddress();

        if(command.equalsIgnoreCase("create")){
            createRoom(address);
        }else if(command.startsWith("join")){
            joinRoom(address,Integer.parseInt(command.substring(4)));
        }else if(command.equalsIgnoreCase("roomlist")){
            roomList(address);
        }
    }


    private class MainThread implements Runnable{

        @Override
        public void run(){
            try{
                byte[] buf = new byte[1024];
                DatagramPacket rece = new DatagramPacket(buf,buf.length);
                while(true) {
                    mainServer.receive(rece);
                    dealCommand(rece);
                }
            }catch (Exception ignored){}
        }
    }


    private void createRoom(InetAddress address){
        Integer roomNum = rooms.createRoom(address);
        send(address,roomNum);
    }
    private void joinRoom(InetAddress address, Integer roomNum){
        InetAddress roomIP = rooms.getRoom(roomNum);
        send(address,roomIP);
    }

    private void roomList(InetAddress address){
        List<Integer> ROOMS = rooms.allRooms();
        send(address,ROOMS);
    }

    private void send(InetAddress address,Object obj){
        try{
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(byteStream);
            oo.writeObject(obj);
            oo.close();

            byte[] buf = byteStream.toByteArray();

            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            packet.setAddress(address);
            packet.setPort(sendingPort);
            mainServer.send(packet);
        }catch (Exception e){}
    }
    public static void main(String[] args){
        MainServer s = new MainServer();
    }

}
