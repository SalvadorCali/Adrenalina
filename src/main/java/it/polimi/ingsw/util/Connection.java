package it.polimi.ingsw.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Connection {
    public static List<InetAddress> getAddresses() throws SocketException {
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

                /*
                if(address.isSiteLocalAddress()){
                    addressesList.add(address);
                }
                */
            }
        }
        return addressesList;
    }

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
        //&& addressesList.size() < 4
        if(addressesList.size() > 1){
            return addressesList.get(1);
        //}
        //else if(addressesList.size() > 3) {
            //return addressesList.get(3);
        }else{
            return addressesList.get(0);
        }
    }
}
