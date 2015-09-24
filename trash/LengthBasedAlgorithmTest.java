package align.filter.aligner.align;

import static align.util.Util.assertAlignmentEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import align.Alignment;

public class LengthBasedAlgorithmTest {
	
	@Test
	public void testAlign() {
		String sourceSegment1 = "very long sentence indeed";
		String sourceSegment2 = "medium sentemce";
		String targetSegment1 = "short 1";
		String targetSegment2 = "short 2";
		String targetSegment3 = "another medium";
		List<String> sourceSegmentList = Arrays.asList(new String[]{
				sourceSegment1, sourceSegment2});
		List<String> targetSegmentList = Arrays.asList(new String[]{
				targetSegment1, targetSegment2, targetSegment3});
		GaleChurchAlignAlgorithm aligner = new GaleChurchAlignAlgorithm();
		List<Alignment> alignmentList = aligner.align(sourceSegmentList, 
				targetSegmentList);
		Alignment alignment;
		assertEquals(2, alignmentList.size());
		alignment = alignmentList.get(0);
		assertAlignmentEquals(new String[]{sourceSegment1}, 
				new String[]{targetSegment1, targetSegment2}, alignment);
		alignment = alignmentList.get(1);
		assertAlignmentEquals(new String[]{sourceSegment2}, 
				new String[]{targetSegment3}, alignment);

	}

}
