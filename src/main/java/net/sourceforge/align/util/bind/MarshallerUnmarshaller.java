package net.sourceforge.align.util.bind;

import static net.sourceforge.align.util.Util.getResourceStream;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.sourceforge.align.util.ResourceNotFoundException;

import org.xml.sax.SAXException;

/**
 * Represents configurable, validating XML marshaller / unmarshaller.
 * Implemented using JAXB technology.
 * 
 * @author loomchild
 */
public class MarshallerUnmarshaller {

	private Marshaller marshaller;

	private Unmarshaller unmarshaller;
	
	public MarshallerUnmarshaller(String context, 
			String[] schemaNameArray) {
		try {
			List<Source> sourceList = new ArrayList<Source>();
			for (String schemaName : schemaNameArray) {
				InputStream schemaStream = getResourceStream(schemaName);
				sourceList.add(new StreamSource(schemaStream));
			}
			SchemaFactory schemaFactory = SchemaFactory.newInstance(
		 			XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = 
				schemaFactory.newSchema(sourceList.toArray(new Source[]{}));
			JAXBContext jaxbcontext = JAXBContext.newInstance(context); 
			unmarshaller = jaxbcontext.createUnmarshaller();
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(new QuietValidationEventHandler());
			marshaller = jaxbcontext.createMarshaller();
			marshaller.setSchema(schema);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException e) {
			throw new BindException("JAXB error", e);
		} catch (SAXException e) {
			throw new BindException("Error parsing XML Schema", e);
		} catch (ResourceNotFoundException e) {
			throw new BindException("Resource not found", e);
		}
	}

	public MarshallerUnmarshaller(String context, String schemaName) {
		this(context, new String[] {schemaName});
	}

	public void marshal(Object object, Writer writer) {
		try {
			marshaller.marshal(object, writer);
		} catch (JAXBException e) {
			throw new BindException("JAXB marshalling error", e);
		}
	}

	public Object unmarshal(Reader reader) {
		try {
			return unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new BindException("JAXB unmarshalling error", e);
		}
	}


}
