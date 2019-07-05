package it.polimi.ingsw.controller;


import java.util.ArrayList;
import java.util.List;

/**
 * This class handles multiple games.
 */
public class ServerControllerManager {
    /**
     * A list of the ServerControllers that handles each game.
     */
    public static List<ServerController> serverControllers = new ArrayList<>();
    /**
     * The current index of the game.
     */
    public static int index;

    /**
     * Returns true if the username passed as parameter is already used by another player in each game.
     * @param username the username.
     * @return a boolean value.
     */
    public static boolean containsUsername(String username){
        for(int i=0; i<serverControllers.size(); i++){
            for(int j=0; j<serverControllers.get(i).getPlayers().size(); j++){
                if(username.equals(serverControllers.get(i).getPlayers().get(j).getUsername())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the username passed as parameter is already used by a disconnected player.
     * @param username the username.
     * @return a boolean value.
     */
    public static boolean containsDisconnectedUsername(String username){
        boolean result = false;
        for(int i=0; i<serverControllers.size(); i++){
            if(serverControllers.get(i).getDisconnectedUsers().containsKey(username)){
                index = i;
                result = true;
            }
        }
        return result;
    }

    /**
     * Getter for the correct ServerController.
     * @param username the username of the player who requested the ServerController.
     * @return a ServerController.
     */
    public static ServerController getServerController(String username){
        return serverControllers.get(index);
    }

    /**
     * Getter for the correct ServerController. If is the first game, it creates a new ServerController, else return the correct one.
     * @return the current ServerController.
     */
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
