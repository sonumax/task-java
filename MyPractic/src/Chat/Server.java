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
            log.info("Server waiting client");

            Socket client = serverSocket.accept();
            log.info("Accepted: " + client.getInetAddress());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream())) {

                while (true) {
                    log.info("Wait answer");
                    String line = in.readLine();
                    System.out.println("Client -> " + line);

                    out.println(line);
                    out.flush();
                    log.info("Send message to client");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
