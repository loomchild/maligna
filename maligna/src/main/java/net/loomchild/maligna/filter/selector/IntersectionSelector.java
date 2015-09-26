package net.loomchild.maligna.filter.selector;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.comparator.Diff;
import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;
import net.loomchild.maligna.comparator.Comparator;

/**
 * Represents a filter that selects only alignments also occurring in given 
 * reference alignment list - creating set intersection of reference alignments 
 * and input alignment.
 * 
 * @author loomchild
 */
public class IntersectionSelector implements Filter {

	private List<Alignment> rightAlignmentList;
	
	/**
	 * Creates intersection selector.
	 * @param rightAlignmentList reference alignment list
	 */
	public IntersectionSelector(List<Alignment> rightAlignmentList) {
		this.rightAlignmentList = new ArrayList<Alignment>(rightAlignmentList);
	}
	
	/**
	 * Filters input alignment leaving only alignments that exist in 
	 * configured reference alignment (set intersection between alignment lists).
	 * @param leftAlignmentList Input alignment list.
	 * @return List containing selected alignments.
	 */
	public List<Alignment> apply(List<Alignment> leftAlignmentList) {
		Diff diff = Comparator.compare(leftAlignmentList, rightAlignmentList);
		return diff.getCommonList();	
	}
	
}
