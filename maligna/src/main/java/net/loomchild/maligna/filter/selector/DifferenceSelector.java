package net.loomchild.maligna.filter.selector;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.comparator.Diff;
import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.comparator.Comparator;
import net.loomchild.maligna.filter.Filter;

/**
 * Represents a filter that selects only those alignments from input list 
 * that are not present in configured reference alingnment (set difference).
 *  
 * @author loomchild
 */
public class DifferenceSelector implements Filter {

	private List<Alignment> rightAlignmentList;
	
	/**
	 * Creates difference selector filter.
	 * @param rightAlignmentList reference alignment list
	 */
	public DifferenceSelector(List<Alignment> rightAlignmentList) {
		this.rightAlignmentList = new ArrayList<Alignment>(rightAlignmentList);
	}

	/**
	 * Filters input alignment leaving only alignments that do not exist in 
	 * configured reference alignment (set difference between alignment lists).
	 * @param leftAlignmentList Input alignment list.
	 * @return List containing selected alignments.
	 */
	public List<Alignment> apply(List<Alignment> leftAlignmentList) {
		Diff diff = Comparator.compare(leftAlignmentList, rightAlignmentList);
		return diff.getLeftList();
	}
	
}
