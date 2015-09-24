package net.sourceforge.align.filter.aligner.align.onetoone;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.aligner.AlignmentImpossibleException;

import org.junit.Test;

/**
 * Represents {@link OneToOneAlgorithm} unit test.
 *
 * @author Jarek Lipski (loomchild)
 */
public class OneToOneAlgorithmTest {
		
	public static final String[] SOURCE_ARRAY = new String[] {
		"a", "b", "c"
	};

	public static final String[] TARGET_ARRAY = new String[] {
		"1", "2", "3"
	};

	public static final String[][] RESULT_SOURCE_ARRAY = new String[][] {
		new String[]{"a"},
		new String[]{"b"},
		new String[]{"c"},
	};

	public static final String[][] RESULT_TARGET_ARRAY = new String[][] {
		new String[]{"1"},
		new String[]{"2"},
		new String[]{"3"},
	};

	/**
	 * Test the case when there is equal number of source and target segments.
	 */
	@Test
	public void alignEqualSize() {
		OneToOneAlgorithm algorithm = new OneToOneAlgorithm(false);
		List<Alignment> alignmentList = algorithm.align(
				Arrays.asList(SOURCE_ARRAY), Arrays.asList(TARGET_ARRAY));
		assertAlignmentListEquals(RESULT_SOURCE_ARRAY, RESULT_TARGET_ARRAY, 
				alignmentList);
	}
	
	public static final String[] NE_SOURCE_ARRAY = new String[] {
		"a", "b", "c"
	};

	public static final String[] NE_TARGET_ARRAY = new String[] {
		"1"
	};

	public static final String[][] NE_RESULT_SOURCE_ARRAY = new String[][] {
		new String[]{"a"},
		new String[]{"b"},
		new String[]{"c"},
	};

	public static final String[][] NE_RESULT_TARGET_ARRAY = new String[][] {
		new String[]{"1"},
		new String[]{},
		new String[]{},
	};

	/**
	 * Checks the case where source and target segment counts are unequal,
	 * but the aligner is not in strict mode.
	 */
	@Test
	public void alignNotEqualSize() {
		OneToOneAlgorithm algorithm = new OneToOneAlgorithm(false);
		List<Alignment> alignmentList = algorithm.align(
				Arrays.asList(NE_SOURCE_ARRAY), Arrays.asList(NE_TARGET_ARRAY));
		assertAlignmentListEquals(NE_RESULT_SOURCE_ARRAY, 
				NE_RESULT_TARGET_ARRAY, alignmentList);
	}

	/**
	 * Checks whether exception will be thrown when source and target segment 
	 * counts are unequal and the aligner is in strict mode.
	 */
	@Test(expected=AlignmentImpossibleException.class)
	public void alignNotEqualSizeStrict() {
		OneToOneAlgorithm algorithm = new OneToOneAlgorithm(true);
		algorithm.align(Arrays.asList(NE_SOURCE_ARRAY), 
				Arrays.asList(NE_TARGET_ARRAY));
	}

	/**
	 * Checks if alignment of empty source and target segment lists will return 
	 * empty list.
	 */
	@Test
	public void alignEmpty() {
		OneToOneAlgorithm algorithm = new OneToOneAlgorithm(true);
		List<String> sourceSegmentList = Arrays.asList(new String[]{});
		List<String> targetSegmentList = Arrays.asList(new String[]{});
		List<Alignment> alignmentList = 
				algorithm.align(sourceSegmentList, targetSegmentList);
		assertEquals(0, alignmentList.size());
	}
	
}
