package net.sourceforge.align.filter.meta;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Represents test suite covering all unit tests in filter.meta package and its
 * subpackages.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	CompositeFilterTest.class, 
	IgnoreInfiniteProbabilityAlignmentsFilterDecoratorTest.class
})
public class MetaTestSuite {
}
