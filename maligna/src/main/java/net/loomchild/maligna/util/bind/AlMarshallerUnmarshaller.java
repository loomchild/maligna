package net.loomchild.maligna.util.bind;

import java.io.Reader;
import java.io.Writer;

import net.loomchild.maligna.util.bind.al.Alignmentlist;

/**
 * Represents XML marshaller / unmarshaller of .al native format.
 * Uses {@link MarshallerUnmarshaller}. Singleton.
 * 
 * @author loomchild
 */
public class AlMarshallerUnmarshaller {
	
	public static final String CONTEXT = "net.loomchild.maligna.util.bind.al";
	
	public static final String SCHEMA = "net/loomchild/maligna/res/xml/al.xsd";

	private static volatile AlMarshallerUnmarshaller instance;
	
	private MarshallerUnmarshaller marshallerUnmarshaller;
	
	/**
	 * @return singleton instance
	 */
    public static AlMarshallerUnmarshaller getInstance() {
    	if (instance == null) {
    		synchronized (AlMarshallerUnmarshaller.class) {
    			if (instance == null) {
    				instance = new AlMarshallerUnmarshaller();
    			}
    		}
    	}
    	return instance;
    }

    private AlMarshallerUnmarshaller() {
    	this.marshallerUnmarshaller = new MarshallerUnmarshaller(CONTEXT, 
    			SCHEMA);
    }

	public void marshal(Alignmentlist al, Writer writer) {
		marshallerUnmarshaller.marshal(al, writer);
	}

	public Alignmentlist unmarshal(Reader reader) {
		return (Alignmentlist)marshallerUnmarshaller.unmarshal(reader);
	}
    
}
