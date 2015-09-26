package net.loomchild.maligna.ui.console.command.exception;

public class ParametersParseException extends CommandException {

	private static final long serialVersionUID = 4883570247314804577L;

	public ParametersParseException(Throwable cause) {
		super("Error parsing parameters.", cause);
	}

}
