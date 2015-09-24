package net.sourceforge.align.parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.util.bind.AlMarshallerUnmarshaller;
import net.sourceforge.align.util.bind.al.Alignmentlist;
import net.sourceforge.align.util.bind.al.Segmentlist;

/**
 * Represents parser of a native .al format.
 * Parses a document configured in constructor.
 *  
 * @author loomchild
 */
public class AlParser implements Parser {
	
	private Reader reader;
	
	/**
	 * Constructs parser.
	 * @param reader input document to be parsed
	 */
	public AlParser(Reader reader) {
		this.reader = reader;
	}

	/**
	 * Parses a document into a list of alignments. 
	 * Retrieves all information stored in this format including score. 
	 */
	public List<Alignment> parse() {
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		Alignmentlist al = 
			AlMarshallerUnmarshaller.getInstance().unmarshal(reader);
		for (net.sourceforge.align.util.bind.al.Alignment a : al.getAlignment()) {
			List<String> sourceSegmentList = 
				createSegmentList(a.getSourcelist());
			List<String> targetSegmentList = 
				createSegmentList(a.getTargetlist());
			float score = a.getScore().floatValue();
			Alignment alignment = new Alignment(sourceSegmentList, 
					targetSegmentList, score);
			alignmentList.add(alignment);
		}
		return alignmentList;
	}

	private List<String> createSegmentList(Segmentlist sl) {
		List<String> segmentList = new ArrayList<String>();
		for (String s : sl.getSegment()) {
			segmentList.add(s);
		}
		return segmentList;
	}
		
}
