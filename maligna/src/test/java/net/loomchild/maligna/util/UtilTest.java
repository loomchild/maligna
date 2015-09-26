package net.loomchild.maligna.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

import org.junit.Test;

/**
 * Represents utility methods test.
 * 
 * @author loomchild
 */
public class UtilTest {

	@Test
	public void alignManyToZero() {
		List<Alignment> alignmentList = Util.alignManyToZero(
				Arrays.asList(new String[]{"a", "b"}));
		assertEquals(1, alignmentList.size());
		Alignment alignment = alignmentList.get(0);
		TestUtil.assertAlignmentEquals(new String[]{"a", "b"},	new String[]{},
				alignment);
	}
	
	@Test
	public void alignZeroToMany() {
		List<Alignment> alignmentList = Util.alignZeroToMany(
				Arrays.asList(new String[]{"a", "b"}));
		assertEquals(1, alignmentList.size());
		Alignment alignment = alignmentList.get(0);
		TestUtil.assertAlignmentEquals(new String[]{},	new String[]{"a", "b"},
				alignment);
	}
	
	@Test
	public void alignManyToMany() {
		List<Alignment> alignmentList = Util.alignManyToMany(
				Arrays.asList(new String[]{"a", "b"}),
				Arrays.asList(new String[]{"c", "d", "e"}));
		assertEquals(1, alignmentList.size());
		Alignment alignment = alignmentList.get(0);
		TestUtil.assertAlignmentEquals(new String[]{"a","b"},
				new String[]{"c", "d", "e"}, alignment);
	}
 
	@Test
	public void assertAlignmentEquals() {
		Alignment alignment = new Alignment(
				Arrays.asList(new String[]{"a", "b"}), 
				Arrays.asList(new String[]{"c"}), 0.5f);
		TestUtil.assertAlignmentEquals(new String[]{"a", "b"},	new String[]{"c"},
				alignment);
		try {
			TestUtil.assertAlignmentEquals(new String[]{"a", "b"},	new String[]{"d"},
					alignment);
		} catch (AssertionError e) {
		}
	}

	public static final float[][] PROBABILITY_ARRAY = new float[][] {
		new float[] {0.1f, },
		new float[] {0.1f, 0.3f,},
		new float[] {0.1f, 0.3f, 0.2f, },
	};
		
	@Test
	public void testScoreSum() {
		List<Float> emptyScoreList = Collections.emptyList();
		assertEquals(0.0f, Util.scoreSum(emptyScoreList), 0.00001f);
		for (int i = 0; i < PROBABILITY_ARRAY.length; ++i) {
			float[] probabilityArray = PROBABILITY_ARRAY[i];
			float expectedProbability = sum(probabilityArray);
			float expectedScore = (float)Util.toScore(expectedProbability);
			List<Float> scoreList = toScore(probabilityArray);
			float actualScore = Util.scoreSum(scoreList);
			float actualProbability = (float)Util.toProbability(actualScore);
			assertEquals("Array " + i, expectedProbability, actualProbability, 
					0.00001f);
			assertEquals("Array " + i, expectedScore, actualScore, 0.00001f);
		}
	}
	
	private float sum(float[] array) {
		float sum = 0;
		for (float element : array) {
			sum += element;
		}
		return sum;
	}

	private List<Float> toScore(float[] probabilityArray) {
		List<Float> scoreList = new ArrayList<Float>(probabilityArray.length);
		for (float probability : probabilityArray) {
			float score = (float)Util.toScore(probability);
			scoreList.add(score);
		}
		return scoreList;
	}

}
