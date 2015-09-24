package net.sourceforge.align.filter.aligner.align;

import junit.framework.TestSuite;
import net.sourceforge.align.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithmTest;
import net.sourceforge.align.filter.aligner.align.hmm.viterbi.ViterbiAlgorithmTest;
import net.sourceforge.align.filter.aligner.align.onetoone.OneToOneAlgorithmTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Represents unit test suite for filter.aligner.align package.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	 AlignAlgorithmMockTest.class, OneToOneAlgorithmTest.class,
	 ViterbiAlgorithmTest.class, ForwardBackwardAlgorithmTest.class
})
public class AlignAlgorithmTestSuite extends TestSuite {

}
