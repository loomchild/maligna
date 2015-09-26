package net.loomchild.maligna.formatter;

import java.io.Writer;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.util.bind.AlMarshallerUnmarshaller;
import net.loomchild.maligna.util.bind.al.Alignmentlist;
import net.loomchild.maligna.util.bind.al.Segmentlist;

/**
 * Represents formatter to native .AL format. 
 * 
 * This format preserves all information about alignments including scores.
 * Alignments are stored using a writer defined in constructor. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class AlFormatter implements Formatter {
	
	private Writer writer;
	
	/**
	 * Creates formatter.
	 * @param writer writer to which the output will be dumped.
	 */
	public AlFormatter(Writer writer) {
		this.writer = writer;
	}

	/**
	 * Formats alignments to a writer preserving all parameters.
	 */
	public void format(List<Alignment> alignmentList) {
		Alignmentlist al = new Alignmentlist();
		for (Alignment alignment : alignmentList) {
			net.loomchild.maligna.util.bind.al.Alignment a = new net.loomchild.maligna.util.bind.al.Alignment();
			a.setScore((double)alignment.getScore());
			a.setSourcelist(createSegmentList(
					alignment.getSourceSegmentList()));
			a.setTargetlist(createSegmentList(
					alignment.getTargetSegmentList()));
			al.getAlignment().add(a);
		}
		AlMarshallerUnmarshaller.getInstance().marshal(al, writer);
	}
	
	private Segmentlist createSegmentList(List<String> segmentList) {
		Segmentlist sl = new Segmentlist();
		for (String segment : segmentList) {
			sl.getSegment().add(segment);
		}
		return sl;
	}

}
