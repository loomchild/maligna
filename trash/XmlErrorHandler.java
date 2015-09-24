package parsers;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Represents strict error handler. Used to report validation errors.
 * @author loomchild
 */
public class XmlErrorHandler implements ErrorHandler {
	
	/**
	 * Ignores warnings.
	 */
	public void warning(SAXParseException exception) throws SAXException {
		//Ignore warnings or log them?
	}

	/**
	 * Throws errors.
	 */
	public void error(SAXParseException exception) throws SAXException {
		throw exception;
	}

	/**
	 * Throws fatal errors.
	 */
	public void fatalError(SAXParseException exception) throws SAXException {
		throw exception;
	}

}
