package net.sourceforge.align.util.bind;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * XML validation event handler. On fatal error or error fails the valiadation.
 * Warnings are logged on DEBUG level.
 *  
 * @author loomchild
 */
public class QuietValidationEventHandler implements ValidationEventHandler {

	private static Log log = LogFactory.getLog(QuietValidationEventHandler.class);

	public boolean handleEvent(ValidationEvent event) {
		if ((event.getSeverity() == ValidationEvent.FATAL_ERROR) || 
				(event.getSeverity() == ValidationEvent.ERROR)) {
			return false;
		} else if (event.getSeverity() == ValidationEvent.WARNING) {
			log.debug("Validation warning: " + event.getMessage());
			return true;
		} else {
			log.fatal("Unknown validation event severity: " + event.getSeverity());
			return false;
		}
	}

}
