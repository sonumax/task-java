package Chat;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.*;


public class WorkWithXml {

    public static String searchInXml(Document document ,String node) throws XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile(node);
        Node nodes = (Node) expr.evaluate(document, XPathConstants.NODE);
        return nodes.getTextContent();
    }
}
