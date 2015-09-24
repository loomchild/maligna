package align.task;

import static loomchild.util.Utils.readAll;
import static loomchild.util.testing.Utils.assertListEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import align.aligner.Aligner;
import align.aligner.AlignerMock;
import align.aligner.AlignmentImpossibleException;
import align.data.Alignment;
import align.splitter.Splitter;
import align.splitter.SplitterMock;

import junit.framework.TestCase;

public class SimpleTaskTest extends TestCase {
	
	private SimpleTask task;
	
	public void setUp() {
		Splitter sourceSplitter = new SplitterMock(1);
		Splitter targetSplitter = new SplitterMock(2);
		Aligner aligner = new AlignerMock();		
		task = new SimpleTask(sourceSplitter, targetSplitter, aligner);
	}
	
	public void testUseDifferentSplitters() throws AlignmentImpossibleException {
		List<Alignment> alignmentList = task.run("abcd", "efgh");
		assertEquals(1, alignmentList.size());
		Alignment alignment = alignmentList.get(0);
		assertListEquals(new String[]{"a", "b", "c", "d",}, 
				alignment.getSourceSegmentList());
		assertListEquals(new String[]{"ef", "gh",}, 
				alignment.getTargetSegmentList());
	}
	
	public void testAlignManyToZero() 
			throws AlignmentImpossibleException, IOException {
		String sourceText = "abcd";
		String targetText = "";
		checkManyToZero(runTask(sourceText, targetText));
		checkManyToZero(runTaskFromReaders(sourceText, targetText));
	}
	
	private void checkManyToZero(List<Alignment> alignmentList) {
		Alignment alignment = getOnlyElement(alignmentList);
		assertListEquals(new String[]{"a", "b", "c", "d",}, 
				alignment.getSourceSegmentList());
		assertListEquals(new String[]{}, alignment.getTargetSegmentList());
	}

	public void testAlignZeroToMany() 
			throws AlignmentImpossibleException, IOException {
		String sourceText = "";
		String targetText = "efgh";
		checkZeroToMany(runTask(sourceText, targetText));
		checkZeroToMany(runTaskFromReaders(sourceText, targetText));
	}
	
	private void checkZeroToMany(List<Alignment> alignmentList) {
		Alignment alignment = getOnlyElement(alignmentList);
		assertListEquals(new String[]{}, alignment.getSourceSegmentList());
		assertListEquals(new String[]{"ef", "gh",}, 
				alignment.getTargetSegmentList());
	}

	public void testAlignZeroToZero() 
			throws AlignmentImpossibleException, IOException {
		String sourceText = "";
		String targetText = "";
		checkZeroToZero(runTask(sourceText, targetText));
		checkZeroToZero(runTaskFromReaders(sourceText, targetText));
	}
	
	private void checkZeroToZero(List<Alignment> alignmentList) {
		assertEquals(0, alignmentList.size());
	}
	
	private List<Alignment> runTask(String sourceString, String targetString)
			throws AlignmentImpossibleException {
		return task.run(sourceString, targetString);
	}

	private List<Alignment> runTaskFromReaders(String sourceString, 
			String targetString) 
			throws AlignmentImpossibleException, IOException {
		Reader sourceReader = new StringReader(sourceString);
		Reader targetReader = new StringReader(targetString);
		return task.run(sourceReader, targetReader);
	}

	private <T> T getOnlyElement(List<T> list) {
		assertEquals(1, list.size());
		return list.get(0);
	}

}

