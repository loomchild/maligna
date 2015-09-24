package net.sourceforge.align.filter.meta;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static net.sourceforge.align.util.Util.createAlignmentList;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.Aligner;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithmMock;
import net.sourceforge.align.filter.modifier.Modifier;
import net.sourceforge.align.filter.modifier.modify.merge.MergeAlgorithm;
import net.sourceforge.align.filter.modifier.modify.merge.SeparatorMergeAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithmMock;

import org.junit.Test;

/**
 * Represents {@link CompositeFilter} unit test.
 * @author loomchild
 */
public class CompositeFilterTest {

	public static final String[][] SOURCE_SEGMENT_ARRAY = {
		new String[] {"abcdef"}
	};

	public static final String[][] TARGET_SEGMENT_ARRAY = {
		new String[] {"12345"}
	};

	public static final String[][] EXPECTED_SOURCE_SEGMENT_ARRAY = {
		new String[] {"ab"},
		new String[] {"cd"},
		new String[] {"ef"},
	};

	public static final String[][] EXPECTED_TARGET_SEGMENT_ARRAY = {
		new String[] {"12"},
		new String[] {"34"},
		new String[] {"5"},
	};
	
	/**
	 * Creates a composite filter consisting of 
	 * {@link SplitAlgorithmMock} filter, {@link AlignAlgorithmMock} filter and
	 * {@link SeparatorMergeAlgorithm}, applies the filter and checks 
	 * if the results are correct.
	 */
	@Test
	public void testRunAllFilters() {
		SplitAlgorithm splitAlgorithm = new SplitAlgorithmMock(1);
		AlignAlgorithm alignAlgorithm = new AlignAlgorithmMock(2);
		MergeAlgorithm mergeAlgorithm = new SeparatorMergeAlgorithm();
		List<Filter> filterList = new ArrayList<Filter>();
		filterList.add(new Modifier(splitAlgorithm, splitAlgorithm));
		filterList.add(new Aligner(alignAlgorithm));
		filterList.add(new Modifier(mergeAlgorithm, mergeAlgorithm));
		CompositeFilter composite = new CompositeFilter(filterList);
		List<Alignment> alignmentList = createAlignmentList(
				SOURCE_SEGMENT_ARRAY, TARGET_SEGMENT_ARRAY);
		List<Alignment> resultAlignmentList = composite.apply(alignmentList);
		assertAlignmentListEquals(EXPECTED_SOURCE_SEGMENT_ARRAY, 
				EXPECTED_TARGET_SEGMENT_ARRAY, resultAlignmentList);
	}
	
}
