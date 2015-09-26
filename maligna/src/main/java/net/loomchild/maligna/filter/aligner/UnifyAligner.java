package net.loomchild.maligna.filter.aligner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;
import net.loomchild.maligna.filter.macro.MooreMacro;


/**
 * <p>Represents an aligner that aligns analogously to given reference alignment,
 * which means numbers of source and target segments in resulting list
 * will be the same as in reference alignment. This implies that numbers of
 * source and target segments must be identical in reference and input 
 * alignments. Additionally scores are copied from reference alignment to 
 * output alignments.</p>
 * 
 * <p>Useful when to perform an alignment it is required to modify or 
 * destroy segment contents (tokenization, stemming, removal of rare words, 
 * etc. - see {@link MooreMacro}). After that the original input can be unified with
 * damaged alignment list to obtain the undamaged result but aligned 
 * correctly.</p>
 * 
 * @author loomchild
 */
public class UnifyAligner implements Filter {
	
	private List<Alignment> referenceAlignmentList;
	
	public UnifyAligner(List<Alignment> referenceAlignmentList) {
		this.referenceAlignmentList = referenceAlignmentList;
	}

	/**
	 * Creates alignment list in which numbers of source and target segments 
	 * in subsequent alignments are the same as in reference alignments 
	 * (but the segment contents come from input alignment list).
	 * Also copies scores from reference alignment list to output alignment 
	 * list.
	 *  
	 * @param alignmentList input alignment list
	 * @return alignment list unified with reference alignment
	 * @throws AlignmentImpossibleException when numbers of source or target
	 * 		segments in input alignment list are different than on
	 * 		reference alignment list
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {

		List<String> sourceSegmentList = new ArrayList<String>();
		List<String> targetSegmentList = new ArrayList<String>();
		for (Alignment alignment : alignmentList) {
			sourceSegmentList.addAll(alignment.getSourceSegmentList());
			targetSegmentList.addAll(alignment.getTargetSegmentList());
		}

		Iterator<String> sourceSegmentIterator = sourceSegmentList.iterator();
		Iterator<String> targetSegmentIterator = targetSegmentList.iterator();
		List<Alignment> newAlignmentList = 
			new ArrayList<Alignment>(referenceAlignmentList.size());
		for (Alignment alignment : referenceAlignmentList) {
			List<String> newSourceSegmentList = getSegmentList(
					sourceSegmentIterator, 
					alignment.getSourceSegmentList().size());
			List<String> newTargetSegmentList = getSegmentList(
					targetSegmentIterator, 
					alignment.getTargetSegmentList().size());
			Alignment newAlignment = 
				new Alignment(newSourceSegmentList, newTargetSegmentList,
						alignment.getScore());
			newAlignmentList.add(newAlignment);
		}
		
		return newAlignmentList;

	}
	
	private List<String> getSegmentList(Iterator<String> segmentIterator, 
			int size) {
		List<String> segmentList = new ArrayList<String>(size);
		for (int i = 0; i < size; ++i) {
			try {
				String segment = segmentIterator.next();
				segmentList.add(segment);
			} catch (NoSuchElementException e) {
				throw new AlignmentImpossibleException("Segment counts in input " +
						"and reference alignment lists are not equal.");
			}
		}
		return segmentList;
	}

}
