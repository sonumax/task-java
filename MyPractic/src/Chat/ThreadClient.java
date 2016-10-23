package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadClient implements Runnable {

    private static final Logger log = Logger.getLogger(Server.class.getName());
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ThreadClient(Socket client) {
        this.client = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            while (true) {
                log.info("Wait answer");
                String line = null;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client -> " + line);

                out.println(line);
                out.flush();
                log.info("Send message to client");
            }
    }

    public Socket getClient() {
        return client;
    }
}
