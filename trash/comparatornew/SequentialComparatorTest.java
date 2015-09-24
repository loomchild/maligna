package net.sourceforge.align.comparator;

import static net.sourceforge.align.util.Util.assertAlignmentListEquals;
import static net.sourceforge.align.util.Util.createAlignmentList;
import static net.sourceforge.align.util.Util.filterSegmentArray;

import java.util.List;

import net.sourceforge.align.Alignment;

import org.junit.Test;

public class SequentialComparatorTest {
	
	public static final String[][] LEFT_SOURCE_ARRAY = new String[][] {
		new String[] {"aa", "bb"},
		new String[] {},
		new String[] {"cc"},
		new String[] {"dd", "ee"},
		new String[] {},
		new String[] {},
		new String[] {"ff"},
		new String[] {"gg"},
		new String[] {"hh"},
		new String[] {"ii"},
		new String[] {},
	};

	public static final String[][] LEFT_TARGET_ARRAY = new String[][] {
		new String[] {"11"},
		new String[] {"22"},
		new String[] {"33"},
		new String[] {"44"},
		new String[] {},
		new String[] {"55"},
		new String[] {"66"},
		new String[] {"77"},
		new String[] {"88"},
		new String[] {},
		new String[] {"99"},
	};
	
	public static final String[][] RIGHT_SOURCE_ARRAY = new String[][] {
		new String[] {"aa", "bb"},
		new String[] {"cc"},
		new String[] {},
		new String[] {},
		new String[] {"ff", "gg"},
		new String[] {"hh"},
		new String[] {"ii"},
	};
	
	public static final String[][] RIGHT_TARGET_ARRAY = new String[][] {
		new String[] {"11", "22"},
		new String[] {"33"},
		new String[] {},
		new String[] {"55"},
		new String[] {"66", "77"},
		new String[] {"88"},
		new String[] {"99"},
	};

	public static final int[] LEFT_ONLY_INDEXES = new int[] {
		0, 1, 3, 6, 7, 9, 10,
	};

	public static final int[] RIGHT_ONLY_INDEXES = new int[] {
		0, 4, 6,
	};
	
	@Test
	public void compare() {
		List<Alignment> leftAlignmentList = createAlignmentList(
				LEFT_SOURCE_ARRAY, LEFT_TARGET_ARRAY);
		List<Alignment> rightAlignmentList = createAlignmentList(
				RIGHT_SOURCE_ARRAY, RIGHT_TARGET_ARRAY);
		Comparator comparator = new SequentialComparator();
		Diff result = comparator.compare(leftAlignmentList, 
				rightAlignmentList);
		assertAlignmentListEquals(
				filterSegmentArray(LEFT_SOURCE_ARRAY, LEFT_ONLY_INDEXES),
				filterSegmentArray(LEFT_TARGET_ARRAY, LEFT_ONLY_INDEXES),
				result.getLeftOnlyAlignmentList());
		assertAlignmentListEquals(
				filterSegmentArray(RIGHT_SOURCE_ARRAY, RIGHT_ONLY_INDEXES),
				filterSegmentArray(RIGHT_TARGET_ARRAY, RIGHT_ONLY_INDEXES),
				result.getRightOnlyAlignmentList());
	}
		
}
