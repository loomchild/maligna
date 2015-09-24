package net.sourceforge.align.ui.console.command;

import static net.sourceforge.align.util.Util.getReader;
import static net.sourceforge.align.util.Util.getWriter;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.align.progress.ProgressManager;
import net.sourceforge.align.progress.WriterProgressObserver;
import net.sourceforge.align.ui.console.Maligna;
import net.sourceforge.align.ui.console.command.exception.CommandException;
import net.sourceforge.align.ui.console.command.exception.MissingParameterException;
import net.sourceforge.align.ui.console.command.exception.ParameterFormatException;
import net.sourceforge.align.ui.console.command.exception.ParametersParseException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public abstract class AbstractCommand implements Command {
	
	Log log = LogFactory.getLog(AbstractCommand.class);
	
	private BufferedReader in;
	private PrintWriter out;
	private PrintWriter err;
	private HelpFormatter helpFormatter;
	private Options options;
	
	public AbstractCommand() {
		this.in =  getReader(System.in);
		this.err = new PrintWriter(System.err, true);
		this.out = getWriter(System.out);
		this.helpFormatter = new HelpFormatter();
		this.options = new Options();
		initBasicOptions(options);
		initOptions(options);
	}
	
	public String getName() {
		String className = this.getClass().getSimpleName();
		int nameLength = className.length() - Command.class.getSimpleName().length();
		String name = className.substring(0, nameLength).toLowerCase();
		return name;
	}
	
	public void run(String[] args) {
		try {
			Parser optionParser = new BasicParser();
			CommandLine commandLine;
			try {
				commandLine = optionParser.parse(options, args);
			} catch (ParseException e) {
				throw new ParametersParseException(e);
			}
			if (commandLine.hasOption('h')) {
				printHelp();
			} else {
				String verbosity = commandLine.getOptionValue('v');
				setVerbosity(verbosity);
				setProgress(verbosity);
				run(commandLine);
			}
		} catch (CommandException e) {
			log.debug("Command exception.", e);
			err.println(e.getMessage());
			printUsage();
		} catch (Exception e) {
			log.error("Unknown exception", e);
		}
	}

	protected Options getOptions() {
		return options;
	}
	
	protected BufferedReader getIn() {
		return in;
	}
	
	protected PrintWriter getOut() {
		return out;
	}

	protected PrintWriter getErr() {
		return err;
	}
	
	protected void printUsage() {
		helpFormatter.printUsage(out, 80, Maligna.MAIN_COMMAND_NAME + " " + getName() + " -h");
	}
	
	protected void printHelp() {
		Maligna.printSignature();
		helpFormatter.printHelp(Maligna.MAIN_COMMAND_NAME + " " + getName(), options, true);
	}

	private void initBasicOptions(Options options) {
		options.addOption("h", "help", false, "Display help message.");
		options.addOption("v", "verbosity", true, "Set verbosity level. Correct values are: trace, debug, info[default], warn, error, fatal, quiet[no progress meter]");
	}
	
	private void setVerbosity(String verbosity) {
		Level level = Level.INFO;
		if ("trace".equals(verbosity)) {
			level = Level.FINEST;
		} else if ("debug".equals(verbosity)) {
			level = Level.FINE;
		} else if ("info".equals(verbosity)) {
			level = Level.INFO;
		} else if ("warn".equals(verbosity)) {
			level = Level.WARNING;
		} else if ("error".equals(verbosity)) {
			level = Level.SEVERE;
		} else if ("fatal".equals(verbosity)) {
			level = Level.SEVERE;
		} else if ("quiet".equals(verbosity)) {
			level = Level.OFF;
		} else if (verbosity != null) {
			throw new ParameterFormatException("verbosity");
		}
		Logger logger = Logger.getLogger("");
		logger.setLevel(level);
		for (Handler handler : logger.getHandlers()) {
			handler.setLevel(Level.ALL);
		}
		log.debug("Setting verbosity level to " + verbosity + ".");
	}
	
	private void setProgress(String verbosity) {
		if (!"quiet".equals(verbosity)) {
			log.debug("Enabling progress meter.");
			WriterProgressObserver progressObserver = new WriterProgressObserver(new OutputStreamWriter(System.err), 40);
			ProgressManager.getInstance().registerProgressObserver(progressObserver);
		}
	}
	
	protected abstract void initOptions(Options options);
	
	protected abstract void run(CommandLine commandLine);
	
	protected int createInt(CommandLine commandLine, String command) {
		return createInt(commandLine, command, null);
	}

	protected int createInt(CommandLine commandLine, String command, 
			Integer defaultValue) {
		String string = commandLine.getOptionValue(command);
		int value;
		if (string == null) {
			if (defaultValue != null) {
				value = defaultValue;
			} else {
				throw new MissingParameterException(command);
			}
		} else {
			try {
				value = Integer.parseInt(string);
			} catch (NumberFormatException e) {
				throw new ParameterFormatException(command);
			}
		}
		return value;
	}

	protected float createFloat(CommandLine commandLine, String command) {
		return createFloat(commandLine, command, null);
	}

	protected float createFloat(CommandLine commandLine, String command, 
			Float defaultValue) {
		String string = commandLine.getOptionValue(command);
		float value;
		if (string == null) {
			if (defaultValue != null) {
				value = defaultValue;
			} else {
				throw new MissingParameterException(command);
			}
		} else {
			try {
				value = Float.parseFloat(string);
			} catch (NumberFormatException e) {
				throw new ParameterFormatException(command);
			}
		}
		return value;
	}

}
