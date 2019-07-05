package it.polimi.ingsw.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Class that generates the Server's ip adresses and give them to it.
 */
public class Connection {
    /**
     * Lan address.
     */
    private static final String ETHERNET = "169.";

    /**
     * Class constructor.
     */
    private Connection(){}

    /**
     * Finds the ip addresses and return the correct one.
     * @return the correct ip address.
     * @throws SocketException caused by socket.
     */
    public static InetAddress getAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        List<InetAddress> addressesList = new ArrayList<>();
        while(networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while(addresses.hasMoreElements()){
                InetAddress address = addresses.nextElement();
                if(address instanceof Inet4Address && !address.isLoopbackAddress()){
                    addressesList.add(address);
                }
            }
        }
        if(addressesList.size() > 1){
            if(addressesList.get(0).toString().contains(ETHERNET)){
                return addressesList.get(0);
            }else{
                return addressesList.get(1);
            }
        }else{
            return addressesList.get(0);
        }
    }
}
