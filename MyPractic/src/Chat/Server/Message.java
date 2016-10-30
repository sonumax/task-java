package Chat.Server;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

public class Message implements Serializable{
    private String message;
    private Date dateCreateMessage;
    private String nameClient;
    private InetAddress IP;

    public Message(String message, Date dateCreateMessage, String nameClient, InetAddress IP) {
        this.message = message;
        this.dateCreateMessage = dateCreateMessage;
        this.nameClient = nameClient;
        this.IP = IP;
    }

    public String getNameClient() {
        return nameClient;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateCreateMessage() {
        return dateCreateMessage;
    }

    public InetAddress getIP() {
        return IP;
    }


}
