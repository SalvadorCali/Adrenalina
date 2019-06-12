package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ServerController;

import java.util.ArrayList;
import java.util.List;

public class ServerControllerManager {
    public static List<ServerController> serverControllers = new ArrayList<>();
    public static int index;

    public static boolean containsUsername(String username){
        boolean result = false;
        for(int i=0; i<serverControllers.size(); i++){
            if(serverControllers.get(i).getDisconnectedUsers().containsKey(username)){
                index = i;
                result = true;
            }
        }
        return result;
    }
    public static ServerController getServerController(String username){
        return serverControllers.get(index);
    }

    public static ServerController getServerController(){
        if(serverControllers.isEmpty()){
            ServerController serverController = new ServerController();
            serverControllers.add(serverController);
            return serverControllers.get(0);
        }else{
            if(serverControllers.get(serverControllers.size()-1).isGamePhase()){
                ServerController serverController = new ServerController();
                serverControllers.add(serverController);
                return serverControllers.get(serverControllers.size()-1);
            }else{
                return serverControllers.get(serverControllers.size()-1);
            }
        }

    }

}
