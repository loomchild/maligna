package align.comparator;

import static loomchild.util.testing.Utils.assertListEquals;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import align.Alignment;

public class SequentialComparatorTest extends TestCase {

	public void testCompare() {
		Alignment[] leftAlignmentArray = new Alignment[] {
				createAlignment(new String[] {"aa", "bb"}, new String[] {"11"}),	
				createAlignment(new String[] {}, new String[] {"22"}),	
				createAlignment(new String[] {"cc"}, new String[] {"33"}),	
				createAlignment(new String[] {"dd", "ee"}, new String[] {"44"}),	
				createAlignment(new String[] {}, new String[] {}),	
				createAlignment(new String[] {}, new String[] {"55"}),	
				createAlignment(new String[] {"ff"}, new String[] {"66"}),	
				createAlignment(new String[] {"gg"}, new String[] {"77"}),	
				createAlignment(new String[] {"hh"}, new String[] {"88"}),	
				createAlignment(new String[] {"ii"}, new String[] {"99"}),	
				createAlignment(new String[] {"jj"}, new String[] {}),	
				};
		List<Alignment> leftAlignmentList = Arrays.asList(leftAlignmentArray);
		Alignment[] rightAlignmentArray = new Alignment[] {
				createAlignment(new String[] {"aa", "bb"}, new String[] {"11", "22"}),	
				createAlignment(new String[] {"cc"}, new String[] {"33"}),	
				createAlignment(new String[] {"dd", "ee"}, new String[] {"44"}),	
				createAlignment(new String[] {}, new String[] {}),	
				createAlignment(new String[] {}, new String[] {"55"}),	
				createAlignment(new String[] {"ff gg"}, new String[] {"66 77"}),	
				createAlignment(new String[] {"hh"}, new String[] {"88"}),	
				createAlignment(new String[] {"ii"}, new String[] {"00"}),	
				};
		List<Alignment> rightAlignmentList = Arrays.asList(rightAlignmentArray);
		Comparator comparator = new SequentialComparator();
		CompareResult result = comparator.compare(leftAlignmentList, 
				rightAlignmentList);
		assertListEquals(new Alignment[] {
				leftAlignmentArray[0], leftAlignmentArray[1], 
				leftAlignmentArray[6], leftAlignmentArray[7], 
				leftAlignmentArray[9], leftAlignmentArray[10]}, 
				result.getLeftOnlyAlignmentList());
		assertListEquals(new Alignment[] {
				rightAlignmentArray[0], rightAlignmentArray[5],
				rightAlignmentArray[7]}, 
				result.getRightOnlyAlignmentList());
	}
	
	private Alignment createAlignment(String[] sourceSegmentArray, 
			String[] targetSegmentArray) {
		return new Alignment(Arrays.asList(sourceSegmentArray), 
				Arrays.asList(targetSegmentArray), 0.0f);
	}
	
}
