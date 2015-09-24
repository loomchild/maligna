package net.sourceforge.align.filter.aligner.align.onetoone;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.aligner.AlignmentImpossibleException;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;

/**
 * Represents an alignment algorithm that always returns one-to-one alignments.
 * If such alignment is impossible can throw 
 * {@link AlignmentImpossibleException} or return some one-to-zero alignments
 * depending on configuration. Useful for forcing certain alignment - for 
 * example when we know that in both text there is the same number of 
 * paragraphs, using this algorithm we can assure they will be aligned correctly. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class OneToOneAlgorithm implements AlignAlgorithm {
	
	public static final boolean DEFAULT_STRICT = false;
	
	private boolean strict;
	
	/**
	 * Creates one to one alignment algorithm
	 * @param strict if it is true then aligner will throw an exception if 
	 * 		source and target segment counts are not equal
	 */
	public OneToOneAlgorithm(boolean strict) {
		this.strict = strict;
	}

	/**
	 * Creates one-to-one alignment algorithm with strict setting equal to
	 * {@link #DEFAULT_STRICT}.
	 */
	public OneToOneAlgorithm() {
		this(DEFAULT_STRICT);
	}

	/**
	 * Aligns source and target segments returning only one-to-one alignments
	 * (one source segment to one target segment).
	 * 
	 * If numbers of source and target segments are not equal throws 
	 * {@link AlignmentImpossibleException} if strict was set to true or 
	 * returns remaining alignments as zero-to-one if strict is set to false.  
	 * 
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return alignment list containing all source and target segments.
	 * @throws AlignmentImpossibleException when strict is true and counts
	 * 		of source and target input segments are different
	 */
	public List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		if (strict && (sourceSegmentList.size() != targetSegmentList.size())) {
			throw new AlignmentImpossibleException("Cannot align 1 to 1 " +
					"- segment amounts are not equal");
		} else if (sourceSegmentList.size() == 0 && 
				targetSegmentList.size() == 0) {
			return Collections.emptyList();
		} else {
			List<Alignment> alignmentList = new ArrayList<Alignment>(
					sourceSegmentList.size());
			Iterator<String> sourceIterator = sourceSegmentList.iterator();
			Iterator<String> targetIterator = targetSegmentList.iterator();
			while (sourceIterator.hasNext() || targetIterator.hasNext()) {
				Alignment alignment = new Alignment(
						getSegmentList(sourceIterator), 
						getSegmentList(targetIterator), 0.0f);
				alignmentList.add(alignment);
			}
			return alignmentList;
		}
	}
	
	private List<String> getSegmentList(Iterator<String> iterator) {
		if (iterator.hasNext()) {
			return singletonList(iterator.next());
		} else {
			return emptyList();
		}
		
	}

}
