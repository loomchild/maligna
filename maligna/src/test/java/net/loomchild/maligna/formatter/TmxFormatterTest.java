package net.loomchild.maligna.formatter;

import static net.loomchild.maligna.util.TestUtil.assertAlignmentListEquals;
import static net.loomchild.maligna.util.TestUtil.createAlignmentList;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.parser.TmxParser;

import org.junit.Test;

/**
 * Represents {@link TmxFormatter} class test.
 * 
 * @author loomchild
 */
public class TmxFormatterTest {
	
	public static final String SOURCE_LANGUAGE = "pl";
	
	public static final String TARGET_LANGUAGE = "de";

	public static final String[][] EXPECTED_SOURCE_ARRAY = new String[][]{
		new String[]{"Ala ma kota kot ma\tale nie wie.\nDrugie.Burza mózgów zawsze " +
				"dobrze robi."},
		new String[]{}, 
	};

	public static final String[][] EXPECTED_TARGET_ARRAY = new String[][]{
		new String[]{"Wasserreservoir, Wasserreservoir..."},
		new String[]{"Immer nur Wasser"}, 
	};

	/**
	 * Tests whether alignment formatted by {@link TmxFormatter} can be 
	 * successfully parsed by {@link TmxParser}.
	 */
	@Test
	public void testFormatParse() {
		List<Alignment> alignmentList = createAlignmentList(
				AlFormatterTest.SOURCE_ARRAY, AlFormatterTest.TARGET_ARRAY);
		StringWriter writer = new StringWriter();
		Formatter formatter = new TmxFormatter(writer, SOURCE_LANGUAGE, 
				TARGET_LANGUAGE);
		formatter.format(alignmentList);
		Reader reader = new StringReader(writer.toString());
		TmxParser parser = new TmxParser(reader, SOURCE_LANGUAGE, 
				TARGET_LANGUAGE);
		List<Alignment> resultAlignmentList = parser.parse();
		assertAlignmentListEquals(EXPECTED_SOURCE_ARRAY, EXPECTED_TARGET_ARRAY, 
				resultAlignmentList);
	}
		
}
