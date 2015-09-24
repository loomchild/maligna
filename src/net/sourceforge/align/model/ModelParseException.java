package net.sourceforge.align.model;

/**
 * Represents model or vocabulary parse exception.
 *
 * @author Jarek Lipski (loomchild)
 */
public class ModelParseException extends RuntimeException {

	private static final long serialVersionUID = 6105226270677843760L;

	public ModelParseException(String message) {
		super(message);
	}


	public ModelParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
