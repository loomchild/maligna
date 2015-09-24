package net.sourceforge.align.util.bind;

/**
 * Represents JAXB parse / format exception. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class BindException extends RuntimeException {

	private static final long serialVersionUID = 4729174015756939625L;

	public BindException(String message) {
		super(message);
	}

	public BindException(String message, Throwable cause) {
		super(message, cause);
	}

	public BindException(Throwable cause) {
		super(cause);
	}

}
