package net.sourceforge.align.parser;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static net.sourceforge.align.util.Util.getReader;
import static net.sourceforge.align.util.Util.getResourceStream;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;

import org.junit.Test;

/**
 * Represents {@link AlParser} unit test.
 * @author loomchild
 */
public class AlParserTest {

	public static final String FILE = "net/sourceforge/align/res/test/simpletext.al";
	
	public static final String[][] SOURCE_SEGMENT_ARRAY = {
		new String[] {"First sentence. ", "Second sentence."},
		new String[] {},
		new String[] {},
	};

	public static final String[][] TARGET_SEGMENT_ARRAY = {
		new String[] {"Pierwsze zdanie."},
		new String[] {"Drugie zdanie."},
		new String[] {},
	};

	/**
	 * Test whether {@link AlParser} is able to parse a test file 
	 * stored in {@value #FILE} into {@link #SOURCE_SEGMENT_ARRAY} and
	 * {@link #TARGET_SEGMENT_ARRAY}.
	 * @throws Exception
	 */
	@Test
	public void parse() throws Exception {
		InputStream inputStream = getResourceStream(FILE);
		Reader reader = getReader(inputStream);
		AlParser parser = new AlParser(reader);
		List<Alignment> alignmentList = parser.parse();
		assertAlignmentListEquals(SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY,
				alignmentList);
	}

}
