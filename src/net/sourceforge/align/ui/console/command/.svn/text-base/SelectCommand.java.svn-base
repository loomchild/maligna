package net.sourceforge.align.ui.console.command;

import static net.sourceforge.align.util.Util.getFileInputStream;
import static net.sourceforge.align.util.Util.getReader;

import java.io.Reader;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.meta.FilterDecorators;
import net.sourceforge.align.filter.selector.DifferenceSelector;
import net.sourceforge.align.filter.selector.FractionSelector;
import net.sourceforge.align.filter.selector.IntersectionSelector;
import net.sourceforge.align.filter.selector.OneToOneSelector;
import net.sourceforge.align.filter.selector.ProbabilitySelector;
import net.sourceforge.align.formatter.AlFormatter;
import net.sourceforge.align.formatter.Formatter;
import net.sourceforge.align.parser.AlParser;
import net.sourceforge.align.parser.Parser;
import net.sourceforge.align.ui.console.command.exception.MissingParameterException;
import net.sourceforge.align.ui.console.command.exception.UnknownParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


public class SelectCommand extends AbstractCommand {
	
	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Selector class. Valid values are: one-to-one, fraction, probability, intersection, difference");
		options.addOption("f", "fraction", true, "Fraction <0, 1> to leave in alignment. Required by fraction selector.");
		options.addOption("p", "probability", true, "Probability threshold, <0, 1>, to leave mapping in alignment. Required by preobability selector.");
		options.addOption("a", "alignment", true, "Other alignment. Required by intersection and difference selectors.");
		options.addOption("h", "help", false, "Display this help message.");
	}
	
	protected void run(CommandLine commandLine) {
		String cls = commandLine.getOptionValue('c');
		if (cls == null) {
			throw new MissingParameterException("class");
		}
		Filter filter;
		
		if (cls.equals("one-to-one")) {
			filter = new OneToOneSelector();
		} else if (cls.equals("fraction")) {
			float fraction = createFloat(commandLine, "fraction");
			filter = new FractionSelector(fraction);
		} else if (cls.equals("probability")) {
			float probability = createFloat(commandLine, "probability");
			filter = new ProbabilitySelector(probability);
		} else if (cls.equals("intersection") || cls.equals("difference")) {
			String alignmentString = commandLine.getOptionValue("alignment");
			if (alignmentString == null) {
				throw new MissingParameterException("alignment");
			}
			List<Alignment> alignment = loadAlignmentList(alignmentString);
			if (cls.equals("intersection")) {
				filter = new IntersectionSelector(alignment);
			} else if (cls.equals("difference")) {
				filter = new DifferenceSelector(alignment);
			} else {
				throw new UnknownParameterException("class");
			}
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

	private List<Alignment> loadAlignmentList(String fileName) {
		Reader reader = getReader(getFileInputStream(fileName));
		Parser parser = new AlParser(reader);
		return parser.parse();
	}

}
