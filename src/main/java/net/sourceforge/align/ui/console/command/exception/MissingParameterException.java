package net.sourceforge.align.ui.console.command.exception;

public class MissingParameterException extends CommandException {

	private static final long serialVersionUID = 6250339071503029391L;

	public MissingParameterException(String parameter) {
		super("Missing " + parameter + " parameter.");
	}

}
