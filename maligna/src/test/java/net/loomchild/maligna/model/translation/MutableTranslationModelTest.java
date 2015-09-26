package net.loomchild.maligna.model.translation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Represents {@link MutableTranslationModel} unit test.
 * @author loomchild
 */
public class MutableTranslationModelTest {
	
	/**
	 * Checks whether {@link MutableTranslationModel#normalize()} and 
	 * {@link MutableTranslationModel#sort()} work as expected.
	 */
	@Test
	public void putNormalizeSort() {
		MutableTranslationModel model = new MutableTranslationModel();
		
		assertEquals(0, model.get(0).getTranslationList().size());

		model.getMutable(1).setTranslationProbability(0, 0.5);
		model.getMutable(1).setTranslationProbability(1, 1.5);
	
		assertEquals(0.5, model.get(1).getTranslationProbability(0), 0.00001);
		assertEquals(1.5, model.get(1).getTranslationProbability(1), 0.00001);
		model.normalize();
		assertEquals(0.25, model.get(1).getTranslationProbability(0), 0.00001);
		assertEquals(0.75, model.get(1).getTranslationProbability(1), 0.00001);

		assertEquals(0, model.get(1).getTranslationList().get(0).getWid());
		model.sort();
		assertEquals(1, model.get(1).getTranslationList().get(0).getWid());
	}
	
}
