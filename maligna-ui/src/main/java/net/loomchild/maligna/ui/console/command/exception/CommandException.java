package net.loomchild.maligna.ui.console.command.exception;

public class CommandException extends RuntimeException {

	private static final long serialVersionUID = 1962758074740784763L;
	
	public CommandException(String message) {
		super(message);
	}
	
	public CommandException(Throwable cause) {
		super(cause);
	}
	
	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}

}
