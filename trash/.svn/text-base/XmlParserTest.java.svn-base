package parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import regions.Region;
import junit.framework.TestCase;

public class XmlParserTest extends TestCase {

	public static final String DOCUMENT = "test/documentx.xml";
	
	public XmlParserTest() {
		parser = XmlParser.getInstance();
	}
	
	public void testParse() {
		Region document = null;
		try {
			BufferedReader file = new BufferedReader(new FileReader(DOCUMENT));
			document = parser.parse(file);
		} catch (ParserException e) {
			fail("Parser error parsing correct document: " + e.getMessage());
		} catch (FileNotFoundException e) {
			fail("File not found: " + e.getMessage());
		}
		assertEquals("To zdanie to drugie jeszcze",document.getContent());
		assertEquals(2, document.getChildren().size());
		Region region = document.getChildren().get(0);
		assertEquals(2, region.getChildren().size());
		region = region.getChildren().get(1);
		System.out.println(region.getContent());
		assertEquals(0, region.getChildren().size());
		assertEquals("to drugie", region.getContent());
	}
	
	private Parser parser;

}
