package net.sourceforge.align.model.translation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Represents {@link TargetDataProbabilityComparator} unit test.
 * @author loomchild
 */
public class TargetDataProbabilityComparatorTest {

	/**
	 * Simple comparator test.
	 */
	@Test
	public void testCompareTo() {
		TargetData[] data = new TargetData[] {
				new TargetData(0, 0.1), new TargetData(1, 0.5), 
				new TargetData(2, 0.5)
		};		
		TargetDataProbabilityComparator comparator = 
			new TargetDataProbabilityComparator();
		assertTrue(comparator.compare(data[0], data[1]) > 0);
		assertTrue(comparator.compare(data[1], data[0]) < 0);
		assertTrue(comparator.compare(data[0], data[0]) == 0);
		assertTrue(comparator.compare(data[1], data[2]) == 0);
	}
	
}
