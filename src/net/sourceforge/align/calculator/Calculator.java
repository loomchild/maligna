package net.sourceforge.align.calculator;

import java.util.List;

/**
 * <p>Represents method of calculating probability of an alignment of given
 * source segments to given target segments. 
 * It's the heart of alignment algorithm.</p>
 * 
 * <p>The actual implementation can calculate the result using just segment
 * lengths (package length) or contents of the segments (package content).</p>
 *
 * @author Jarek Lipski (loomchild)
 */
public interface Calculator {

	/**
	 * Calculates score (equal to -ln(probability)) of alignment of given
	 * source segment to given target segments.
	 * 
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return result (-ln(probability)) of the alignment, >= 0
	 */
	public float calculateScore(List<String> sourceSegmentList, 
			List<String> targetSegmentList);

}
