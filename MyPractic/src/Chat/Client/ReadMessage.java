package Chat.Client;

import Chat.Server.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

class ReadMessage implements Runnable {

    private static final Logger log = Logger.getLogger(Client.class.getName());

    private ObjectInputStream ois;
    private Message answerMessage;

    public ReadMessage(InputStream in) throws IOException {
        ois = new ObjectInputStream(in);
    }

    @Override
    public void run() {
        try {
            while (true) {
                log.fine("Client wait answer");
                answerMessage = (Message) ois.readObject();
                if (answerMessage.getMessage().equals("null"))
                    break;
                System.out.println(answerMessage.getNameClient() + " : " + answerMessage.getMessage());
            }
        } catch (ClassNotFoundException | IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
}


