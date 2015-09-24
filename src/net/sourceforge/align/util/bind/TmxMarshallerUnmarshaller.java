package net.sourceforge.align.util.bind;

import java.io.Reader;
import java.io.Writer;

import net.sourceforge.align.util.bind.tmx.Tmx;

/**
 * Represents XML marshaller / unmarshaller of .tmx format.
 * Uses {@link MarshallerUnmarshaller}. Singleton.
 * 
 * @author loomchild
 */
public class TmxMarshallerUnmarshaller {
	
	public static final String CONTEXT = "net.sourceforge.align.util.bind.tmx";
	
	/**
	 * List of XML schemas used for validation. 
	 * Order of appearance on this array is important!
	 */
	public static final String[] SCHEMA_ARRAY = new String[] {
		"net/sourceforge/align/res/xml/xml.xsd", "net/sourceforge/align/res/xml/tmx.xsd" 
	};

	private static volatile TmxMarshallerUnmarshaller instance;
	
	private MarshallerUnmarshaller marshallerUnmarshaller;
	
	/**
	 * @return singleton instance
	 */
    public static TmxMarshallerUnmarshaller getInstance() {
    	if (instance == null) {
    		synchronized (TmxMarshallerUnmarshaller.class) {
    			if (instance == null) {
    				instance = new TmxMarshallerUnmarshaller();
    			}
    		}
    	}
    	return instance;
    }

    private TmxMarshallerUnmarshaller() {
    	this.marshallerUnmarshaller = new MarshallerUnmarshaller(CONTEXT, 
    			SCHEMA_ARRAY);
    }

	public void marshal(Tmx tmx, Writer writer) {
		marshallerUnmarshaller.marshal(tmx, writer);
	}

	public Tmx unmarshal(Reader reader) {
		return (Tmx)marshallerUnmarshaller.unmarshal(reader);
	}
    
}
