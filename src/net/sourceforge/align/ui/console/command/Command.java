package net.sourceforge.align.ui.console.command;


public interface Command {
	
	public String getName();

	public void run(String[] args);

}
