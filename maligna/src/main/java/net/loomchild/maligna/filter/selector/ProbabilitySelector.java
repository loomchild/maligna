package net.loomchild.maligna.filter.selector;

import static net.loomchild.maligna.util.Util.toScore;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;


/**
 * Selects alignments with probability equal or greater than given threshold.
 *
 * @author Jarek Lipski (loomchild)
 */
public class ProbabilitySelector implements Filter {
	
	private double scoreThreshold;
	
	/**
	 * Creates selector.
	 * @param probabilityThreshold Minimum accepted alignment probability. 
	 * From range [0,1].
	 */
	public ProbabilitySelector(double probabilityThreshold) {
		assert probabilityThreshold >= 0.0f && probabilityThreshold <= 1.0f;
		this.scoreThreshold = toScore(probabilityThreshold);
	}

	/**
	 * Selects alignments with probability equal or greater than threshold.
	 * @param alignmentList input alignment list
	 * @return list containing selected alignments
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		List<Alignment> selectedAlignmentList = new ArrayList<Alignment>();
		for (Alignment alignment : alignmentList) {
			if (alignment.getScore() <= scoreThreshold) {
				selectedAlignmentList.add(alignment);
			}
		}
		return selectedAlignmentList;
	}
	
}
