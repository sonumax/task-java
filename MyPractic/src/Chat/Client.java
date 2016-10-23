package Chat;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {

    private static final String LOCALHOST = "localhost";
    private static final int PORT = 1234;
    private static final Logger log = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        try {
            Socket client = new Socket(LOCALHOST, PORT);
            log.info("Connected server on port: " + PORT);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream())) {

                BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));

                while (true) {
                    out.println(readConsole.readLine());
                    out.flush();
                    log.info("Send message");

                    log.info("Wait answer");
                    String answer = in.readLine();

                    System.out.println("Server -> " + answer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
