package net.loomchild.maligna.filter.aligner.align.hmm.viterbi;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.length.counter.CharCounter;
import net.loomchild.maligna.calculator.length.counter.Counter;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmTest;
import net.loomchild.maligna.matrix.FullMatrixFactory;
import net.loomchild.maligna.calculator.length.NormalDistributionCalculator;
import net.loomchild.maligna.coretypes.CategoryDefaults;
import net.loomchild.maligna.matrix.MatrixFactory;

import org.junit.Before;

/**
 * Represents {@link ViterbiAlgorithm} unit test.
 * @author loomchild
 *
 */
public class ViterbiAlgorithmTest extends HmmAlignAlgorithmTest {

	private ViterbiAlgorithm algorithm;
	
	protected ViterbiAlgorithm getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * Constructs algorithm object. It is similar to Gale and Church algorithm.
	 */
	@Before
	public void setUp() {
		Counter counter = new CharCounter();
		Calculator calculator = new NormalDistributionCalculator(counter);
		MatrixFactory matrixFactory = new FullMatrixFactory();
		
		algorithm = new ViterbiAlgorithm(calculator, 
				CategoryDefaults.BEST_CATEGORY_MAP, matrixFactory);
	}

}
