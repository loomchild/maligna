package net.sourceforge.align.filter.meta;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;

/**
 * Represents a filter decorator ignoring all alignments with score
 * equal to {@link Float#NEGATIVE_INFINITY}. Can be used to force 
 * certain alignments / segmentations because they were checked by human.
 * 
 * Note: 
 * Decorators are classes that enhance or change behavior of underlying class.
 *  
 * @author loomchild
 */
public class IgnoreInfiniteProbabilityAlignmentsFilterDecorator implements Filter {

	private Filter filter;
	
	/**
	 * Creates decorator.
	 * @param filter filter to be decorated
	 */
	public IgnoreInfiniteProbabilityAlignmentsFilterDecorator(Filter filter) {
		this.filter = filter;
	}
	
	/**
	 * Iterates over a list of alignments and if the alignment has
	 * score equal to {@link Float#NEGATIVE_INFINITY} then it 
	 * is copied to resulting list. Otherwise alignment is stored
	 * in a helper list to which underlying filter will be applied
	 * when the next ignored alignment is encountered.
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		List<Alignment> resultList = new ArrayList<Alignment>();
		
		List<Alignment> currentList = new ArrayList<Alignment>();
		
		for (Alignment alignment : alignmentList) {
			if (alignment.getScore() == Float.NEGATIVE_INFINITY) {
				resultList.addAll(filter.apply(currentList));
				currentList.clear();
				resultList.add(alignment);
			} else {
				currentList.add(alignment);
			}
		}
		resultList.addAll(filter.apply(currentList));
		
		return resultList;
	}

}
