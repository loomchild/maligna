package net.loomchild.maligna.util.bind;

import java.io.Reader;
import java.io.Writer;

import net.loomchild.maligna.util.bind.tmx.Tmx;

/**
 * Represents XML marshaller / unmarshaller of .tmx format.
 * Uses {@link MarshallerUnmarshaller}. Singleton.
 * 
 * @author loomchild
 */
public class TmxMarshallerUnmarshaller {
	
	public static final String CONTEXT = "net.loomchild.maligna.util.bind.tmx";
	
	/**
	 * List of XML schemas used for validation. 
	 * Order of appearance on this array is important!
	 */
	public static final String[] SCHEMA_ARRAY = new String[] {
			"net/loomchild/maligna/res/xml/xml.xsd", "net/loomchild/maligna/res/xml/tmx.xsd"
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
