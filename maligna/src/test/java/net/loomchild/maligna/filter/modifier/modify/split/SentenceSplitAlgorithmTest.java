package net.loomchild.maligna.filter.modifier.modify.split;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;



/**
 * Represents {@link SentenceSplitAlgorithm} unit test.
 *
 * @author Jarek Lipski (loomchild)
 */
public class SentenceSplitAlgorithmTest {

	public static final String TEXT = 
		"Ala ma kota. Prof. Kot nie wie kim jest. Ech\nNic.";
	
	public static final String[] SEGMENT_ARRAY =
		{"Ala ma kota.", " Prof.", " Kot nie wie kim jest.", " Ech\n", 
		"Nic."};
	
	private SentenceSplitAlgorithm splitter;
	
	@Before
	public void setUp() {
		this.splitter = new SentenceSplitAlgorithm();
	}
	
	/**
	 * Tests simple split.
	 */
	@Test
	public void stringSplit() {
		List<String> segmentList = splitter.split(TEXT);
		String[] segmentArray = segmentList.toArray(new String[segmentList.size()]);
		assertEquals(SEGMENT_ARRAY, segmentArray);
	}
	
}
