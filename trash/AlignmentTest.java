package align;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class AlignmentTest extends TestCase {

	private List<String> sourceSegmentList;
	
	private List<String> targetSegmentList;
	
	public void setUp() {
		sourceSegmentList = new LinkedList<String>();
		targetSegmentList = new LinkedList<String>();
	}
	
	/**
	 * Testuje czy po utworzeniu dopasowania listy są niezależne od argumentów.
	 */
	public void testContructorListCopying() {
		Alignment alignment = new Alignment(sourceSegmentList, 
				targetSegmentList, 2.0f);
		checkAlignment(alignment);
	}
	
	/**
	 * Testuje czy po dodaniu list do dopasowania są one niezależne od 
	 * argumentów.
	 */
	public void testMethodListCopying() {
		Alignment alignment = new Alignment();
		alignment.addSourceSegmentList(sourceSegmentList);
		alignment.addTargetSegmentList(targetSegmentList);
		alignment.setScore(2.0f);
		checkAlignment(alignment);
	}

	private void checkAlignment(Alignment alignment) {
		assertEquals(0, alignment.getSourceSegmentList().size());
		sourceSegmentList.add("a");
		assertEquals(0, alignment.getSourceSegmentList().size());
		assertEquals(0, alignment.getTargetSegmentList().size());
		targetSegmentList.add("c");
		assertEquals(0, alignment.getTargetSegmentList().size());
		assertEquals(2.0f, alignment.getScore(), 0.000000001f);
	}

	public void testMerge() {
		Alignment alignment = new Alignment();
		alignment.addSourceSegmentList(
				Arrays.asList(new String[]{"aa", "", "bbb"}));
		alignment.addTargetSegmentList(Arrays.asList(new String[]{}));
		String merged = alignment.getMergedSourceSegment(); 
		assertEquals("aabbb", merged);
		merged = alignment.getMergedTargetSegment(); 
		assertEquals("", merged);
	}
	

}
