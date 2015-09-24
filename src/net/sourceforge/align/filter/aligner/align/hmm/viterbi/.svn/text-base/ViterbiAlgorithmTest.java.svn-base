package net.sourceforge.align.filter.aligner.align.hmm.viterbi;

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
