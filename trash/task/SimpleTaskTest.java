package align.task;

import static loomchild.util.testing.Utils.assertListEquals;

import java.util.List;

import junit.framework.TestCase;
import align.aligner.Aligner;
import align.aligner.AlignerMock;
import align.aligner.AlignmentImpossibleException;
import align.data.Alignment;
import align.splitter.Splitter;
import align.splitter.SplitterMock;

public class SimpleTaskTest extends TestCase {
	
	public void testUseDifferentSplitters() throws AlignmentImpossibleException {
		Splitter sourceSplitter = new SplitterMock(1);
		Splitter targetSplitter = new SplitterMock(2);
		Aligner aligner = new AlignerMock();		
		SimpleTask task = 
			new SimpleTask(sourceSplitter, targetSplitter, aligner);
		List<Alignment> alignmentList = task.run("abcd", "efgh");
		assertEquals(1, alignmentList.size());
		Alignment alignment = alignmentList.get(0);
		assertListEquals(new String[]{"a", "b", "c", "d",}, 
				alignment.getSourceSegmentList());
		assertListEquals(new String[]{"ef", "gh",}, 
				alignment.getTargetSegmentList());
	}
	
}

