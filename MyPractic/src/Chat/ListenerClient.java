package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ListenerClient extends Thread {

    private static final Logger log = Logger.getLogger(Server.class.getName());

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ListenerClient(Socket client) {
        this.socket = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            start();
            log.fine("Thread: " + this.getName() + " start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String answer;
        try {
            while (true) {
                log.fine("Server wait answer");
                answer = in.readLine();
                if (answer.equals("null"))
                    break;
                Clients.getInstance().sendMessageToAll(answer);
                System.out.println("Client -> " + answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                log.info("Socket - " + socket.getInetAddress() + " disconnect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    public Socket getSocket() {
        return socket;
    }
}
