package net.sourceforge.align.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.modifier.modify.ModifyAlgorithm;

/**
 * <p>Represents a filter manipulating source or target segments in an 
 * alignment list.</p>
 * <p>The modification can be for example merging segments (merge package), 
 * splitting segments (split package) or changing segment contents 
 * (clean package).</p>
 * <p>Applies separate algorithms ({@link ModifyAlgorithm}) to source 
 * and target segments in each alignment on input list.</p>
 * @author loomchild
 */
public class Modifier implements Filter {

	private ModifyAlgorithm sourceAlgorithm;

	private ModifyAlgorithm targetAlgorithm;
	
	/**
	 * Creates modifies using two separate source and target segment
	 * modification algorithms.
	 * @param sourceAlgorithm source segment modification algorithm
	 * @param targetAlgorithm target segment modification algorithm
	 */
	public Modifier(ModifyAlgorithm sourceAlgorithm, 
			ModifyAlgorithm targetAlgorithm) {
		this.sourceAlgorithm = sourceAlgorithm;
		this.targetAlgorithm = targetAlgorithm;
	}

	/**
	 * Iterates over input alignment list and applies source algorithm 
	 * to source segments and target algorithm to target segments to each 
	 * alignment.
	 * @param alignmentList input alignment list
	 * @return list containing alignments with modified segments
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		List<Alignment> newAlignmentList = new ArrayList<Alignment>();
		for(Alignment alignment : alignmentList) {
			List<String> sourceSegmentList = 
				sourceAlgorithm.modify(alignment.getSourceSegmentList()); 
			List<String> targetSegmentList = 
				targetAlgorithm.modify(alignment.getTargetSegmentList()); 
			Alignment newAlignment = new Alignment(sourceSegmentList, 
					targetSegmentList, alignment.getScore());
			newAlignmentList.add(newAlignment);
		}
		return newAlignmentList;
	}
	
}
