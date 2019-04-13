package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;

public class SocketClient implements ClientInterface, Runnable {
    private Thread thisThread;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private static final int SOCKET_PORT = 4321;
    private ViewInterface view;
    private String username;

    public SocketClient() throws IOException {
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("[CLIENT]Please, set an ip address:");
        String host = userInputStream.readLine();
        Socket clientSocket = new Socket(host, SOCKET_PORT);

        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        view = new CommandLine(this);
        view.start();
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (this.thisThread == currentThread){
            try {
                int val = objectInputStream.readInt();
                //handle the message
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void start() {
        thisThread = new Thread(this);
        thisThread.start();
    }
}
