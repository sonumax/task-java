package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {

    private static final String IP = "localhost";
    private static final int PORT = 1234;
    private static final Logger log = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        startClient(IP, PORT);
    }

    private static void startClient(String IP, int PORT) {
        Socket client = null;
        try {
            client = new Socket(IP, PORT);
            log.info("Connected server on port: " + PORT);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream());
                 BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in))) {

                Thread sendMessage = new Thread(new SendMessage(readConsole, out));
                sendMessage.start();

                String answer;
                while (true) {
                    log.fine("Client wait answer");
                    answer = in.readLine();
                    if (answer.equals("null"))
                        break;
                    System.out.println("Server -> " + answer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                log.info("Socket - " + client.getInetAddress() + " close");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
