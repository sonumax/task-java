package Chat.Server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListenerClient extends Thread {

    private static final Logger log = Logger.getLogger(Server.class.getName());

    private Socket socket;
    private BufferedReader in;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Message answerClientMessage;

    public ListenerClient(Socket client) {
        this.socket = client;
        try {
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            start();
            log.fine("Thread: " + this.getName() + " start");
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException", e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                log.fine("Server wait answer");
                answerClientMessage = (Message) ois.readObject();
                if(answerClientMessage.getMessage().equals("null"))
                    break;
                Clients.getInstance().sendMessageToAll(answerClientMessage);
            }
        } catch (ClassNotFoundException | IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        } finally {
            try {
                socket.close();
                log.info("Socket - " + socket.getInetAddress() + " disconnect");
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException", e);
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException", e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
