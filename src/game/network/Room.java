package game.network;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Room implements Serializable {
    private int roomId;
//    public List<Integer> ClientId=new ArrayList<Integer>();
//    public List<String> ClientName=new ArrayList<>();
    public HashMap<Integer,String> Clients = new HashMap<>();
    public String ServerIp;
    public String RoomIP;
    public int status=0;
    public int mapID;

    private final String roomIpPre= "230.0.0.";

    public Room(){
        Random r = new Random();
        this.mapID=r.nextInt(2)+1;
//        this.mapID=2;
    }

    public void setRoomId(int roomNum){
        if(roomNum>=1&&roomNum<=255) {
            this.roomId = roomNum;
            this.RoomIP = roomIpPre + roomNum;
        }
    }

    public int getRoomId(){
        return roomId;
    }


}
