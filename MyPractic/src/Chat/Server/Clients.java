package Chat.Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Clients {
    private static Clients instance;
    private ArrayList<ListenerClient> listClients;
    private LinkedList<Message> lastMessage;
    private int limitLastMessage = 10;
    private int limitClients = 10;

    public static Clients getInstance() {
        if(instance == null)
            instance = new Clients();
        return instance;
    }

    private Clients() {
        listClients = new ArrayList<>();
        lastMessage = new LinkedList<>();
    }

    public void addClient(Socket client) {
        ListenerClient listenerClient = new ListenerClient(client);
        listClients.add(listenerClient);
        lastMessage.forEach(listenerClient::sendMessage);
    }

    public void sendMessageToAll(Message message) {
        if(lastMessage.size() == limitLastMessage)
            lastMessage.removeFirst();
        lastMessage.addLast(message);
        for (ListenerClient listenerClient : listClients)
            listenerClient.sendMessage(message);
    }

    public boolean checkLimitUser(){
        if(listClients.size() >=  limitClients)
            return true;
        return false;
    }
}
