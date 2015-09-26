package net.loomchild.maligna.filter.aligner.align.hmm.fb;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.length.counter.CharCounter;
import net.loomchild.maligna.calculator.length.counter.Counter;
import net.loomchild.maligna.coretypes.CategoryDefaults;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmTest;
import net.loomchild.maligna.matrix.FullMatrixFactory;
import net.loomchild.maligna.matrix.MatrixFactory;
import net.loomchild.maligna.calculator.length.NormalDistributionCalculator;

import org.junit.Before;

/**
 * Represents {@link ForwardBackwardAlgorithm} unit test.
 * @author loomchild
 *
 */
public class ForwardBackwardAlgorithmTest extends HmmAlignAlgorithmTest {

	private ForwardBackwardAlgorithm algorithm;
	
	protected ForwardBackwardAlgorithm getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * Constructs algorithm object. It is similar to Gale and Church algorithm 
	 * but based on Forward Backward method instead of Viterbi method.
	 */
	@Before
	public void setUp() {
		Counter counter = new CharCounter();
		Calculator calculator = new NormalDistributionCalculator(counter);
		MatrixFactory matrixFactory = new FullMatrixFactory();
		
		algorithm = new ForwardBackwardAlgorithm(calculator, 
				CategoryDefaults.BEST_CATEGORY_MAP, matrixFactory);
	}
	
}
