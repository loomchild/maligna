package net.loomchild.maligna.ui.console.command;


public interface Command {
	
	public String getName();

	public void run(String[] args);

}
