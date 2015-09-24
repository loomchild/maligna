package net.sourceforge.align.filter.modifier.modify.split;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.align.filter.modifier.modify.merge.MergeAlgorithm;
import net.sourceforge.align.filter.modifier.modify.merge.SeparatorMergeAlgorithm;

import org.junit.Test;


/**
 * Tests {@link SplitAlgorithmMock}. This is a little paranoid because mock is
 * used for testing itself.
 * @author loomchild
 */
public class SplitAlgorithmMockTest {

	@Test
	public void split() {
		String[] segments = new String[] {"aa", "bb", "c"};
		MergeAlgorithm merger = new SeparatorMergeAlgorithm("");
		String text = merger.merge(Arrays.asList(segments));
		SplitAlgorithm splitter = new SplitAlgorithmMock(2);
		List<String> splitted = splitter.split(text);
		String[] splittedArray = splitted.toArray(new String[splitted.size()]);
		assertEquals(segments, splittedArray);
	}
	
}
