package parsers;

import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import regions.ChildRegion;
import regions.ParentRegion;
import regions.Region;
import exceptions.InitializationException;

public class XmlParser implements Parser {

	{
		builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(false);  //We are not using namespaces
		builderFactory.setValidating(false); //Disable DTD
		builderFactory.setIgnoringComments(true);  //Ignore comments
		builderFactory.setIgnoringElementContentWhitespace(false);  //We don't ignore whitespaces because parser must be in validating mode to do this
	}
	
	public static XmlParser getInstance() {
		return instance;
	}

	public Region parse(Reader input) throws ParserException {
		Document document = parseOnly(input);
		Element element = document.getDocumentElement();
		return parseElement(null, element);
	}

	/**
	 * Creates nonvalidating parser.
	 */
	private XmlParser() {
		initializeBuilder();
	}

	/**
	 * Parse given file and return DOM document.
	 */
	private Document parseOnly(Reader input) throws ParserException {
		try {
			Document document = builder.parse(new InputSource(input));
			return document;
		} catch (SAXException e) {
			throw new ParserException("Parsing error", e);
		} catch (IOException e) {
			throw new ParserException("IO error", e);
		}
	}
	
	private Region parseElement(ParentRegion parent, Element node) {
		NodeList nodeList = node.getChildNodes();
		if ((nodeList.getLength() == 1) && (nodeList.item(0) instanceof Text)) {
			return new ChildRegion(parent, node.getNodeValue());
		} else if (node instanceof Element) {
			Element element = (Element)node;
			ParentRegion region = new ParentRegion(parent, 
					element.getNodeName());
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node domNode = nodeList.item(i);
				if (domNode instanceof Element) {
					Region child = parseElement(region, (Element)domNode);
					if (child != null) {
						region.addChild(child);
					}
				}
			}
			return region;
		} else {
			return null;
		}
	}
	
	/**
	 * Initialize document builder - parser.
	 */
	private void initializeBuilder() {
		try {
			builder = builderFactory.newDocumentBuilder();
			//Handle normal errors the same as fatal errors
			builder.setErrorHandler(new XmlErrorHandler());
		} catch (ParserConfigurationException e) {
			throw new InitializationException("Could not create XML parser: " + e.getMessage());
		}
	}
	
	private static XmlParser instance = new XmlParser();
	
	private static DocumentBuilderFactory builderFactory;
	
	private DocumentBuilder builder;

}
