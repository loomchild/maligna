package align.util.bind;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IgnoreDTDEntityResolver implements EntityResolver {

	public IgnoreDTDEntityResolver() {
	}
	
	public InputSource resolveEntity(String publicId, String systemId) 
			throws SAXException, IOException {
		return new InputSource(new ByteArrayInputStream(new byte[0]));
	}
	
}
