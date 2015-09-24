package net.sourceforge.align.filter.aligner.align.hmm.fb;

import java.util.Map;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.matrix.MatrixFactory;

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
