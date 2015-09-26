package net.loomchild.maligna.formatter;

import static net.loomchild.maligna.util.Util.merge;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

/**
 * Represents a formatter writing to separate source and target plaintext files.
 * 
 * In each line of each file all all given alignment segments are written.
 * Subsequent lines in source and target files correspond to each other, number
 * of lines is equal in files. If alignment consists of more than one segment,
 * they are merged and space is inserted between them.
 * 
 * @author loomchild
 */
public class PlaintextFormatter implements Formatter {
	
	private PrintWriter sourceWriter;
	
	private PrintWriter targetWriter;
	
	/**
	 * Creates formatter.
	 * @param sourceWriter source file writer
	 * @param targetWriter target file writer
	 */
	public PlaintextFormatter(Writer sourceWriter, Writer targetWriter) {
		this.sourceWriter = new PrintWriter(sourceWriter, true);
		this.targetWriter = new PrintWriter(targetWriter, true);
	}

	/**
	 * Formats alignment using defined source and target writer.
	 * @param alignmentList input alignment list
	 */
	public void format(List<Alignment> alignmentList) {
		for (Alignment alignment : alignmentList) {
			printSegmentList(sourceWriter, alignment.getSourceSegmentList());
			printSegmentList(targetWriter, alignment.getTargetSegmentList());
		}
	}
	
	/**
	 * Merges segments and replaces end-of-line characters with spaces to make
	 * sure resulting files have the same number of lines.
	 * @param writer
	 * @param segmentList
	 */
	private void printSegmentList(PrintWriter writer, 
			List<String> segmentList) {
		String segment = merge(segmentList);
		segment = segment.replace("\n", " ");
		writer.println(segment);
	}

}
