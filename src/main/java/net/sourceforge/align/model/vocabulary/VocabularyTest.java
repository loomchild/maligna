package net.sourceforge.align.model.vocabulary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Represents {@link Vocabulary} unit test.
 * @author loomchild
 */
public class VocabularyTest {

	/**
	 * Performs various tests on vocabulary including adding words, getting
	 * the word ids, etc.
	 */
	@Test
	public void testVocabulary() {
		Vocabulary vocabulary = new Vocabulary();
		assertFalse(vocabulary.containsWord("a b"));
		assertNull(vocabulary.getWid("a b"));
		assertTrue(vocabulary.containsWid(Vocabulary.NULL_WID));
		assertFalse(vocabulary.containsWid(10));
		assertEquals(0, vocabulary.getWordCount());
		vocabulary.putWord("a b");
		assertEquals(1, vocabulary.getWordCount());
		assertTrue(vocabulary.containsWord("a b"));
		int wid = 1;
		assertEquals(wid, vocabulary.getWid("a b"));
		assertTrue(vocabulary.containsWid(wid));
		assertEquals("a b", vocabulary.getWord(wid));
	}
	
}
