package net.sourceforge.align.util.date;

/**
 * This exception is thrown by {@link DateParser} when it encounters an
 * invalid date.
 * 
 * @author loomchild
 */
public class InvalidDateException extends RuntimeException {

	private static final long serialVersionUID = 7608394842578468135L;

	public InvalidDateException(String message) {
		super(message);
	}

}
