package Chat.Server;

import Chat.Client.Client;
import Chat.WorkWithXml;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Clients {
    private static final String PATH_TO_XML = "./src/Chat/Resources/properties.xml";
    private static volatile Clients instance;
    private ArrayList<ListenerClient> listListenerClient;
    private ArrayList<Client> listClient;
    private LinkedList<Message> lastMessage;
    private int limitLastMessage;
    private int limitClients;

    public static Clients getInstance() {
        if(instance == null) {
            synchronized (Clients.class) {
                if(instance == null) {
                    instance = new Clients();
                }
            }
        }
        return instance;
    }

    private Clients() {
        listListenerClient = new ArrayList<>();
        lastMessage = new LinkedList<>();
        listClient = new ArrayList<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(PATH_TO_XML);

            limitLastMessage = Integer.parseInt(WorkWithXml.searchInXml(document, "//Properties/LimitLastMessage"));
            limitClients = Integer.parseInt(WorkWithXml.searchInXml(document, "//Properties/LimitClients"));
        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean addListenerClient(Socket client) {
        if(listListenerClient.size() >=  limitClients) {
            return false;
        }
        ListenerClient listenerClient = new ListenerClient(client);
        listListenerClient.add(listenerClient);
        lastMessage.forEach(listenerClient::sendMessage);
        return true;
    }

    public synchronized void addListClient(Client client) {
        if(listClient.size() >=  limitClients) {
            return;
        }
        listClient.add(client);
    }

    public synchronized void sendMessageToAll(Message message) {
        if(lastMessage.size() == limitLastMessage) {
            lastMessage.removeFirst();
        }
        lastMessage.addLast(message);
        for (ListenerClient listenerClient : listListenerClient) {
            listenerClient.sendMessage(message);
        }
    }

    public synchronized void removeClient(Socket client) {
        for (int i = 0; i < listListenerClient.size(); i++) {
            if(listListenerClient.get(i).getSocket().equals(client)) {
                listListenerClient.remove(i);
            }
        }
    }

    public synchronized void interruptAllClient() {
        listListenerClient.forEach(Thread::interrupt);
        listClient.forEach(Thread::interrupt);
    }
}
