package net.sourceforge.align.model.translation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * Represents {@link MutableSourceData} unit test.
 * @author loomchild
 */
public class MutableSourceDataTest {

	/**
	 * Check if {@link MutableSourceData#normalize()} and 
	 * {@link MutableSourceData#sort()} work as expected.
	 */
	@Test
	public void getPutSortNormalize() {
		MutableSourceData data = new MutableSourceData();
		assertEquals(0, data.getTranslationList().size());
		assertEquals(0, data.getTranslationProbability(0), 0.000001);
		data.setTranslationProbability(0, 0.6f);
		assertEquals(0.6, data.getTranslationProbability(0), 0.000001);
		data.setTranslationProbability(1, 1.0f);
		assertEquals(1.0, data.getTranslationProbability(1), 0.000001);
		data.setTranslationProbability(2, 0.4f);
		assertEquals(0.4, data.getTranslationProbability(2), 0.000001);
		data.normalize();
		data.sort();
		List<TargetData> targetList = data.getTranslationList();
		assertEquals(3, targetList.size());
		TargetData target = targetList.get(0);
		assertEquals(0.5, target.getProbability(), 0.000001);
		assertEquals(1, target.getWid());
		target = targetList.get(1);
		assertEquals(0.3, target.getProbability(), 0.000001);
		assertEquals(0, target.getWid());
		target = targetList.get(2);
		assertEquals(0.2, target.getProbability(), 0.000001);
		assertEquals(2, target.getWid());
	}
	
}
