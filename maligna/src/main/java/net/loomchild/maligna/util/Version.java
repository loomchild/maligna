package net.loomchild.maligna.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.Manifest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Retrieves program version and build date from META-INF/MANIFEST.MF file 
 * stored in a JAR. Singleton.
 * @author loomchild
 */
public class Version {
	
    private static final Log log = LogFactory.getLog(Version.class);
	
	public static final String VERSION_ATTRIBUTE = "Implementation-Version";  
	public static final String DATE_ATTRIBUTE = "Build-Date";  
	
	private static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";
	
	private static Version instance = new Version();
	
	private String version;

	private String date;
	
	public static Version getInstance() {
		return instance;
	}
	
	private Version() {
		try {
			Manifest manifest = getJarManifest(Version.class);
			version = manifest.getMainAttributes().getValue(VERSION_ATTRIBUTE);
			date = manifest.getMainAttributes().getValue(DATE_ATTRIBUTE);
		} catch (ResourceNotFoundException e) {
			// Ignore, attributes stay null
			log.debug("Version number cannot be retrieved.");
		}
	}

	public String getVersion() {
		return version;
	}
	
	public String getDate() {
		return date;
	}
	
	/**
	 * Returns Manifest of a jar containing given class. If class is not
	 * in a jar, throws {@link ResourceNotFoundException}.
	 * @param klass Class.
	 * @return Manifest.
	 * @throws ResourceNotFoundException Thrown if manifest was not found.
	 */
	private Manifest getJarManifest(Class<?> klass) {
        URL classUrl = klass.getResource(klass.getSimpleName() + ".class");
        if (classUrl == null) {
            throw new IllegalArgumentException("Class not found: " + 
                    klass.getName() + ".");
        }
        String classPath = classUrl.toString();
        int jarIndex = classPath.indexOf('!');
        if (jarIndex != -1) {
            String manifestPath = 
            	classPath.substring(0, jarIndex + 1) + MANIFEST_PATH;
            try {
                URL manifestUrl = new URL(manifestPath);
                InputStream manifestStream = manifestUrl.openStream();
                Manifest manifest = new Manifest(manifestStream);
                return manifest;
            } 
            catch (IOException e) {
            	throw new ResourceNotFoundException(
            			"IO Error retrieving manifest.", e);
            }
        }
        else {
        	throw new ResourceNotFoundException(
        			"Class is not in a JAR archive " + klass.getName() + ".");
        }
    }
	
}
