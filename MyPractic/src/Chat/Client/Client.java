package Chat.Client;

import Chat.Server.Clients;
import Chat.Server.Message;
import Chat.WorkWithXml;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread{

    private static final Logger log = Logger.getLogger(Client.class.getName());
    public static final String PATH_TO_PASSWORDS = "src/Chat/Resources/passwords.properties";
    private static final String PATH_TO_XML = "./src/Chat/Resources/properties.xml";
    private static  String IP;
    private static int PORT;

    private String nameClient;
    private String passwordClient;
    private String readLine;
    private Socket client;
    private Properties passwordsProp;

    public static void main(String[] args) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(PATH_TO_XML);

            IP = WorkWithXml.searchInXml(document, "//Properties/IP");
            PORT = Integer.parseInt(WorkWithXml.searchInXml(document, "//Properties/Port"));

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        new Client().startClient(IP, PORT);
    }

    private void startClient(String IP, int PORT) {
        try {
            client = new Socket(IP, PORT);
            log.info("Connected server on port: " + PORT);

            try (InputStream in = client.getInputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                 BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
                 FileInputStream fileInputStream = new FileInputStream(PATH_TO_PASSWORDS);
                 FileOutputStream fileOutputStream = new FileOutputStream(PATH_TO_PASSWORDS, true)) {

                passwordsProp = new Properties();
                passwordsProp.load(fileInputStream);

                Clients.getInstance().addListClient(this);

                Thread readMessage = new Thread(new ReadMessage(client, in));

                writeMessage(oos, entryName(readConsole, fileOutputStream));

                readMessage.setDaemon(true);
                readMessage.start();

                while (!this.isInterrupted()) {
                    readLine = readConsole.readLine();
                    writeMessage(oos, readLine);
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


    private String entryName(BufferedReader readConsole, FileOutputStream fileOutputStream) throws IOException {
        System.out.print("Your name: ");
        nameClient = readConsole.readLine();
        if(entryPassword(readConsole, fileOutputStream)){
            System.out.println("Welcome " + nameClient);
            return "join";
        } else {
            System.out.println("Sorry incorrect password");
            throw new IOException();
        }
    }

    private boolean entryPassword(BufferedReader readConsole, FileOutputStream fileOutputStream) throws IOException {
        if(passwordsProp.containsKey(nameClient)) {
            System.out.print("Entry you password: ");
            passwordClient = readConsole.readLine();
            if(passwordClient.equals(passwordsProp.getProperty(nameClient))) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.print("Write new password: ");
            passwordClient = readConsole.readLine();
            PrintStream pw = new PrintStream(fileOutputStream, true);
            pw.append(nameClient + " = " + passwordClient);
            pw.flush();
            return true;
        }
    }

    private void writeMessage(ObjectOutputStream oos ,String write) throws IOException {
        Message message = new Message(write, new Date(), nameClient, client.getInetAddress());
        oos.writeObject(message);
        oos.flush();
    }
}
