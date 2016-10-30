package Chat.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final int PORT = 1234;
    private static ServerSocket serverSocket;
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private Socket client;

    public static void main(String[] args) {
        new Server().startServer(PORT);
    }

    private void startServer(int PORT) {
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("Server open on " + PORT);

            while (true) {
                log.info("Server waiting client");
                if(Clients.getInstance().checkLimitUser())
                    return;
                client = serverSocket.accept();
                log.info("Accepted: " + client.getInetAddress());
                Clients.getInstance().addClient(client);
                log.fine("Add client - " + client.getInetAddress());
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException", e);
        } finally {
            try {
                serverSocket.close();
                log.info("Server close");
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException", e);
            }
        }
    }
}