package net.sourceforge.align.model.language;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Represents {@link MutableLanguageModel} unit test.
 * @author loomchild
 */
public class MutableLanguageModelTest {

	/**
	 * Checks if model probabilities are calculated properly.
	 */
	@Test
	public void word() {
		MutableLanguageModel model = new MutableLanguageModel();
		assertEquals(0.0f, model.getWordProbability(1));
		model.addWordOccurence(1);
		model.addWordOccurence(1);
		model.addWordOccurence(1);
		model.addWordOccurence(2);
		model.normalize();
		assertEquals(0.75f, model.getWordProbability(1), 0.01f);
		assertEquals(0.25f, model.getWordProbability(2), 0.01f);
		assertEquals(0.25f, model.getSingletonWordProbability(), 0.01f);
	}
	
}
