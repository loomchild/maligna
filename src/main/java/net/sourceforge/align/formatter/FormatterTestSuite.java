package net.sourceforge.align.formatter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite including all formatter tests in this package and subpackages.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AlFormatterTest.class, PresentationFormatterTest.class, 
	TmxFormatterTest.class
})
public class FormatterTestSuite {

}
