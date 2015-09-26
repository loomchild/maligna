package net.loomchild.maligna.model.length;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


/**
 * Represents {@link LengthModelUtil} test suite.
 * @author loomchild
 *
 */
public class LengthModelUtilTest {

	/**
	 * Checks if length probabilities are calculated correctly.
	 */
	@Test
	public void train() {
		List<Integer> lengthList = Arrays.asList(new Integer[]{3, 1, 1, 0});
		LengthModel model = LengthModelUtil.train(lengthList);
		assertEquals(0.25f, model.getLengthProbability(0), 0.01f);
		assertEquals(0.5f, model.getLengthProbability(1), 0.01f);
		assertEquals(0.0f, model.getLengthProbability(2), 0.01f);
		assertEquals(0.25f, model.getLengthProbability(3), 0.01f);
		assertEquals(1.25f, model.getMeanLength(), 0.01f);
	}
	
}
