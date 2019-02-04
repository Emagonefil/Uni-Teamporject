package game.network;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    public int roomId;
    public List<Integer> ClientId=new ArrayList<Integer>();
    public String ServerIp;
}
