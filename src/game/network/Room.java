package game.network;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Room implements Serializable {

    /** room id of this room*/
    private int roomId;
    /** hashmap for storing all clients' information in this room*/
    public HashMap<Integer,String> Clients = new HashMap<>();
    /** the if of server of this room*/
    public String ServerIp;
    /** the multicast address of this room */
    public String RoomIP;
    /** status of this room*/
    public int status=0;
    /** map id of the map is using for this room*/
    public int mapID;
    /** prefix ip address of the multicast address is using*/
    private final String roomIpPre= "230.0.0.";

    /**
     * default constructor
     */
    public Room(){
        Random r = new Random();
        this.mapID=r.nextInt(2)+1;
    }

    /**
     * set the room id of this room
     * multicast address would be set as well
     * @param roomNum room id
     */
    public void setRoomId(int roomNum){
        if(roomNum>=1&&roomNum<=255) {
            this.roomId = roomNum;
            this.RoomIP = roomIpPre + roomNum;
        }
    }

    /**
     * get the room id of this room
     * @return room id
     */
    public int getRoomId(){
        return roomId;
    }


}
