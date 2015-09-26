package net.loomchild.maligna.model.length;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Represents {@link MutableLengthModel} unit test.
 * @author loomchild
 */
public class MutableLengthModelTest {

	/**
	 * Checks if length probabilities are calculated correctly.
	 */
	@Test
	public void length() {
		MutableLengthModel model = new MutableLengthModel();
		assertEquals(0.0f, model.getLengthProbability(3), 0.01f);
		model.addLengthOccurence(3);
		model.addLengthOccurence(3);
		model.addLengthOccurence(3);
		model.addLengthOccurence(2);
		model.normalize();
		assertEquals(0.75f, model.getLengthProbability(3), 0.01f);
		assertEquals(0.25f, model.getLengthProbability(2), 0.01f);
		assertEquals(2.75f, model.getMeanLength(), 0.01f);
	}
	

}
