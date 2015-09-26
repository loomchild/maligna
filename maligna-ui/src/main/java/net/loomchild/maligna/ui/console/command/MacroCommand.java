package net.loomchild.maligna.ui.console.command;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;
import net.loomchild.maligna.filter.macro.GaleAndChurchMacro;
import net.loomchild.maligna.filter.macro.MooreMacro;
import net.loomchild.maligna.filter.macro.PoissonMacro;
import net.loomchild.maligna.filter.macro.TranslationMacro;
import net.loomchild.maligna.filter.meta.FilterDecorators;
import net.loomchild.maligna.formatter.AlFormatter;
import net.loomchild.maligna.formatter.Formatter;
import net.loomchild.maligna.parser.AlParser;
import net.loomchild.maligna.parser.Parser;
import net.loomchild.maligna.ui.console.command.exception.MissingParameterException;
import net.loomchild.maligna.ui.console.command.exception.UnknownParameterException;
import net.loomchild.maligna.filter.macro.PoissonTranslationMacro;

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
