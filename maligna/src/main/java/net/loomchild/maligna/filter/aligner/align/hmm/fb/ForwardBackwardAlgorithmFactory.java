package net.loomchild.maligna.filter.aligner.align.hmm.fb;

import java.util.Map;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.coretypes.Category;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.matrix.MatrixFactory;

/**
 * Factory always producing objects of {@link ForwardBackwardAlgorithm}.
 * @author loomchild
 */
public class ForwardBackwardAlgorithmFactory implements HmmAlignAlgorithmFactory {

	public AlignAlgorithm createAlignAlgorithm(Calculator calculator,
			Map<Category, Float> categoryMap, MatrixFactory matrixFactory) {
		return new ForwardBackwardAlgorithm(calculator, categoryMap, 
				matrixFactory);
	}
	
}
