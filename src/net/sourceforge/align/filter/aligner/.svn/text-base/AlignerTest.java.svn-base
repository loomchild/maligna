package net.sourceforge.align.filter.aligner;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static net.sourceforge.align.util.Util.createAlignmentList;

import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithmMock;

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
		List<Alignment> alignmentList = createAlignmentList(
				SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY);
		List<Alignment> resultAlignmentList = aligner.apply(alignmentList);
		assertAlignmentListEquals(EXPECTED_SOURCE_SEGMENT_ARRAY, 
				EXPECTED_TARGET_SEGMENT_ARRAY, resultAlignmentList);
	}
	
}

