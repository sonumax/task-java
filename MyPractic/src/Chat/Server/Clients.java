package Chat.Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Clients {
    private static volatile Clients instance;
    private ArrayList<ListenerClient> listClients;
    private LinkedList<Message> lastMessage;
    private int limitLastMessage = 10;
    private int limitClients = 2;

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
        listClients = new ArrayList<>();
        lastMessage = new LinkedList<>();
    }

    public synchronized void addClient(Socket client) {
        ListenerClient listenerClient = new ListenerClient(client);
        listClients.add(listenerClient);
        lastMessage.forEach(listenerClient::sendMessage);
    }

    public synchronized void sendMessageToAll(Message message) {
        if(lastMessage.size() == limitLastMessage) {
            lastMessage.removeFirst();
        }
        lastMessage.addLast(message);
        for (ListenerClient listenerClient : listClients) {
            listenerClient.sendMessage(message);
        }
    }

    public synchronized boolean checkLimitUser(){
        if(listClients.size() >=  limitClients) {
            return true;
        }
        return false;
    }

    public synchronized void removeClient(Socket client) {
        for (int i = 0; i < listClients.size(); i++) {
            if(listClients.get(i).getSocket().equals(client)) {
                listClients.remove(i);
            }
        }
    }
}
