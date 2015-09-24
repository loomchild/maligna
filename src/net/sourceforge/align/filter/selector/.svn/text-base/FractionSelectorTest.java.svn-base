package net.sourceforge.align.filter.selector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;

import org.junit.Test;

/**
 * Represents {@link FractionSelector} unit test.
 * @author loomchild
 */
public class FractionSelectorTest {

	/**
	 * Test of selecting from empty list returns empty list and does not
	 * throw an exception.
	 */
	@Test
	public void testEmpty() {
		Filter filter = new FractionSelector(0.8f);
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		alignmentList = filter.apply(alignmentList);
		assertEquals(0, alignmentList.size());
	}
	
	/**
	 * Test if a selecting from a list containing one element returns empty
	 * list if fraction is < 0.5, and one element list if fraction >= 0.5. 
	 */
	@Test
	public void testSingleton() {
		Filter filter;
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		List<Alignment> filteredAlignmentList;
		Alignment alignment = new Alignment();
		alignment.setScore(1.0f);
		alignmentList.add(alignment);
		filter = new FractionSelector(0.4999f);
		filteredAlignmentList = filter.apply(alignmentList);
		assertEquals(0, filteredAlignmentList.size());
		filter = new FractionSelector(0.5f);
		filteredAlignmentList = filter.apply(alignmentList);
		assertEquals(1, filteredAlignmentList.size());
	}
	
	/**
	 * Test if the list contains elements with identical score,
	 * they will all be returned if fraction > 0.
	 */
	@Test
	public void testIdentical() {
		Filter filter = new FractionSelector(0.79f);
		List<Alignment> alignmentList = createAlignmentList(
				new float[]{2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
		alignmentList = filter.apply(alignmentList);
		assertEquals(5, alignmentList.size());
	}

	/**
	 * Tests if fraction selector selects correct number of elements.
	 */
	@Test
	public void testDifferent() {
		Filter filter = new FractionSelector(0.79f);
		List<Alignment> alignmentList = createAlignmentList(
				new float[]{1.0f, 4.0f, 2.0f, 5.0f, 2.0f});
		alignmentList = filter.apply(alignmentList);
		assertEquals(4, alignmentList.size());
	}	
	
	/**
	 * Helper function to create mock alignment list containing elements
	 * with given scores. 
	 * @param scoreArray
	 * @return
	 */
	private List<Alignment> createAlignmentList(float[] scoreArray) {
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		for (float score : scoreArray) {
			Alignment alignment = new Alignment();
			alignment.setScore(score);
			alignmentList.add(alignment);
		}
		return alignmentList;
	}
	
}
