package net.loomchild.maligna.filter.aligner;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithmMock;
import net.loomchild.maligna.util.TestUtil;

import org.junit.Test;

/**
 * Represents unit test of {@link Aligner} class. Checks if it applies 
 * {@link AlignAlgorithm} correctly using {@link AlignAlgorithmMock}.
 * @author loomchild
 */
public class AlignerTest {
	
	public static final String[][] SOURCE_SEGMENT_ARRAY = {
		new String[] {"a", "b", "c", "d"},
		new String[] {"e", "f"}
	};

	public static final String[][] TARGET_SEGMENT_ARRAY = {
		new String[] {"1", "2", "3"},
		new String[] {"4"}
	};

	public static final String[][] EXPECTED_SOURCE_SEGMENT_ARRAY = {
		new String[] {"a", "b"},
		new String[] {"c", "d"},
		new String[] {"e", "f"},
	};

	public static final String[][] EXPECTED_TARGET_SEGMENT_ARRAY = {
		new String[] {"1", "2"},
		new String[] {"3"},
		new String[] {"4"},
	};

	/**
	 * Checks if {@link Aligner} uses {@link AlignAlgorithm} correctly.
	 */
	@Test
	public void testAlign() {
		AlignAlgorithm algorithm = new AlignAlgorithmMock(2);
		Aligner aligner = new Aligner(algorithm);
		List<Alignment> alignmentList = TestUtil.createAlignmentList(
				SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY);
		List<Alignment> resultAlignmentList = aligner.apply(alignmentList);
		TestUtil.assertAlignmentListEquals(EXPECTED_SOURCE_SEGMENT_ARRAY,
				EXPECTED_TARGET_SEGMENT_ARRAY, resultAlignmentList);
	}
	
}

