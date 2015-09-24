package net.sourceforge.align.filter;

import net.sourceforge.align.filter.aligner.AlignerTestSuite;
import net.sourceforge.align.filter.macro.MacroTestSuite;
import net.sourceforge.align.filter.meta.MetaTestSuite;
import net.sourceforge.align.filter.modifier.ModifierTestSuite;
import net.sourceforge.align.filter.selector.SelectorTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Represents test suite covering all unit tests in filter package and its
 * subpackages.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	SelectorTestSuite.class, ModifierTestSuite.class, AlignerTestSuite.class,
	MacroTestSuite.class, MetaTestSuite.class
})
public class FilterTestSuite {
}
