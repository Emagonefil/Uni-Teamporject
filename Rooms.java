import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rooms {

    private HashMap<Integer,InetAddress> rooms = new HashMap<>();

    public Integer createRoom(InetAddress roomIP){
        int roomNum = availableRoomNum();
        rooms.put(roomNum, roomIP);
        return roomNum;
    }

    public InetAddress getRoom(Integer roomNum){
        return rooms.get(roomNum);
    }

    public List<Integer> allRooms(){
        List<Integer> list = new ArrayList<>();
        for(Integer i : rooms.keySet()){
            list.add(i);
        }
        return list;
    }


    private Integer availableRoomNum(){
        int num = -1;
        for(int i=0;i<10000;i++){
            if(!rooms.containsKey(i))
                return i;
        }
        return num;
    }



}
