package net.sourceforge.align.filter.aligner.align.hmm;

import java.util.Map;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.sourceforge.align.matrix.MatrixFactory;

/**
 * Represents a factory producing align algorithms based on Hidden Markov
 * Models (HMM). Used by {@link AdaptiveBandAlgorithm} to be independent
 * of actual algorithm.
 * @author loomchild
 */
public interface HmmAlignAlgorithmFactory {

	/**
	 * Creates align algorithm.
	 * @param calculator calculator
	 * @param categoryMap map of possible alignment categories
	 * @param matrixFactory factory creating matrices to be used by algorithm
	 * @return align algorithm
	 */
	public AlignAlgorithm createAlignAlgorithm(Calculator calculator, 
			Map<Category, Float> categoryMap, MatrixFactory matrixFactory);
	
}
