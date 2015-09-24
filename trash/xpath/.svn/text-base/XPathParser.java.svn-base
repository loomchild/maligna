package align.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import align.splitter.Splitter;

/**
 * Reprezentuje splitter wydobywający z dokumentu XML określoną zawartość 
 * znaczników zawierających tekst, Wybór znaczników dodkonywany jest na 
 * podstawie zadanego wyrażenia w języku XPath. Warunek by były to znaczniki
 * zawierające tekst determinuje to że wyrażenie XPath musi się kończyć /text().
 * Latwo będzie w przyszłości rozszerzyć funkcjonalność o obsługe zawartości 
 * innych typów znaczników i atrybutów.
 * @author loomchild
 */
public class XPathParser implements Splitter {
	
	private static XPathFactory xPathFactory = XPathFactory.newInstance();

	private XPathExpression xPathExpression;
	
	public XPathParser(String expression) {
		try {
			XPath xPath = xPathFactory.newXPath();
			xPath.setNamespaceContext(new XmlNamespaceContext());
			this.xPathExpression = xPath.compile(expression);
		} catch (XPathExpressionException e) {
			throw new XPathParserException("Expression compilation error", e);
		}
	}

	public List<String> split(String string) {
		StringReader reader = new StringReader(string);
		return split(reader);
	}
	
	private List<String> split(Reader reader) {
		List<String> segmentList = new ArrayList<String>();
		try {
			InputSource inputSource = new InputSource(reader);
			NodeList nodeList = (NodeList)
				xPathExpression.evaluate(inputSource, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); ++i) {
	            Node node = nodeList.item(i);
	            if (node.getNodeType() == Node.TEXT_NODE) {
	            	segmentList.add(node.getTextContent());
	            }
	        }
		} catch (XPathExpressionException e) {
			throw new XPathParserException("Expression compilation error", e);
		}
		return segmentList;
	}

}

class XmlNamespaceContext implements NamespaceContext {

    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
        	throw new NullPointerException("Null prefix");
        } else if (XMLConstants.XML_NS_PREFIX.equals(prefix)) {
        	return XMLConstants.XML_NS_URI;
        } else {
            return XMLConstants.NULL_NS_URI;
        }
     }

     public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }

     public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }

}

