package net.sourceforge.align.model.language;

import static net.sourceforge.align.model.Util.createWidList;
import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

/**
 * Represents {@link LanguageModelUtil} unit test.
 * @author loomchild
 */
public class LanguageModelUtilTest {

	public String LANGUAGE_MODEL = 
		"3	2\n" +
		"1	3\n" +
		"2	0\n" +
		" ";
	
	/**
	 * Tests if model training produces expected probabilities.
	 */
	@Test
	public void train() {
		int[][] widArray = new int[][] {
			new int[] {1, 2, 1}, new int[] {1}, new int[] {2}, new int[] {},
		};
		List< List<Integer> > widList = createWidList(widArray);
		LanguageModel model = LanguageModelUtil.train(widList);
		assertEquals(0.6f, model.getWordProbability(1), 0.01f);
		assertEquals(0.4f, model.getWordProbability(2), 0.01f);
		assertEquals(0.0f, model.getWordProbability(0), 0.01f);
		assertEquals(0.2f, model.getSingletonWordProbability());
	}
	
	/**
	 * Tests if parsing of simple test model {@link #LANGUAGE_MODEL} 
	 * works as expected. 
	 */
	@Test
	public void testParse() {
		StringReader reader = new StringReader(LANGUAGE_MODEL);
		LanguageModel languageModel = LanguageModelUtil.parse(reader);
		assertEquals(0.2f, languageModel.getSingletonWordProbability(), 0.0001f);
		assertEquals(0.6f, languageModel.getWordProbability(1), 0.0001f);
		assertEquals(0.0f, languageModel.getWordProbability(2), 0.0001f);
	}
	
}
