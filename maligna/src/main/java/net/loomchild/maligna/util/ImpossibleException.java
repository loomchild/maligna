package net.loomchild.maligna.util;

/**
 * Represents exception that should never occur. Should be thrown when You want
 * to swallow other exception because you are sure that it won't be thrown.
 * 
 * @author Jarek Lipski (loomchild)
 */
public class ImpossibleException extends RuntimeException {

	private static final long serialVersionUID = -5899108883494773808L;

	public ImpossibleException() {
	}

	public ImpossibleException(String message) {
		super(message);
	}

	public ImpossibleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImpossibleException(Throwable cause) {
		super(cause);
	}

}
