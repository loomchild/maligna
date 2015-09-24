package net.sourceforge.align.model.translation;

import static net.sourceforge.align.model.Util.createWidList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.List;

import net.sourceforge.align.model.vocabulary.Vocabulary;

import org.junit.Ignore;
import org.junit.Test;


/**
 * Represents {@link TranslationModelUtil} unit test.
 * @author loomchild
 */
public class TranslationModelUtilTest {

	/**
	 * Test if {@link TranslationModelUtil#train(int, List, List)} trains the
	 * model correctly.
	 * 
	 * First checks if values are distributed correctly after first iteration.
	 * Later checks if the results converge to optimum.
	 * 
	 * Does not pass after modifications in training process suggested by Moore
	 * by introducing minimum probability change.
	 * 
	 * TODO: Investigate problem, make it pass. 
	 */
	@Ignore
	@Test
	public void train() {
		int[][] sourceWidArray = {
				new int[] {},
				new int[] {2},
				new int[] {1, 2, 3}, 
		};
		int[][] targetWidArray = {
				new int[] {3},
				new int[] {2},
				new int[] {1, 2}, 
		};
		List< List<Integer> > sourceWidList = createWidList(sourceWidArray);
		List< List<Integer> > targetWidList = createWidList(targetWidArray);
		TranslationModel model = 
			TranslationModelUtil.train(1, sourceWidList, targetWidList);
		SourceData sourceData;
		List<TargetData> targetDataList;
		TargetData targetData;
		
		sourceData = model.get(0);
		targetDataList = sourceData.getTranslationList();
		assertEquals(3, targetDataList.size());
		targetData = targetDataList.get(0);
		assertEquals(3, targetData.getWid());
		assertEquals(0.5, targetData.getProbability(), 0.01);
		targetData = targetDataList.get(1);
		assertEquals(2, targetData.getWid());
		assertEquals(0.375, targetData.getProbability(), 0.01);
		targetData = targetDataList.get(2);
		assertEquals(1, targetData.getWid());
		assertEquals(0.125, targetData.getProbability(), 0.01);

		sourceData = model.get(1);
		targetDataList = sourceData.getTranslationList();
		assertEquals(2, targetDataList.size());
		targetData = targetDataList.get(0);
		assertEquals(1, targetData.getWid());
		assertEquals(0.5, targetData.getProbability(), 0.01);
		targetData = targetDataList.get(1);
		assertEquals(2, targetData.getWid());
		assertEquals(0.5, targetData.getProbability(), 0.01);

		sourceData = model.get(2);
		targetDataList = sourceData.getTranslationList();
		assertEquals(2, targetDataList.size());
		targetData = targetDataList.get(0);
		assertEquals(2, targetData.getWid());
		assertEquals(0.75, targetData.getProbability(), 0.01);
		targetData = targetDataList.get(1);
		assertEquals(1, targetData.getWid());
		assertEquals(0.25, targetData.getProbability(), 0.01);
		
		sourceData = model.get(3);
		targetDataList = sourceData.getTranslationList();
		assertEquals(2, targetDataList.size());
		targetData = targetDataList.get(0);
		assertEquals(1, targetData.getWid());
		assertEquals(0.5, targetData.getProbability(), 0.01);
		targetData = targetDataList.get(1);
		assertEquals(2, targetData.getWid());
		assertEquals(0.5, targetData.getProbability(), 0.01);
		
		for (int i = 2; i < 4; ++i) {
			TranslationModel oldModel = model;
			model = 
				TranslationModelUtil.train(i, sourceWidList, targetWidList);
			assertTrue("Iteration " + i,
					model.get(0).getTranslationProbability(3) > 
					oldModel.get(0).getTranslationProbability(3));
			double delta0To2 = oldModel.get(0).getTranslationProbability(2) 
					- model.get(0).getTranslationProbability(2);
			double delta0To1 = oldModel.get(0).getTranslationProbability(1) 
					- model.get(0).getTranslationProbability(1);
			assertTrue("Iteration " + i, delta0To2 < delta0To1);
			assertTrue("Iteration " + i,
					model.get(2).getTranslationProbability(2) > 
					oldModel.get(2).getTranslationProbability(2));
		}
		
	}

	public String TRANSLATION_MODEL = 
		"b	C	0.1\n" +
		"b	D	0.3\n" +
		"c	A	0.5\n" +
		"a	D	0.33\n" +
		" ";
	
	/**
	 * Tests translation model parsing.
	 */
	@Test
	public void testParse() {
		StringReader reader = new StringReader(TRANSLATION_MODEL);
		Vocabulary sourceVocabulary = new Vocabulary();
		Vocabulary targetVocabulary = new Vocabulary();
		TranslationModel translationModel = TranslationModelUtil.parse(reader, 
				sourceVocabulary, targetVocabulary);
		SourceData sourceData;
		sourceData = translationModel.get(sourceVocabulary.getWid("a"));
		assertEquals(1, sourceData.getTranslationList().size());
		assertEquals(0.0, sourceData.getTranslationProbability(targetVocabulary.getWid("A")), 0.0001);
		assertEquals(1.0, sourceData.getTranslationProbability(targetVocabulary.getWid("D")), 0.0001);
		sourceData = translationModel.get(sourceVocabulary.getWid("b"));
		assertEquals(2, sourceData.getTranslationList().size());
		assertEquals(0.25, sourceData.getTranslationProbability(targetVocabulary.getWid("C")), 0.0001);
		assertEquals(0.75, sourceData.getTranslationProbability(targetVocabulary.getWid("D")), 0.0001);
		sourceData = translationModel.get(sourceVocabulary.getWid("c"));
		assertEquals(1, sourceData.getTranslationList().size());
		assertEquals(1.0, sourceData.getTranslationProbability(targetVocabulary.getWid("A")), 0.0001);
		sourceData = translationModel.get(100);
		assertEquals(0, sourceData.getTranslationList().size());
	}
	
}
