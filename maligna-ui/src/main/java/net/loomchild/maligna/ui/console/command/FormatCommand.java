package net.loomchild.maligna.ui.console.command;

import static net.loomchild.maligna.util.Util.getFileOutputStream;
import static net.loomchild.maligna.util.Util.getWriter;

import java.io.Writer;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.formatter.AlFormatter;
import net.loomchild.maligna.formatter.HtmlFormatter;
import net.loomchild.maligna.formatter.InfoFormatter;
import net.loomchild.maligna.formatter.PlaintextFormatter;
import net.loomchild.maligna.parser.AlParser;
import net.loomchild.maligna.parser.Parser;
import net.loomchild.maligna.ui.console.command.exception.MissingParameterException;
import net.loomchild.maligna.ui.console.command.exception.WrongArgumentCountException;
import net.loomchild.maligna.formatter.Formatter;
import net.loomchild.maligna.formatter.PresentationFormatter;
import net.loomchild.maligna.formatter.TmxFormatter;
import net.loomchild.maligna.ui.console.command.exception.ParameterFormatException;
import net.loomchild.maligna.ui.console.command.exception.UnknownParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


public class FormatCommand extends AbstractCommand {
	
	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Formatter class. Valid values are: al, txt, tmx, presentation, html, info.");
		options.addOption("l", "languages", true, "Source and target language separated by comma (required for tmx formatter).");
		options.addOption("w", "width", true, "Output width (optional for presentation formatter, default " + PresentationFormatter.DEFAULT_WIDTH + ").");
	}
	
	public void run(CommandLine commandLine) {
		String cls = commandLine.getOptionValue('c');
		if (cls == null) {
			throw new MissingParameterException("class");
		}
		Formatter formatter;
		if (cls.equals("al")) {
			Writer writer = getSingleWriter(commandLine);
			formatter = new AlFormatter(writer);
		} else if (cls.equals("txt")) {
			if (commandLine.getArgs().length != 2) {
				throw new WrongArgumentCountException("2", commandLine.getArgs().length);
			}
			String sourceFileName = commandLine.getArgs()[0];
			String targetFileName = commandLine.getArgs()[1];
			Writer sourceWriter = getWriter(getFileOutputStream(sourceFileName));
			Writer targetWriter = getWriter(getFileOutputStream(targetFileName));
			formatter = new PlaintextFormatter(sourceWriter, targetWriter);
		} else if (cls.equals("tmx")) {
			Writer writer = getSingleWriter(commandLine);
			String languages = commandLine.getOptionValue('l');
			if (languages == null) {
				throw new MissingParameterException("languages");
			}
			String[] languageArray = languages.split(",");
			if (languageArray.length != 2) {
				throw new ParameterFormatException("languages");
			}
			formatter = new TmxFormatter(writer, languageArray[0], languageArray[1]);
		} else if (cls.equals("presentation")) {
			Writer writer = getSingleWriter(commandLine);
			int width = createInt(commandLine, "width", PresentationFormatter.DEFAULT_WIDTH);
			formatter = new PresentationFormatter(writer, width);
		} else if (cls.equals("html")) {
			Writer writer = getSingleWriter(commandLine);
			formatter = new HtmlFormatter(writer);
		} else if (cls.equals("info")) {
			Writer writer = getSingleWriter(commandLine);
			formatter = new InfoFormatter(writer);
		} else {
			throw new UnknownParameterException("class");
		}
		Parser parser = new AlParser(getIn());
		List<Alignment> alignmentList = parser.parse();
		formatter.format(alignmentList);
	}
	
	private Writer getSingleWriter(CommandLine commandLine) {
		Writer writer;
		if (commandLine.getArgs().length == 0) {
			writer = getOut();
		} else if (commandLine.getArgs().length == 1) {
			String fileName = commandLine.getArgs()[0];
			writer = getWriter(getFileOutputStream(fileName));
		} else {
			throw new WrongArgumentCountException("0, 1", commandLine.getArgs().length);
		}
		return writer;
	}
}
