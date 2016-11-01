package Chat.Client;

import Chat.Server.Clients;
import Chat.Server.Message;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static final String IP = "localhost";
    private static final int PORT = 1234;

    private String nameClient;
    private Message message;
    private String readLine;
    private Socket client;

    public static void main(String[] args) {
        new Client().startClient(IP, PORT);
    }

    private void startClient(String IP, int PORT) {
        try {
            client = new Socket(IP, PORT);
            log.info("Connected server on port: " + PORT);

            try (InputStream in = client.getInputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                 BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in))) {

                entryName(oos, readConsole);

                new Thread(new ReadMessage(client, in)).start();

                while (client.isConnected()) {
                    readLine = readConsole.readLine();
                    message = new Message(readLine, new Date(), nameClient, client.getInetAddress());
                    oos.writeObject(message);
                    oos.flush();
                }
            } finally {
                try {
                    client.close();
                    log.info("Socket - " + client.getInetAddress() + " close");
                } catch (IOException e) {
                    log.log(Level.SEVERE, "IOException", e);
                }
            }
        } catch (UnknownHostException e) {
            log.log(Level.SEVERE, "UnknownHostException", e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException", e);
        }
    }


    private void entryName(ObjectOutputStream oos, BufferedReader readConsole) throws IOException {
        System.out.print("Your name: ");
        nameClient = readConsole.readLine();
        System.out.println("Welcome " + nameClient);
        Message name = new Message("join", new Date(), nameClient, client.getInetAddress());
        oos.writeObject(name);
        oos.flush();
    }
}
