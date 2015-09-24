package net.sourceforge.align.filter.selector;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Represents test suite consisting of all the classes in selector package.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FractionSelectorTest.class, OneToOneSelectorTest.class,
	IntersectionSelectorTest.class, DifferenceSelectorTest.class
})
public class SelectorTestSuite {
}
