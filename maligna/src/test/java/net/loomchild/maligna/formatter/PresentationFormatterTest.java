package net.loomchild.maligna.formatter;

import static net.loomchild.maligna.util.TestUtil.createAlignmentList;
import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

import org.junit.Test;

/**
 * Represents {@link PresentationFormatter} test.
 * 
 * @author loomchild
 */
public class PresentationFormatterTest {
	
	public static final String LINE_SEPARATOR = 
		System.getProperty("line.separator");

	public static final String EXPECTED = 	
		"Ala ma kota kot ma   | Wasserreservoir, Was" + LINE_SEPARATOR +
		"ale nie wie.         | serreservoir...     " + LINE_SEPARATOR +
		"Drugie.              |                     " + LINE_SEPARATOR +
		"                     |                     " + LINE_SEPARATOR +
		"Burza mózgów zawsze  |                     " + LINE_SEPARATOR +
		"dobrze robi.         |                     " + LINE_SEPARATOR +
		"_____________________|_____________________" + LINE_SEPARATOR +
		"_____________________|_____________________" + LINE_SEPARATOR +
		"                     | Immer nur Wasser    " + LINE_SEPARATOR;

	/**
	 * Tests if formatting empty text returns empty output.
	 */
	@Test
	public void formatEmpty() {
		StringWriter writer = new StringWriter();
		Formatter formatter = new PresentationFormatter(writer, 9);
		List<Alignment> alignmentList = Collections.emptyList();
		formatter.format(alignmentList);
		assertEquals("", writer.toString());
	}
	
	/**
	 * Tests if the output is the same as {@link #EXPECTED}.
	 */
	@Test
	public void format() {
		List<Alignment> alignmentList = createAlignmentList(
				AlFormatterTest.SOURCE_ARRAY, AlFormatterTest.TARGET_ARRAY);
		StringWriter writer = new StringWriter();
		Formatter formatter = new PresentationFormatter(writer, 43);
		formatter.format(alignmentList);
		assertEquals(EXPECTED + " ", writer.toString() + " ");
	}
	
}
