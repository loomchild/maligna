package net.sourceforge.align.filter.modifier.modify.clean;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.filter.modifier.modify.ModifyAlgorithm;


/**
 * Represents modify algorithm that cleans input segment list from useless
 * segments or characters inside segments.
 * @author loomchild
 */
public abstract class CleanAlgorithm implements ModifyAlgorithm {

	/**
	 * Modifies each individual segment by calling {@link #clean(String)}
	 * method (implemented by this class subclasses) for it. Stores the
	 * results in output list, ignoring a segment when {@link #clean(String)} 
	 * returns null. 
	 * @param segmentList source segment list
	 * @return cleaned segment list
	 */
	public List<String> modify(List<String> segmentList) {
		List<String> newSegmentList = new ArrayList<String>();
		for (String segment : segmentList) {
			String newSegment = clean(segment);
			if (newSegment != null) {
				newSegmentList.add(newSegment);
			}
		}
		return newSegmentList;
	}
	
	/**
	 * Modifies single individual segment. If returns null the segment is 
	 * removed from resulting list.
	 * @param segment Sinput segment
	 * @return modified segment or null if it should be removed from the result
	 */
	public abstract String clean(String segment);

}
