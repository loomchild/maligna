package net.sourceforge.align.ui.console.command;

import static net.sourceforge.align.comparator.Comparator.compare;
import static net.sourceforge.align.util.Util.getFileInputStream;
import static net.sourceforge.align.util.Util.getReader;
import static net.sourceforge.align.util.Util.round;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.align.comparator.Diff;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.formatter.Formatter;
import net.sourceforge.align.formatter.PresentationFormatter;
import net.sourceforge.align.parser.AlParser;
import net.sourceforge.align.parser.Parser;
import net.sourceforge.align.ui.console.command.exception.WrongArgumentCountException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


public class CompareCommand extends AbstractCommand {
	
	private static char INTERSECTION_SYMBOL = '\u2229';
	
	protected void initOptions(Options options) {
		options.addOption("d", "diff", false, "Print differences.");
		options.addOption("w", "width", true, "Differences output width (optional, default " + PresentationFormatter.DEFAULT_WIDTH + ").");
	}
	
	public void run(CommandLine commandLine) {
		if (commandLine.getArgs().length != 2) {
			throw new WrongArgumentCountException("2", commandLine.getArgs().length);
		}
		
		String leftFileName = commandLine.getArgs()[0];
		String rightFileName = commandLine.getArgs()[1];
		Reader leftReader = getReader(getFileInputStream(leftFileName));
		Reader rightReader = getReader(getFileInputStream(rightFileName));
		Parser leftParser = new AlParser(leftReader);
		Parser rightParser = new AlParser(rightReader);
		
		int width = createInt(commandLine, "width", PresentationFormatter.DEFAULT_WIDTH);

		Formatter formatter = new PresentationFormatter(getErr(), width);

		boolean showDiff = commandLine.hasOption('d');

		List<Alignment> leftAlignmentList = leftParser.parse();
		List<Alignment> rightAlignmentList = rightParser.parse();

		Diff diff = compare(leftAlignmentList, rightAlignmentList);
		
		if (showDiff) {
			
			Iterator<List<Alignment>> leftIterator = diff.getLeftGroupList().iterator();
			Iterator<List<Alignment>> rightIterator = diff.getRightGroupList().iterator();
			
			while (leftIterator.hasNext()) {
				
				List<Alignment> leftGroup = leftIterator.next();
				List<Alignment> rightGroup = rightIterator.next();
				
				getErr().println("< left alignments");
				getErr().println();
				formatter.format(leftGroup);
				getErr().println();
				getErr().println();

				getErr().println("> right alignments");
				getErr().println();
				formatter.format(rightGroup);
				getErr().println();
				getErr().println();

				getErr().println();
				
			}
			
		}

		int commonAlignmentCount = diff.getCommonList().size();
		double precision = round((double)commonAlignmentCount /
				(double)rightAlignmentList.size(), 2);
		double recall = round((double)commonAlignmentCount /
				(double)leftAlignmentList.size(), 2);
		getErr().println(
				"Precision |A " + INTERSECTION_SYMBOL + " B| / |B| = " + 
				precision);
		getErr().println(
				"Recall    |A " + INTERSECTION_SYMBOL + " B| / |A| = " + 
				recall);
	}
	
}
