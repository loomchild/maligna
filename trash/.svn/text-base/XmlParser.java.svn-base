package parsers;

import java.io.IOException;
import java.io.Reader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import regions.Region;

import exceptions.InitializationException;

public class XmlParser implements Parser {


	public static XmlParser getInstance() {
		return instance;
	}

	public Region parse(Reader reader) throws ParserException {
		parser.setContentHandler(handler);
		try {
			parser.parse(new InputSource(reader));
		} catch (IOException e) {
			throw new ParserException("IO error during parsing",e);
		} catch (SAXException e) {
			throw new ParserException("SAX error during parsing",e);
		}
		return handler.getRegion();
	}
	
	private XmlParser() {
		try {
			parser = XMLReaderFactory.createXMLReader();
			handler = new XmlHandler();
		} catch (SAXException e) {
			throw new InitializationException("Cannot crete XML parser: " 
					+ e.getMessage());
		}
	}
	
	private static XmlParser instance = new XmlParser();
	
	private XMLReader parser;
	private XmlHandler handler;
	
}
