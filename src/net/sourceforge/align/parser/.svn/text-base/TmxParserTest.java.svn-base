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
 * Represents {@link TmxParser} unit test.
 * @author loomchild
 */
public class TmxParserTest {

	public static final String FILE = "net/sourceforge/align/res/test/simpletext.tmx";
	
	public static final String SOURCE_LANGUAGE = "en";

	public static final String TARGET_LANGUAGE = "pl";

	public static final String[][] SOURCE_SEGMENT_ARRAY = {
		new String[] {"First sentence. "},
		new String[] {"Second sentence."},
	};

	public static final String[][] TARGET_SEGMENT_ARRAY = {
		new String[] {"Pierwsze zdanie."},
		new String[] {},
	};

	/**
	 * Test if parsing {@value #FILE} works as expected.
	 * @throws Exception
	 */
	@Test
	public void parseCorrect() throws Exception {
		InputStream inputStream = getResourceStream(FILE);
		Reader reader = getReader(inputStream);
		TmxParser parser = new TmxParser(reader, SOURCE_LANGUAGE, 
				TARGET_LANGUAGE);
		List<Alignment> alignmentList = parser.parse();
		assertAlignmentListEquals(SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY,
				alignmentList);
	}

	public static final String BAD_SOURCE_LANGUAGE = "de";

	/**
	 * Test if parsing {@value #FILE} but with a source language  
	 * {@value #BAD_SOURCE_LANGUAGE} throws an exception because it contains
	 * more than one variant in this language in one translation unit.
	 * 
	 * @see TmxParser
	 * @throws Exception
	 */
	@Test(expected=TmxParseException.class)
	public void parseBadVariantCount() throws Exception {
		InputStream inputStream = getResourceStream(FILE);
		Reader reader = getReader(inputStream);
		TmxParser parser = new TmxParser(reader, BAD_SOURCE_LANGUAGE, 
				TARGET_LANGUAGE);
		parser.parse();
	}

}
