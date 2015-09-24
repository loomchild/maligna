package net.sourceforge.align.filter.aligner;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;


/**
 * Represents aligner - for each alignment on input list aligns source segments
 * with target segments and appends obtained list of alignments to the result.
 * This implies that resulting list can have more alignments than input list 
 * but cannot have less. Does not change alignment contents. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class Aligner implements Filter {
	
	private AlignAlgorithm algorithm;
	
	public Aligner(AlignAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	/**
	 * For each alignment on input list aligns source segments with target
	 * segments, and appends the obtained alignment list to the result. 
	 * @throws AlignmentImpossibleException when it is not possible to align 
	 * 		texts
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		List<Alignment> newAlignmentList = new ArrayList<Alignment>();
		for (Alignment alignment : alignmentList) {
			List<Alignment> currentAlignmentList =
				algorithm.align(alignment.getSourceSegmentList(), 
						alignment.getTargetSegmentList());
			newAlignmentList.addAll(currentAlignmentList);
		}
		return newAlignmentList;
	}
	
}
