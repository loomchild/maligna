package align.parser;

import static align.util.Util.readAllFromResource;
import static loomchild.util.testing.Utils.assertListEquals;

import java.util.List;

import org.junit.Test;



public class XPathParserTest {

	public static final String TEXT_FILE = "test/simpletext.xml";
	
	public static final String EXPRESSION = 
		"/document/paragraph/sentence[@language='en']/text()"; 
	
	public static final String[] SEGMENT_ARRAY = {
		"First sentence. ", "Third sentence."
	};

	@Test
	public void split() {
		XPathParser splitter = new XPathParser(EXPRESSION);
		String text = readAllFromResource(TEXT_FILE);
		List<String> segmentList = splitter.split(text);
		assertListEquals(SEGMENT_ARRAY, segmentList);
	}
	
}
