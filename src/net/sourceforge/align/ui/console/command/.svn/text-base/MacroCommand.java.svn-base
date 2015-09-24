package net.sourceforge.align.ui.console.command;

import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.macro.GaleAndChurchMacro;
import net.sourceforge.align.filter.macro.MooreMacro;
import net.sourceforge.align.filter.macro.PoissonMacro;
import net.sourceforge.align.filter.macro.PoissonTranslationMacro;
import net.sourceforge.align.filter.macro.TranslationMacro;
import net.sourceforge.align.filter.meta.FilterDecorators;
import net.sourceforge.align.formatter.AlFormatter;
import net.sourceforge.align.formatter.Formatter;
import net.sourceforge.align.parser.AlParser;
import net.sourceforge.align.parser.Parser;
import net.sourceforge.align.ui.console.command.exception.MissingParameterException;
import net.sourceforge.align.ui.console.command.exception.UnknownParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class MacroCommand extends AbstractCommand {

	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Macro class. Valid values are: galechurch, moore, poisson, translation, poisson-translation.");
	}

	protected void run(CommandLine commandLine) {
		String cls = commandLine.getOptionValue('c');
		if (cls == null) {
			throw new MissingParameterException("class");
		}

		Filter filter;
		if (cls.equals("galechurch")) {
			filter = new GaleAndChurchMacro();
		} else if (cls.equals("moore")) {
			filter = new MooreMacro();
		} else if (cls.equals("poisson")) {
			filter = new PoissonMacro();
		} else if (cls.equals("translation")) {
			filter = new TranslationMacro();
		} else if (cls.equals("poisson-translation")) {
			filter = new PoissonTranslationMacro();
		} else {
			throw new UnknownParameterException("class");
		}

		filter = FilterDecorators.decorate(filter);

		Parser parser = new AlParser(getIn());
		Formatter formatter = new AlFormatter(getOut());
		List<Alignment> alignmentList = parser.parse();
		alignmentList = filter.apply(alignmentList);
		formatter.format(alignmentList);
	}

}
