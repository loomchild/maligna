package net.sourceforge.align.filter.aligner;

import net.sourceforge.align.filter.aligner.align.AlignAlgorithmTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Represents unit test suite for filter.aligner package. 
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AlignerTest.class, UnifyAlignerTest.class, AlignAlgorithmTestSuite.class
})
public class AlignerTestSuite {
}
