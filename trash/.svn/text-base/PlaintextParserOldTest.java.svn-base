package parsers;

import junit.framework.TestCase;

public class PlaintextParserTest extends TestCase {

	public PlaintextParserTest() {
		this.parser = new PlaintextParser();
		parser.addSeparator("\n\n");
		parser.addSeparator("\n");
	}
	
	public void testParse() {
		
	}
	
	public void testSeparators() {
		PlaintextParser testParser = new PlaintextParser();
		assertEquals(0, testParser.getSeparatorList().size());
		testParser.addSeparator("aaa");
		assertEquals(1, testParser.getSeparatorList().size());
		testParser.addSeparator("bbb");
		assertEquals(2, testParser.getSeparatorList().size());
		testParser.removeSeparator(0);
		assertEquals(1, testParser.getSeparatorList().size());
		assertEquals("bbb", testParser.getSeparatorList().get(0));
	}
	
	private PlaintextParser parser;
	
}
