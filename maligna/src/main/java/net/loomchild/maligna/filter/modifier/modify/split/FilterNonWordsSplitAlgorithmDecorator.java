package net.loomchild.maligna.filter.modifier.modify.split;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a split algorithm that splits input segment using 
 * given algorithm but ignores all punctuation in the output segments.
 * To be used together with {@link WordSplitAlgorithm}.
 * Decorator design pattern.
 * 
 * @author loomchild
 *
 */
public class FilterNonWordsSplitAlgorithmDecorator extends SplitAlgorithm {
	
	private SplitAlgorithm splitAlgorithm;
	
	/**
	 * Creates splitter decorator.
	 * @param splitAlgorithm split algorithm to be used
	 */
	public FilterNonWordsSplitAlgorithmDecorator(SplitAlgorithm splitAlgorithm) {
		this.splitAlgorithm = splitAlgorithm;
	}

	@Override
	public List<String> split(String string) {
		List<String> segmentList = splitAlgorithm.split(string);
		List<String> resultSegmentList = new ArrayList<String>();
		for (String segment : segmentList) {
			// Checks whether segment consists only of letters and numbers.
			// Assumes that if the first character is a letter or number
			// then that's true.
			if (Character.isLetterOrDigit(segment.charAt(0))) {
				resultSegmentList.add(segment.toLowerCase());
			}
		}
		return resultSegmentList;
	}

}
