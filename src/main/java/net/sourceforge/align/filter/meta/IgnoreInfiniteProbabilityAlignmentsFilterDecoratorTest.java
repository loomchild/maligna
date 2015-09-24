package net.sourceforge.align.filter.meta;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static net.sourceforge.align.util.Util.createAlignmentList;

import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.Aligner;
import net.sourceforge.align.filter.aligner.align.onetoone.OneToOneAlgorithm;

import org.junit.Test;

/**
 * Represents {@link IgnoreInfiniteProbabilityAlignmentsFilterDecorator} 
 * unit test.
 * @author loomchild
 */
public class IgnoreInfiniteProbabilityAlignmentsFilterDecoratorTest {

	public static final String[][] SOURCE_SEGMENT_ARRAY = {
		new String[] {"A", "B"},
		new String[] {"X", "Y"},
		new String[] {"C", "D"},
	};

	public static final String[][] TARGET_SEGMENT_ARRAY = {
		new String[] {"1", "2"},
		new String[] {"9", "8"},
		new String[] {"3", "4"},
	};

	public static final String[][] EXPECTED_SOURCE_SEGMENT_ARRAY = {
		new String[] {"A"},
		new String[] {"B"},
		new String[] {"X", "Y"},
		new String[] {"C"},
		new String[] {"D"},
	};

	public static final String[][] EXPECTED_TARGET_SEGMENT_ARRAY = {
		new String[] {"1"},
		new String[] {"2"},
		new String[] {"9", "8"},
		new String[] {"3"},
		new String[] {"4"},
	};
	
	/**
	 * Tests whether alignments with infinite score are ignored and
	 * the ones without are not by using {@link OneToOneAlgorithm} aligner.
	 */
	@Test
	public void testIgnoreInfiniteProbability() {
		Filter oneToOneAligner = new Aligner(new OneToOneAlgorithm());
		Filter filter = 
			new IgnoreInfiniteProbabilityAlignmentsFilterDecorator(oneToOneAligner);

		List<Alignment> alignmentList = createAlignmentList(
				SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY);
		
		// Mark middle alignment as fixed.
		alignmentList.get(1).setScore(Float.NEGATIVE_INFINITY);
		
		List<Alignment> resultAlignmentList = filter.apply(alignmentList);
		assertAlignmentListEquals(EXPECTED_SOURCE_SEGMENT_ARRAY, 
				EXPECTED_TARGET_SEGMENT_ARRAY, resultAlignmentList);
	}
	
}
