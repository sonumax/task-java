package Chat.Client;

import Chat.Server.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ReadMessage implements Runnable{

    private static final Logger log = Logger.getLogger(Client.class.getName());

    private Socket client;
    private ObjectInputStream ois;
    private Message answerMessage;

    public ReadMessage(Socket client, InputStream in) throws IOException {
        ois = new ObjectInputStream(in);
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (client.isConnected()) {
                log.fine("Client wait answer");
                answerMessage = (Message) ois.readObject();
                if (answerMessage.getMessage().equals("null")) {
                    client.close();
                    break;
                }
                System.out.println(answerMessage.getNameClient() + " : " + answerMessage.getMessage());
            }
        } catch (ClassNotFoundException | IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
}


