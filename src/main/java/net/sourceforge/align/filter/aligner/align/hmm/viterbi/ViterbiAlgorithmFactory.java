package net.sourceforge.align.filter.aligner.align.hmm.viterbi;

import java.util.Map;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.matrix.MatrixFactory;

/**
 * Factory always producing objects of {@link ViterbiAlgorithm}.
 * @author loomchild
 */
public class ViterbiAlgorithmFactory implements HmmAlignAlgorithmFactory {

	public AlignAlgorithm createAlignAlgorithm(Calculator calculator, 
			Map<Category, Float> categoryMap, MatrixFactory matrixFactory) {
		return new ViterbiAlgorithm(calculator, categoryMap, matrixFactory);
	}
	
}
