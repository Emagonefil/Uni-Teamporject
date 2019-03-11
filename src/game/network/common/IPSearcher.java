package game.network.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

import static java.net.NetworkInterface.getNetworkInterfaces;

public class IPSearcher {

    public static String goldenaxeAddress(){
        try{
            Enumeration<NetworkInterface> interfaceList = getNetworkInterfaces();
            if (interfaceList == null) {
                return null;
            } else {
                while(interfaceList.hasMoreElements()){
                    NetworkInterface iface = interfaceList.nextElement();
                    Enumeration<InetAddress> addrList = iface.getInetAddresses();
                    while(addrList.hasMoreElements()){
                        InetAddress inetAddress = addrList.nextElement();
                        if(inetAddress instanceof Inet4Address) {
                            String address = inetAddress.getHostAddress();
                            String pattern = "192.168.137.\\d+";
                            if(Pattern.matches(pattern, address))
                                return address;
                        }
                    }
                }
            }
            return null;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
