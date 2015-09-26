package net.loomchild.maligna.model.vocabulary;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import net.loomchild.maligna.model.ModelParseException;
import net.loomchild.maligna.model.Util;

import org.junit.Test;

/**
 * Represents vocabulary utilities test suite.
 * @author loomchild
 */
public class VocabularyUtilTest {

	public String VOCABULARY_STRING = 
		"1	ala\n" +
		"2	ma\n" +
		"3	kota\n" +
		" ";
	
	/**
	 * Test vocabulary parsing.
	 */
	@Test
	public void testParse() {
		StringReader reader = new StringReader(VOCABULARY_STRING);
		Vocabulary vocabulary = VocabularyUtil.parse(reader);
		assertEquals(3, vocabulary.getWordCount());
		assertEquals(1, (int)vocabulary.getWid("ala"));
		assertEquals("kota", vocabulary.getWord(3));
	}
	
	public String VOCABULARY_UNORDERED_STRING = 
		"1	ala\n" +
		"3	ma\n" +
		"2	kota\n" +
		"";

	/**
	 * Tests that vocabulary will throw an exception if vocabulary is unordered.
	 */
	@Test(expected=ModelParseException.class)
	public void testParseUnordered() {
		StringReader reader = new StringReader(VOCABULARY_UNORDERED_STRING);
		VocabularyUtil.parse(reader);
	}
	
	
	public static final List<List<Integer>> WID_LIST = 
			Util.createWidList(new int[][] {
					new int[] {1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3},
					new int[] {4, 4, 4, 5, 5, 6}
			});
	
	public static final Vocabulary VOCABULARY = 
			new Vocabulary(Arrays.asList(
					new String[] {"aaa", "bbb", "ccc", "ddd", "eee", "fff"}
			));
	
	
	public static final int MAX_WORD_COUNT_1 = 10;
	
	public static final int MIN_OCCURENCE_COUNT_1 = 3;

	public static final String[] EXPECTED_TRUNCATED_VOCABULARY_1 = 
			new String[] {
					"aaa", "bbb", "ccc", "ddd"
			};
	
	/**
	 * Tests whether 
	 * {@link VocabularyUtil#createTruncatedVocabulary(List, Vocabulary, int, int)
	 * works as expected in regard to enforcing minimum occurrence count.
	 */
	@Test
	public void testCreateTruncatedVocabularyMinOccurenceCount() {
		Vocabulary truncatedVocabulary = 
			VocabularyUtil.createTruncatedVocabulary(
				WID_LIST, VOCABULARY, MAX_WORD_COUNT_1, MIN_OCCURENCE_COUNT_1);
		assertVocabularyEquals(EXPECTED_TRUNCATED_VOCABULARY_1, 
				truncatedVocabulary);
	}


	public static final int MAX_WORD_COUNT_2 = 3;
	
	public static final int MIN_OCCURENCE_COUNT_2 = 1;

	public static final String[] EXPECTED_TRUNCATED_VOCABULARY_2 = 
			new String[] {
					"aaa", "bbb", "ccc"
			};

	/**
	 * Tests whether 
	 * {@link VocabularyUtil#createTruncatedVocabulary(List, Vocabulary, int, int)
	 * works as expected in regard to enforcing maximum word count.
	 */
	@Test
	public void testCreateTruncatedVocabularyMaxWordCount() {
		Vocabulary truncatedVocabulary = 
			VocabularyUtil.createTruncatedVocabulary(
				WID_LIST, VOCABULARY, MAX_WORD_COUNT_2, MIN_OCCURENCE_COUNT_2);
		assertVocabularyEquals(EXPECTED_TRUNCATED_VOCABULARY_2, 
				truncatedVocabulary);
	}


	public static final int MAX_WORD_COUNT_3 = 0;
	
	public static final int MIN_OCCURENCE_COUNT_3 = 3;

	public static final String[] EXPECTED_TRUNCATED_VOCABULARY_3 = 
			new String[] {};
	
	/**
	 * Checks if when the maximum word count is zero truncated vocabulary will 
	 * be empty.
	 */
	@Test
	public void testCreateTruncatedVocabularyMaxWordsZero() {
		Vocabulary truncatedVocabulary = 
			VocabularyUtil.createTruncatedVocabulary(
				WID_LIST, VOCABULARY, MAX_WORD_COUNT_3, MIN_OCCURENCE_COUNT_3);
		assertVocabularyEquals(EXPECTED_TRUNCATED_VOCABULARY_3, 
				truncatedVocabulary);
	}
	
	private void assertVocabularyEquals(String[] wordArray, 
			Vocabulary vocabulary) {
		assertEquals(wordArray.length, vocabulary.getWordCount());
		
	}

}
