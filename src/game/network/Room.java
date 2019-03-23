package game.network;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private int roomId;
    public List<Integer> ClientId=new ArrayList<Integer>();
    public String ServerIp;
    public String RoomIP;
    public int status=0;
    public int mapID;

    private final String roomIpPre= "230.0.0.";

    public Room(){
        this.mapID=1;
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
