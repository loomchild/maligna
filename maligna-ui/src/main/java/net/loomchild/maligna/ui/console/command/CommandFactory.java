package net.loomchild.maligna.ui.console.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CommandFactory {
	
	private static volatile CommandFactory instance;
	
	private Map<String, Command> commandMap;
	
		
	public static CommandFactory getInstance() 
	{
		if (instance == null) 
		{
			synchronized (CommandFactory.class)
			{
				if (instance == null)
				{
					instance = new CommandFactory();
				}
			}
		}
		return instance;
	}
	
	private CommandFactory() 
	{
		initCommandMap();
	}
	
	private void initCommandMap()
	{
		commandMap = new HashMap<String, Command>();
		addCommand(new AlignCommand());
		addCommand(new CompareCommand());
		addCommand(new FormatCommand());
		addCommand(new ModelCommand());
		addCommand(new ModifyCommand());
		addCommand(new ParseCommand());
		addCommand(new SelectCommand());
		addCommand(new MacroCommand());
		addCommand(new TestCommand());
	}
	
	private void addCommand(Command command) {
		commandMap.put(command.getName(), command);
	}
	
	public Command getCommand(String name) {
		return commandMap.get(name);
	}
	
	public Set<String> getCommandNameSet() {
		return commandMap.keySet();
	}
	
}
