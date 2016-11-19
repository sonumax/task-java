package Chat.Server;

import Chat.WorkWithXml;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {

    private static int PORT;
    private static ServerSocket serverSocket;
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private Socket client;
    private static final String PATH_TO_XML = "./src/Chat/Resources/properties.xml";

    public static void main(String[] args) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(PATH_TO_XML);

            PORT = Integer.parseInt(WorkWithXml.searchInXml(document, "//Properties/Port"));
        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        new Server().startServer(PORT);
    }

    private void startServer(int PORT) {
        try {

            serverSocket = new ServerSocket(PORT);
            log.info("Server open on " + PORT);

            while (true) {
                log.info("Server waiting client");
                client = serverSocket.accept();
                if (!Clients.getInstance().addListenerClient(client)) {
                    client.close();
                    continue;
                }
                log.info("Accepted: " + client.getInetAddress());
                log.fine("Add client - " + client.getInetAddress());
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException", e);
        } finally {
            try {
                Clients.getInstance().interruptAllClient();
                serverSocket.close();
                log.info("Server close");
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException", e);
            }
        }
    }
}
