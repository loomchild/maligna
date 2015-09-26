package net.loomchild.maligna.filter.selector;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;

/**
 * Represents the filter that selects only one to one alignments and removes
 * the rest.
 * 
 * @author Jarek Lipski (loomchild)
 */
public class OneToOneSelector implements Filter {
	
	/**
	 * Filters the alignment list by leaving only 1-1 alignments.
	 * 
	 * @param alignmentList input alignment list
	 * @return filtered alignment list
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		List<Alignment> filteredAlignmentList = new ArrayList<Alignment>();
		for (Alignment alignment : alignmentList) {
			if (alignment.getSourceSegmentList().size() == 1 &&
					alignment.getTargetSegmentList().size() == 1) {
				filteredAlignmentList.add(alignment);
			}
		}
		return filteredAlignmentList;
	}

}
