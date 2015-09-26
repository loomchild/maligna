package net.loomchild.maligna.filter.modifier.modify.merge;

import static java.util.Collections.singletonList;

import java.util.List;

import net.loomchild.maligna.filter.modifier.modify.ModifyAlgorithm;


/**
 * Represents algorithm merging a few segments into one.
 * This operation can add extra characters between segments or modify segment 
 * contents - the important characteristic of it is that it always takes 
 * segment list but returns just one segment.
 *
 * @author Jarek Lipski (loomchild)
 */
public abstract class MergeAlgorithm implements ModifyAlgorithm {
	
	public List<String> modify(List<String> segmentList) {
		return singletonList(merge(segmentList));
	}

	/**
	 * Merges segments from input list into one output segment.
	 * @param segmentList source segment list
	 * @return output segment 
	 */
	public abstract String merge(List<String> segmentList);

}
