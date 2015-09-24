                   package net.sourceforge.align.filter.aligner.align;

import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.filter.aligner.AlignmentImpossibleException;

/**
 * Represents alignment algorithm.
 *
 * @author Jarek Lipski (loomchild)
 */
public interface AlignAlgorithm {

	/**
	 * Aligns source segment list with target segment list and returns a 
	 * list of alignments. All segments on the input list will be 
	 * present in resulting alignment in the same order as they were present
	 * on input lists.
	 * Alignments can be, one-to-zero, one-to-one,  
	 * many-to-zero, many-to-one, many-to-many (see {@link Category} for lists
	 * of alignment categories for different aligners).
	 * If both lists are empty returns empty list. If one of the lists is 
	 * empty returns only many-to-zero alignments (all-to-zero if possible).
	 *    
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return alignment list containing all segments.
	 * @throws AlignmentImpossibleException when it is impossible to align 
	 * 		given segments using this aligner
	 */
	public List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList);

}
