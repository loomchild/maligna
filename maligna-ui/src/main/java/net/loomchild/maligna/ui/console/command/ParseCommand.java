package net.loomchild.maligna.ui.console.command;

import static net.loomchild.maligna.util.Util.getFileInputStream;
import static net.loomchild.maligna.util.Util.getReader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.parser.Parser;
import net.loomchild.maligna.parser.PlaintextParser;
import net.loomchild.maligna.ui.console.command.exception.MissingParameterException;
import net.loomchild.maligna.util.IORuntimeException;
import net.loomchild.maligna.formatter.AlFormatter;
import net.loomchild.maligna.formatter.Formatter;
import net.loomchild.maligna.parser.AlParser;
import net.loomchild.maligna.parser.TmxParser;
import net.loomchild.maligna.ui.console.command.exception.ParameterFormatException;
import net.loomchild.maligna.ui.console.command.exception.UnknownParameterException;
import net.loomchild.maligna.ui.console.command.exception.WrongArgumentCountException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


public class ParseCommand extends AbstractCommand {
	
	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Parser class. Valid values are: al, txt, tmx.");
		options.addOption("l", "languages", true, "Source and target language separated by comma (optional for tmx parser).");
	}
	
	public void run(CommandLine commandLine) {
		try {
			String cls = commandLine.getOptionValue('c');
			if (cls == null) {
				throw new MissingParameterException("class");
			}
			List<Alignment> alignmentList = new ArrayList<Alignment>();
			if (cls.equals("al")) {
				if (commandLine.getArgs().length < 1) {
					throw new WrongArgumentCountException("1, 2, 3, ...", commandLine.getArgs().length);
				}
				for (String fileName : commandLine.getArgs()) {
					Reader reader = getReader(getFileInputStream(fileName));
					Parser parser = new AlParser(reader);
					List<Alignment> currentAlignmentList = parser.parse();
					alignmentList.addAll(currentAlignmentList);
					reader.close();
				}
			} else if (cls.equals("txt")) {
				if ((commandLine.getArgs().length % 2) != 0) {
					throw new WrongArgumentCountException("2, 4, 6, ...", commandLine.getArgs().length);
				}
				for (int i = 0; i < commandLine.getArgs().length; i += 2) {
					String sourceFileName = commandLine.getArgs()[i];
					String targetFileName = commandLine.getArgs()[i + 1];
					Reader sourceReader = getReader(getFileInputStream(sourceFileName));
					Reader targetReader = getReader(getFileInputStream(targetFileName));
					Parser parser = new PlaintextParser(sourceReader, targetReader);
					List<Alignment> currentAlignmentList = parser.parse();
					alignmentList.addAll(currentAlignmentList);
					sourceReader.close();
					targetReader.close();
				}
			} else if (cls.equals("tmx")) {
				if (commandLine.getArgs().length < 1) {
					throw new WrongArgumentCountException("1, 2, 3, ...", commandLine.getArgs().length);
				}
				String languages = commandLine.getOptionValue('l');
				String[] languageArray;
				if (languages == null) {
					languageArray = new String[0];
				} else {
					languageArray = languages.split(",");
					if (languageArray.length != 2) {
						throw new ParameterFormatException("languages");
					}
				}
				for (String fileName : commandLine.getArgs()) {
					Reader reader = getReader(getFileInputStream(fileName));
					Parser parser;
					if (languageArray.length == 0) {
						parser = new TmxParser(reader);
					} else {
						parser = new TmxParser(reader, languageArray[0], 
								languageArray[1]);
					}
					List<Alignment> currentAlignmentList = parser.parse();
					alignmentList.addAll(currentAlignmentList);
					reader.close();
				}
				
			} else {
				throw new UnknownParameterException("class");
			}
			Formatter formatter = new AlFormatter(getOut());
			formatter.format(alignmentList);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
