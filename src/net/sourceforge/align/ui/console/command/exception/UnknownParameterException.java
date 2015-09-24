package net.sourceforge.align.ui.console.command.exception;

public class UnknownParameterException extends CommandException {

	private static final long serialVersionUID = 917612043442645990L;

	public UnknownParameterException(String parameter) {
		super("Unknown " + parameter + " parameter.");
	}

}
