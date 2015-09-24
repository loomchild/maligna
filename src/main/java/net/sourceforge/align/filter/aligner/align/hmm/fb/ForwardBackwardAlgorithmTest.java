package net.sourceforge.align.filter.aligner.align.hmm.fb;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.calculator.length.NormalDistributionCalculator;
import net.sourceforge.align.calculator.length.counter.CharCounter;
import net.sourceforge.align.calculator.length.counter.Counter;
import net.sourceforge.align.coretypes.CategoryDefaults;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmTest;
import net.sourceforge.align.matrix.FullMatrixFactory;
import net.sourceforge.align.matrix.MatrixFactory;

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
