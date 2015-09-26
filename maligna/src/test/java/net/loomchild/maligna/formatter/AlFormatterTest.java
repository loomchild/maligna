package net.loomchild.maligna.formatter;

import static net.loomchild.maligna.util.TestUtil.assertAlignmentListEquals;
import static net.loomchild.maligna.util.TestUtil.createAlignmentList;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.parser.AlParser;

import org.junit.Test;

/**
 * Represents {@link AlFormatter} unit test.
 * @author loomchild
 */
public class AlFormatterTest {
	
	public static final String[][] SOURCE_ARRAY = new String[][]{
		new String[]{"Ala ma kota kot ma\tale nie wie.\nDrugie.", 
				"Burza mózgów zawsze dobrze robi."},
		new String[]{}, 
		new String[]{}, 
	};

	public static final String[][] TARGET_ARRAY = new String[][]{
		new String[]{"Wasserreservoir, Wasserreservoir..."},
		new String[]{},				
		new String[]{"Immer nur Wasser"}, 
	};

	/**
	 * Tests whether alignment formatted by {@link AlFormatter} can be 
	 * successfully parsed by {@link AlParser}.
	 */
	@Test
	public void testFormatParse() {
		List<Alignment> alignmentList = createAlignmentList(SOURCE_ARRAY,
				TARGET_ARRAY);
		StringWriter writer = new StringWriter();
		Formatter formatter = new AlFormatter(writer);
		formatter.format(alignmentList);
		Reader reader = new StringReader(writer.toString());
		AlParser parser = new AlParser(reader);
		List<Alignment> resultAlignmentList = parser.parse();
		assertAlignmentListEquals(SOURCE_ARRAY, TARGET_ARRAY, 
				resultAlignmentList);
	}
		
}
