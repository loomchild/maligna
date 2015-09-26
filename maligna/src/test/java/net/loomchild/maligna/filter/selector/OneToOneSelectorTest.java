package net.loomchild.maligna.filter.selector;

import static net.loomchild.maligna.util.TestUtil.assertAlignmentListEquals;
import static net.loomchild.maligna.util.TestUtil.createAlignmentList;
import static net.loomchild.maligna.util.TestUtil.filterSegmentArray;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;

import org.junit.Test;

/**
 * Represents {@link OneToOneSelector} unit test.
 * @author loomchild
 */
public class OneToOneSelectorTest {
	
	public static final String[][] SOURCE_ARRAY = new String[][] {
		new String[] {"aa", "bb"},
		new String[] {},
		new String[] {"cc"},
		new String[] {},
		new String[] {"dd"},
		new String[] {"ee", "ff"},
	};

	public static final String[][] TARGET_ARRAY = new String[][] {
		new String[] {"11"},
		new String[] {"22"},
		new String[] {"33"},
		new String[] {},
		new String[] {"44"},
		new String[] {"55", "66"},
	};
	
	public static final int[] RESULT_INDEXES = new int[] {
		2, 4
	};

	/**
	 * Checks if selector leaves only and all one to one alignments.
	 */
	@Test
	public void compare() {
		List<Alignment> alignmentList = createAlignmentList(
				SOURCE_ARRAY, TARGET_ARRAY);
		Filter filter = new OneToOneSelector();
		List<Alignment> resultAlignmentList = filter.apply(alignmentList);
		assertAlignmentListEquals(
				filterSegmentArray(SOURCE_ARRAY, RESULT_INDEXES),
				filterSegmentArray(TARGET_ARRAY, RESULT_INDEXES),
				resultAlignmentList);
	}
	
}
