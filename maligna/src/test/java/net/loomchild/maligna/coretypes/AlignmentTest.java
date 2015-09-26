package net.loomchild.maligna.coretypes;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Represents {@link Alignment} class test.
 * @author loomchild
 */
public class AlignmentTest {

	private List<String> sourceSegmentList;
	
	private List<String> targetSegmentList;
	
	@Before
	public void setUp() {
		sourceSegmentList = new ArrayList<String>();
		targetSegmentList = new ArrayList<String>();
	}
	
	/**
	 * Tests whether after calling the constructor lists stored in 
	 * {@link Alignment} are copies of the arguments.
	 */
	@Test
	public void contructorListCopying() {
		Alignment alignment = new Alignment(sourceSegmentList, 
				targetSegmentList, 2.0f);
		checkAlignment(alignment);
	}
	
	/**
	 * Tests whether after calling {@link Alignment#addSourceSegmentList(List)}
	 * and {@link Alignment#addTargetSegmentList(List)} methods, lists stored in 
	 * {@link Alignment} are copies of the arguments.
	 */
	@Test
	public void methodListCopying() {
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

}
