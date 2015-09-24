package net.sourceforge.align.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Test suite containing unit tests for parser package.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlParserTest.class, PlaintextParserTest.class, TmxParserTest.class
        })
public class ParserTestSuite {

}
