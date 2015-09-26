package net.loomchild.maligna.filter.modifier.modify.split;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.filter.modifier.modify.ModifyAlgorithm;


/**
 * Represents modify algorithm splitting single segment into a list of segments.
 *
 * @author Jarek Lipski (loomchild)
 */
public abstract class SplitAlgorithm implements ModifyAlgorithm {
	
	/**
	 * Modifies a segment list by splitting each segment on the list and 
	 * adding the resulting list to an output list.
	 * @param segmentList source segment list
	 * @return output segment list 
	 */
	public List<String> modify(List<String> segmentList) {
		List<String> newSegmentList = new ArrayList<String>();
		for (String segment : segmentList) {
			List<String> currentSegmentList = split(segment);
			newSegmentList.addAll(currentSegmentList);
		}
		return newSegmentList;
	}
	
	/**
	 * Splits a segment into a list of segments.
	 * @param string input segment
	 * @return resulting segment list
	 */
	public abstract List<String> split(String string);

}
