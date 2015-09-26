package net.loomchild.maligna.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

/**
 * Testing utility functions.
 *
 * @author Jarek Lipski (loomchild)
 */
public class TestUtil {
	
	/**
	 * Asserts that given alignment consists of given source and 
	 * target segments. If not then exception is thrown. Used for unit testing.
	 * 
	 * @param sourceSegmentArray expected source segments
	 * @param targetSegmentArray expected target segments
	 * @param alignment alignment to be checked
	 * @throws AssertionError when alignment does not consist of 
	 *         given segments
	 */
	public static void assertAlignmentEquals(String[] sourceSegmentArray, 
			String[] targetSegmentArray, Alignment alignment) {
		List<String> sourceSegmentList = alignment.getSourceSegmentList();
		List<String> targetSegmentList = alignment.getTargetSegmentList();
		String[] actualSourceSegmentArray = 
			sourceSegmentList.toArray(new String[sourceSegmentList.size()]);
		String[] actualTargetSegmentArray = 
			targetSegmentList.toArray(new String[targetSegmentList.size()]);
		assertEquals(sourceSegmentArray, actualSourceSegmentArray);
		assertEquals(targetSegmentArray, actualTargetSegmentArray);
	}

	/**
	 * Asserts that alignments on a given list consists of given source and 
	 * target segments in given arrays. If not then exception is thrown.
	 * 
	 * @param sourceSegmentArray expected source segments
	 * @param targetSegmentArray expected target segments
	 * @param alignmentList alignments to be checked
	 * @throws AssertionError when any alignment on the list does not 
	 * 		consist of given segments
	 */
	public static void assertAlignmentListEquals(String[][] sourceSegmentArray, 
			String[][] targetSegmentArray, List<Alignment> alignmentList) {
		assertEquals(sourceSegmentArray.length, targetSegmentArray.length);
		int length = sourceSegmentArray.length;
		assertEquals(length, alignmentList.size());
		int i = 0;
		for (Alignment alignment : alignmentList) {
			assertAlignmentEquals(sourceSegmentArray[i], targetSegmentArray[i], 
					alignment);
			++i;
		}
	}
	
	/**
	 * Asserts that given alignment list consists only from given 
	 * source and target segments in the same order.
	 * 
	 * @param sourceSegments expected source segments, order is important
	 * @param targetSegments expected target segments, order is important
	 * @param alignmentList alignments to be checked
	 * @throws AssertionError when alignment list does not consist only from 
	 * 		given source and target segments
	 */
	public static void assertAlignmentListContains(String[] sourceSegments, 
			String[] targetSegments, List<Alignment> alignmentList) {
		List<String> actualSourceSegments = new ArrayList<String>();
		List<String> actualTargetSegments = new ArrayList<String>();
		for (Alignment alignment : alignmentList) {
			actualSourceSegments.addAll(alignment.getSourceSegmentList());
			actualTargetSegments.addAll(alignment.getTargetSegmentList());
		}
		assertEquals(sourceSegments, actualSourceSegments.toArray());
		assertEquals(targetSegments, actualTargetSegments.toArray());
	}

	/**
	 * Creates alignment list from segments read from given source and target 
	 * segment arrays. Array sizes must be equal, the resulting list will have
	 * the same size. Used for unit testing.
	 * 
	 * @param sourceArray
	 * @param targetArray
	 * @return alignment list
	 */
	public static List<Alignment> createAlignmentList(String[][] sourceArray, 
			String[][] targetArray) {
		assertEquals(sourceArray.length, targetArray.length);
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		int alignmentCount = sourceArray.length;
		for (int i = 0; i < alignmentCount; ++i) {
			Alignment alignment = new Alignment(Arrays.asList(sourceArray[i]), 
					Arrays.asList(targetArray[i]), 1.0f);
			alignmentList.add(alignment);
		}
		return alignmentList;
	}
	
	/**
	 * Returns array of segments containing only alignments with indexes
	 * specified in indexes array from given segment array. 
	 * Used for unit testing.
	 * 
	 * @param segmentArray segment array
	 * @param indexes index array; contains indexes of elements from 
	 * 		segment array
	 * @return segment array containing selected segments
	 */
	public static String[][] filterSegmentArray(String[][] segmentArray, 
			int[] indexes) {
		String[][] newArray = new String[indexes.length][];
		for (int i = 0; i < indexes.length; ++i) {
			newArray[i] = segmentArray[indexes[i]];
		}
		return newArray;
	}

}
