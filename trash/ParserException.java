package parsers;

public class ParserException extends Exception {
	
	public ParserException(String message) {
		this(message, null);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = 100L; 

}
