package parsers;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import regions.ChildRegion;
import regions.ParentRegion;
import regions.Region;

public class XmlHandler implements ContentHandler {

	public XmlHandler() {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void startDocument() throws SAXException {
		parent = null;
		region = null;
		content = "";
	}

	public void endDocument() throws SAXException {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (region )
		parent = new ParentRegion(parent);
		region = null;
		content = "";
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (region == null) {
			new ChildRegion(parent, content);
			content = "";
		}
		region = parent;
		parent = parent.getParent();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (region == null) {
			content += new String(ch, start, length);
		}
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}
	
	public Region getRegion() {
		return region;
	}
	
	private Region region;
	
	private ParentRegion parent;
	
	String content;

}
