package net.loomchild.maligna.filter.modifier.modify.split;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


/**
 * Represents {@link WordSplitAlgorithm} unit test. 
 * @author loomchild
 */
public class WordSplitAlgorithmTest {
	
	public static final String SPACE = "  ab\t 9\net ";
	
	public static final String[] EXPECTED_SPACE = 
		new String[] {"ab", "9", "et"};

	/**
	 * Checks if splitting on whitespace works as expected and that whitespace
	 * characters are removed from the output.
	 */
	@Test
	public void splitSpace() {
		WordSplitAlgorithm splitter = new WordSplitAlgorithm();
		List<String> wordList = splitter.split(SPACE);
		String[] wordArray = wordList.toArray(new String[wordList.size()]);
		assertEquals(EXPECTED_SPACE, wordArray);
	}

	public static final String PUNCTUATION = 
		"1. Ja, niżej  podpisan(I'm \"batman01\").";
	
	public static final String[] EXPECTED_PUNCTUATION = new String[] {
		"1", ".", "Ja", ",", "niżej", "podpisan", "(", "I", "'", "m", 
		"\"", "batman01", "\"", ")", "."};

	/**
	 * Checks if splitting after punctuation characters works as expected.
	 */
	@Test
	public void splitPunctuation() {
		WordSplitAlgorithm splitter = new WordSplitAlgorithm();
		List<String> wordList = splitter.split(PUNCTUATION);
		String[] wordArray = wordList.toArray(new String[wordList.size()]);
		assertEquals(EXPECTED_PUNCTUATION, wordArray);
	}

}
