package it.polimi.ingsw.network;

public class NetworkString {
    /**
     * Class constructor.
     */
    private NetworkString(){}
    /**
     * Represents the choice between cli and gui.
     */
    public static final String UI_CHOOSE = "[CLIENT]Choose 'cli' or 'gui':";
    /**
     * Represents the choice between rmi and socket.
     */
    public static final String CONNECTION_CHOOSE = "[CLIENT]Choose 'rmi' or 'socket':";
    /**
     * Tells to the user to insert an ip address.
     */
    public static final String IP_MESSAGE = "[CLIENT]Please, set an ip address:";
    /**
     * Shows the current ip address of the server.
     */
    public static final String IP_SERVER = "[SERVER]Current ip address:";
    /**
     * Sets the relative property.
     */
    public static final String PROPERTY = "java.rmi.server.hostname";
    /**
     * Represents the registry name.
     */
    public static final String REGISTRY_NAME = "server";
    /**
     * Represents the relative powerup's name.
     */
    public static final String TAGBACK_GRENADE = "tagbackgrenade";
    /**
     * Represents the relative powerup's name.
     */
    public static final String TARGETING_SCOPE = "targetingscope";
}
