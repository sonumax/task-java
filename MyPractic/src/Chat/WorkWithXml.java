package Chat;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;


public class WorkWithXml {

    private static volatile WorkWithXml instance;
    private static final String PATH_TO_XML = "./src/Chat/Resources/properties.xml";
    private DocumentBuilder documentBuilder;
    private Document document;
    private XPathFactory pathFactory;
    private XPath xpath;

    public static WorkWithXml getInstance() {
        if(instance == null) {
            synchronized (WorkWithXml.class) {
                if(instance == null) {
                    instance = new WorkWithXml();
                }
            }
        }
        return instance;
    }

    private WorkWithXml() {
        pathFactory = XPathFactory.newInstance();
        xpath = pathFactory.newXPath();

        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(PATH_TO_XML);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String searchInXml(String node){
        try {
            XPathExpression expr = xpath.compile(node);
            Node nodes = (Node) expr.evaluate(document, XPathConstants.NODE);
            return nodes.getTextContent();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
