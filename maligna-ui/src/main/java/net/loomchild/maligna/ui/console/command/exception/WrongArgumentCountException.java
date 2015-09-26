package net.loomchild.maligna.ui.console.command.exception;

public class WrongArgumentCountException extends CommandException {

	private static final long serialVersionUID = 4883570247314804577L;

	public WrongArgumentCountException(String expected, int actual) {
		super("Wrong argument count. Expected " + expected + ", but was " + actual + ".");
	}

}
