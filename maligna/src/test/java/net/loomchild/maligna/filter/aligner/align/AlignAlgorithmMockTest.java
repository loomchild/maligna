package net.loomchild.maligna.filter.aligner.align;

import static net.loomchild.maligna.util.TestUtil.assertAlignmentEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

import org.junit.Test;

/**
 * Represents {@link AlignAlgorithmMock} unit test.
 * @author loomchild
 */
public class AlignAlgorithmMockTest {

	/**
	 * Checks if aligning empty lists returns empty list.
	 */
	@Test
	public void alignEmpty() {
		AlignAlgorithm aligner = new AlignAlgorithmMock(2);
		List<String> segmentList = Collections.emptyList();
		List<Alignment> alignmentList = aligner.align(segmentList, segmentList);
		assertEquals(0, alignmentList.size());
	}

	/**
	 * Checks whether mock aligner works as described.
	 */
	@Test
	public void align() {
		AlignAlgorithm aligner = new AlignAlgorithmMock(2);
		String[][] sourceArray = new String[][]{
				new String[]{"a", "b"}, new String[]{"c","d"}, 
				new String[]{"e", "f"}				
		};
		String[][] targetArray = new String[][]{
				new String[]{"1", "2"}, new String[]{"3"}, new String[]{} 
		};
		assert sourceArray.length == targetArray.length;
		int alignmentCount = sourceArray.length;
		List<String> sourceList = combine(sourceArray);
		List<String> targetList = combine(targetArray);
		List<Alignment> alignmentList = aligner.align(sourceList, targetList);
		assertEquals(alignmentCount, alignmentList.size());
		for (int i = 0; i < alignmentCount; ++i) {
			assertAlignmentEquals(sourceArray[i], targetArray[i], 
					alignmentList.get(i));
		}
	}
	
	/**
	 * Creates a list of strings containing all strings from input 
	 * two dimensional array.
	 * @param array array
	 * @return list
	 */
	private List<String> combine(String[][] array) {
		List<String> list = new ArrayList<String>();
		for (String[] group : array) {
			for (String element : group) {
				list.add(element);
			}
		}
		return list;
	}
	
}
