package net.loomchild.maligna.filter.aligner.align;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.aligner.AlignmentImpossibleException;

/**
 * Align algorithm mock used for unit testing. 
 * Returns alignments with given number of source and target segments 
 * (if possible). 
 *
 * @author Jarek Lipski (loomchild)
 */
public class AlignAlgorithmMock implements AlignAlgorithm {

	private int maxSegments;
	
	/**
	 * Creates aligner.
	 * @param maxSegments number of source / target segments in each alignment
	 */
	public AlignAlgorithmMock(int maxSegments) {
		this.maxSegments = maxSegments;
	}
	
	/**
	 * Aligns source segments to target segments. Resulting 
	 * alignments will always be of n-n category (if there are enough segments), 
	 * where n is configured max segments.
	 * 
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return alignment list
	 * @throws AlignmentImpossibleException never thrown because alignment is
	 * 		always possible with this aligner
	 */
	public List<Alignment> align(List<String> sourceSegmentList,
			List<String> targetSegmentList) {
		Iterator<String> sourceIterator = sourceSegmentList.iterator();
		Iterator<String> targetIterator = targetSegmentList.iterator();
		int maxSize = Math.max(sourceSegmentList.size(), 
				targetSegmentList.size());
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		Alignment alignment = null;
		for (int i = 0; i < maxSize; ++i) {
			if (i % maxSegments == 0) {
				alignment = new Alignment();
				alignmentList.add(alignment);
			}
			if (sourceIterator.hasNext()) {
				alignment.addSourceSegment(sourceIterator.next());
			}
			if (targetIterator.hasNext()) {
				alignment.addTargetSegment(targetIterator.next());
			}
		}
		return alignmentList;
	}
	

}
