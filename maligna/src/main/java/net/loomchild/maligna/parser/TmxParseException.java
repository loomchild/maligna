package net.loomchild.maligna.parser;

/**
 * Represrnts TMX document parsing exception
 *
 * @author Jarek Lipski (loomchild)
 */
public class TmxParseException extends RuntimeException {

	private static final long serialVersionUID = 5752610837896744124L;

	public TmxParseException(String message) {
		super(message);
	}


	public TmxParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
