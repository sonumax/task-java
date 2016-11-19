package Chat.Server;

import Chat.WorkWithXml;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {

    private static final int PORT = Integer.parseInt(WorkWithXml.getInstance().searchInXml("//Properties/Port"));;
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private static ServerSocket serverSocket;
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
                if (!Clients.getInstance().addListenerClient(client)) {
                    client.close();
                    continue;
                }
                log.info("Accepted: " + client.getInetAddress());
                log.fine("Add client - " + client.getInetAddress());
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException", e);
        } finally {
            try {
                Clients.getInstance().interruptAllClient();
                serverSocket.close();
                log.info("Server close");
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException", e);
            }
        }
    }
}
