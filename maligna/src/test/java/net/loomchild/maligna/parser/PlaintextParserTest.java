package net.loomchild.maligna.parser;

import static net.loomchild.maligna.util.TestUtil.assertAlignmentListEquals;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

import org.junit.Test;

/**
 * Represents {@link PlaintextParser} unit test.
 * @author loomchild
 */
public class PlaintextParserTest {

	public static final String SOURCE_STRING = "aaabbb";
	
	public static final String TARGET_STRING = "1122";
	
	public static final String[][] SOURCE_SEGMENT_ARRAY = {
		new String[] {SOURCE_STRING},
	};

	public static final String[][] TARGET_SEGMENT_ARRAY = {
		new String[] {TARGET_STRING},
	};

	@Test
	public void parseString() {
		Parser parser = new PlaintextParser(SOURCE_STRING, TARGET_STRING);
		List<Alignment> alignmentList = parser.parse();
		assertAlignmentListEquals(SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY,
				alignmentList);
	}

	@Test
	public void parseReader() {
		Reader sourceReader = new StringReader(SOURCE_STRING);
		Reader targetReader = new StringReader(TARGET_STRING);
		Parser parser = new PlaintextParser(sourceReader, targetReader);
		List<Alignment> alignmentList = parser.parse();
		assertAlignmentListEquals(SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY, 
				alignmentList);
	}
		
}
