package net.sourceforge.align.filter.modifier.modify.split;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents split algorithm mock.
 * Responsible for splitting input segment int susbsegments of given lenght.
 * Used for testing.
 *
 * @author Jarek Lipski (loomchild)
 */
public class SplitAlgorithmMock extends SplitAlgorithm {

	private int charsInSegment;
	
	/**
	 * Creates split algorithm splitting input segment into segments of given 
	 * length.
	 * @param charsInSegment output segment length.
	 */
	public SplitAlgorithmMock(int charsInSegment) {
		assert charsInSegment > 0;
		this.charsInSegment = charsInSegment;
	}
	
	/**
	 * Splits text into segments of given length (the last one can be shorter). 
	 * @param string input segment
	 * @return output segment list
	 */
	public List<String> split(String string) {
		List<String> segmentList = new ArrayList<String>();
		int start = 0;
		for(int end = start + charsInSegment; end < string.length(); 
				start += charsInSegment, end += charsInSegment) {
			String segment = string.substring(start, end);
			segmentList.add(segment);
		}
		String segment = string.substring(start);
		segmentList.add(segment);
		return segmentList;
	}

}
