package net.sourceforge.align;

import net.sourceforge.align.calculator.CalculatorTestSuite;
import net.sourceforge.align.comparator.ComparatorTestSuite;
import net.sourceforge.align.coretypes.AlignmentTest;
import net.sourceforge.align.filter.FilterTestSuite;
import net.sourceforge.align.formatter.FormatterTestSuite;
import net.sourceforge.align.model.ModelTestSuite;
import net.sourceforge.align.parser.ParserTestSuite;
import net.sourceforge.align.util.UtilTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Represents test suite for the whole program.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AlignmentTest.class, UtilTestSuite.class, 
	ParserTestSuite.class, FormatterTestSuite.class, 
	FilterTestSuite.class, ComparatorTestSuite.class,
	ModelTestSuite.class, CalculatorTestSuite.class
})
public class AlignTestSuite {

}
