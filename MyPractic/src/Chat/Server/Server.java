package Chat.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {

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
                client = serverSocket.accept();
                if (Clients.getInstance().checkLimitUser()) {
                    client.close();
                    continue;
                }
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
