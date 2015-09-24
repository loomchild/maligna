package net.sourceforge.align.filter.modifier.modify;

import net.sourceforge.align.filter.modifier.modify.merge.MergeTestSuite;
import net.sourceforge.align.filter.modifier.modify.split.SplitTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Represents test suite for classes in filter.modifier.moify package (
 * mostly implementations of {@link ModifyAlgorithm}.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	MergeTestSuite.class, SplitTestSuite.class
})
public class ModifyTestSuite {
}
