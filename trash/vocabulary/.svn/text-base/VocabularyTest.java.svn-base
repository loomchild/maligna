package align.aligner.algorithms.moore.translation;

import junit.framework.TestCase;

public class VocabularyTest extends TestCase {

	public void testVocabulary() {
		Vocabulary vocabulary = new Vocabulary();
		assertFalse(vocabulary.containsWord("a b"));
		try {
			vocabulary.getWid("a b");
			fail("Znalazłem nieistniejące słowo");
		} catch (WordNotFoundException e) {
			//Spodziewane
		}
		assertFalse(vocabulary.containsWid(0));
		try {
			vocabulary.getWord(0);
			fail("Znalazłem nieistniejące id");
		} catch (WordIdNotFoundException e) {
			//Spodziewane
		}
		vocabulary.put("a b");
		assertTrue(vocabulary.containsWord("a b"));
		int wid = vocabulary.getWid("a b");
		assertTrue(vocabulary.containsWid(wid));
		assertEquals("a b", vocabulary.getWord(wid));
	}
	
}
