package Chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {

    private static final int PORT = 1234;
    private static final Logger log = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                log.info("Server waiting client");
                Socket client = serverSocket.accept();
                log.info("Accepted: " + client.getInetAddress());

                Thread threadClient = new Thread(new ThreadClient(client));
                threadClient.start();
                log.fine("Thread: " + threadClient.getName() + " start");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
