package net.sourceforge.align.filter.modifier.modify.merge;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * Represents {@link SeparatorMergeAlgorithm} unit test.
 * @author loomchild
 */
public class SeparatorMergeAlgorithmTest {

	private MergeAlgorithm merger;
	
	@Before
	public void setUp() {
		merger = new SeparatorMergeAlgorithm("  ");
	}
	
	/**
	 * Check if merging empty list returns empty segment.
	 */
	@Test
	public void mergeEmpty() {
		List<String> list = Collections.emptyList();
		String segment = merger.merge(list);
		assertEquals("", segment);
	}
	
	/**
	 * Checks if merging a list containing just one segment
	 * returns the same segment.
	 */
	@Test
	public void mergeSingleton() {
		List<String> list = Collections.singletonList("ala");
		String segment = merger.merge(list);
		assertEquals("ala", segment);
	}
	
	/**
	 * Test merging with separator.
	 */
	@Test
	public void merge() {
		List<String> list = Arrays.asList(new String[] {"ala", "ma", " kota"});
		String segment = merger.merge(list);
		assertEquals("ala  ma   kota", segment);
	}

	/**
	 * Tests merging without a separator - if the result will be 
	 * exactly the same as string contatenation.
	 */
	@Test
	public void mergeNoSeparator() {
		MergeAlgorithm emptyMerger = new SeparatorMergeAlgorithm("");
		List<String> list = Arrays.asList(new String[] {"ala", "ma", " kota"});
		String segment = emptyMerger.merge(list);
		assertEquals("alama kota", segment);
	}

	
}
