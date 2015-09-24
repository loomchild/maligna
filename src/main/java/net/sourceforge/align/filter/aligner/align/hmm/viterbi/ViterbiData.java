package net.sourceforge.align.filter.aligner.align.hmm.viterbi;

import net.sourceforge.align.coretypes.Category;

/**
 * Represents alignment data type stored in a matrix by {@link ViterbiAlgorithm}.
 * Includes current alignment category, its score and cumulative score of
 * all alignments scores from best path leading to this alignment including 
 * its score.
 * 
 * @author loomchild
 */
public class ViterbiData {
	
	private Category category;
	
	private float score;

	private float totalScore;
	
	/**
	 * Creates data.
	 * @param category category of an alignment
	 * @param score score of this alignment
	 * @param totalScore total score of this alignment including all previous
	 * 		alignments on the path 
	 */
	public ViterbiData(Category category, 
			float score, float totalScore) {
		this.category = category;
		this.score = score;
		this.totalScore = totalScore;
	}

	/**
	 * @return this alignment score
	 */
	public float getScore() {
		return score;
	}

	/**
	 * @return total score of this alignment including all previous alignments
	 * 		on the path 
	 */
	public float getTotalScore() {
		return totalScore;
	}
	
	/**
	 * @return this alignment category
	 */
	public Category getCategory() {
		return category;
	}

}
