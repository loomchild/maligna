package net.sourceforge.align.model.translation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Represents model.translation package unit test suite.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	MutableSourceDataTest.class, MutableTranslationModelTest.class,
	TargetDataProbabilityComparatorTest.class, TranslationModelUtilTest.class
})
public class TranslationTestSuite {

}
