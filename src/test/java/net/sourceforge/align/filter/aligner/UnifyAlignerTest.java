package net.sourceforge.align.filter.aligner;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static net.sourceforge.align.util.Util.createAlignmentList;

import java.util.List;

import net.sourceforge.align.coretypes.Alignment;

import org.junit.Test;

/**
 * Represents {@link UnifyAligner} unit test.
 * @author loomchild
 *
 */
public class UnifyAlignerTest {
	
	public static final String[][] REFERENCE_SOURCE_SEGMENT_ARRAY = {
		new String[] {"", ""},
		new String[] {"", "", ""},
		new String[] {""},
	};

	public static final String[][] REFERENCE_TARGET_SEGMENT_ARRAY = {
		new String[] {""},
		new String[] {""},
		new String[] {"", ""},
	};
	
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
		new String[] {"c", "d", "e"},
		new String[] {"f"},
	};

	public static final String[][] EXPECTED_TARGET_SEGMENT_ARRAY = {
		new String[] {"1"},
		new String[] {"2"},
		new String[] {"3", "4"},
	};

	/**
	 * Checks whether unify aligner works as expected using 
	 * {@link #REFERENCE_SOURCE_SEGMENT_ARRAY}, 
	 * {@link #REFERENCE_TARGET_SEGMENT_ARRAY}. 
	 */
	@Test
	public void testAlign() {
		List<Alignment> referenceAlignmentList = createAlignmentList(
				REFERENCE_SOURCE_SEGMENT_ARRAY, REFERENCE_TARGET_SEGMENT_ARRAY);
		UnifyAligner aligner = new UnifyAligner(referenceAlignmentList);
		List<Alignment> alignmentList = createAlignmentList(
				SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY);
		List<Alignment> resultAlignmentList = aligner.apply(alignmentList);
		assertAlignmentListEquals(EXPECTED_SOURCE_SEGMENT_ARRAY, 
				EXPECTED_TARGET_SEGMENT_ARRAY, resultAlignmentList);
	}
	
}

