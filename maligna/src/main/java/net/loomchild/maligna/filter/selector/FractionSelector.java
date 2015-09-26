package net.loomchild.maligna.filter.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;


/**
 * Represents a filter that selects given fraction of most probable 
 * alignments.
 *
 * @author Jarek Lipski (loomchild)
 */
public class FractionSelector implements Filter {
	
	private float fraction;
	
	/**
	 * Creates filter.
	 * @param fraction fraction that will be left after filtering, <0,1>
	 */
	public FractionSelector(float fraction) {
		assert fraction >= 0.0f && fraction <= 1.0f;
		this.fraction = fraction;
	}

	/**
	 * Selects most probable alignments from input list and leaves only 
	 * given fraction of the best ones. For example if list has 100 alignments
	 * and the fraction was set to 0.8, then the resulting list will have 
	 * 80 alignments with highest probability (lowest score).
	 * Does not change alignments order. 
	 * Resulting list can have few more elements if they have equal score.
	 * 
	 * TODO: why return more elements sometimes - maybe fix it so the number
	 * is always correct, by keeping only calculated number of identical 
	 * elements?
	 * @param alignmentList input alignment list
	 * @return filtered alignment list
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		float threshold = calculateThreshold(alignmentList);
		List<Alignment> filteredAlignmentList = new ArrayList<Alignment>();
		for (Alignment alignment : alignmentList) {
			if (alignment.getScore() <= threshold) {
				filteredAlignmentList.add(alignment);
			}
		}
		return filteredAlignmentList;
	}

	private float calculateThreshold(List<Alignment> alignmentList) {
		float[] scoreArray = new float[alignmentList.size()];
		int index = 0;
		for (Alignment alignment : alignmentList) {
			scoreArray[index] = alignment.getScore();
			++index;
		}
		Arrays.sort(scoreArray);
		float firstFiltered = fraction * (float)scoreArray.length - 0.5f;
		float threshold;
		if (firstFiltered < 0.0f) {
			threshold = Float.NEGATIVE_INFINITY;
		} else {
			threshold = scoreArray[(int)firstFiltered];
		}
		return threshold;
	}
	
}
