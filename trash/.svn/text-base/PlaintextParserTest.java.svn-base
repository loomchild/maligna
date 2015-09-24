package parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import regions.Region;

import junit.framework.TestCase;

public class PlaintextParserTest extends TestCase {

	public static final String FILE_NAME = "data/test/simpleinput.txt";
	
	public PlaintextParserTest() {
		this.parser = new PlaintextParser();
		parser.addSeparator("\n\n");
		parser.addSeparator("\n");
	}
	
	public void testParse() {
		Region region = null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(FILE_NAME));
			region = parser.parse(reader);
		} catch (FileNotFoundException e) {
			fail("Test file not found: " + e);
		} catch (ParserException e) {
			fail("Parser exception: " + e);
		}

		assertEquals("dokument1, paragraf1\n\ndokument2, paragraf1\n" +
				"dokument2, paragraf2", region.getContent());
		assertEquals(2, region.getChildren().size());
		region = region.getChildren().get(1);

		assertEquals("dokument2, paragraf1\ndokument2, paragraf2", 
				region.getContent());
		assertEquals(2, region.getChildren().size());
		region = region.getChildren().get(1);

		assertEquals("dokument2, paragraf2", 
				region.getContent());
		assertEquals(0, region.getChildren().size());
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
