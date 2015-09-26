package net.loomchild.maligna.filter.aligner.align.hmm.viterbi;

import java.util.Map;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.coretypes.Category;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.loomchild.maligna.matrix.MatrixFactory;

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
