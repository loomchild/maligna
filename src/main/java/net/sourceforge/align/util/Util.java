package net.sourceforge.align.util;

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

import net.sourceforge.align.coretypes.Alignment;

/**
 * General purpose utility functions.
 *
 * @author Jarek Lipski (loomchild)
 */
public class Util {
	
	public static final int READ_BUFFER_SIZE = 1024;

	/**
	 * Creates alignment list containing one alignment. This alignment
	 * contains given source segments and no target segments.
	 * 
	 * @param sourceSegmentList source segments
	 * @return singleton alignment list
	 */
	public static List<Alignment> alignManyToZero(
			List<String> sourceSegmentList) {
		Alignment alignment = new Alignment();
		alignment.addSourceSegmentList(sourceSegmentList);
		return Collections.singletonList(alignment);
	}

	/**
	 * Creates alignment list containing one alignment. This alignment
	 * contains no source segments and given target segments.
	 * 
	 * @param targetSegmentList target segments
	 * @return singleton alignment list
	 */
	public static List<Alignment> alignZeroToMany(
			List<String> targetSegmentList) {
		Alignment alignment = new Alignment();
		alignment.addTargetSegmentList(targetSegmentList);
		return Collections.singletonList(alignment);
	}
	
	/**
	 * Creates alignment list containing one alignment. This alignment contains
	 * given source and target segments. 
	 * 
	 * @param sourceSegmentList source segments
	 * @param targetSegmentList target segments
	 * @return singleton alignment list
	 */
	public static List<Alignment> alignManyToMany(
			List<String> sourceSegmentList, List<String> targetSegmentList) {
		Alignment alignment = 
			new Alignment(sourceSegmentList, targetSegmentList, 0.0f);
		return Collections.singletonList(alignment);
	}
	
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
	 * segment arrays. Array sizes must be equalm the resulting list will have
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

	/**
	 * <p>Converts probability to score. The score is more accurate for 
	 * calculations</p> 
	 * <p>score = -ln(probability)</p>
	 * 
	 * @param probability probability
	 * @return score
	 */
	public static double toScore(double probability) {
		return -Math.log(probability);
	}

	/**
	 * <p>Converts score to probability. The probability is easier to understand
	 * by humans than score.</p> 
	 * <p>probability = e^(-score)</p>
	 * 
	 * @param score score
	 * @return probability
	 */
	public static double toProbability(double score) {
		return Math.exp(-score);
	}

	/**
	 * Calculates a sum of probabilities. The probabilities are given
	 * as score and the result is returned as score as well. Takes care
	 * to preserve maximum precision.
	 * 
	 * @see #toScore(double)
	 * @see #toProbability(double)
	 * 
	 * @param scoreList list of score that need to be converted to probabilities 
	 * 		and added
	 * @return score representing a sum of probabilities
	 */
	public static float scoreSum(List<Float> scoreList) {
		float scoreSum;
		if (scoreList.size() == 0) {
			 scoreSum = 0.0f;
		} else {
			float minScore = Collections.min(scoreList);
			if (minScore == Float.POSITIVE_INFINITY) {
				scoreSum = Float.POSITIVE_INFINITY;
			} else {
				double probabilitySum = 0.0;
				for (float score : scoreList) {
					double probability = Util.toProbability(score - minScore);
					probabilitySum += probability;
				}
				double probabilityScore = Util.toScore(probabilitySum);
				scoreSum = (float)(minScore + probabilityScore);
			}
		}
		return scoreSum;
	}

	/**
	 * Merges a string list into a single string without inserting any extra
	 * characters between strings.
	 * 
	 * @param stringList
	 * @return merged string
	 */
	public static String merge(List<String> stringList) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : stringList) {
			stringBuilder.append(string);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Rounds a given number to a given precision places after decimal point.
	 * 
	 * @param number
	 * @param precision
	 * @return Returns rounded number.
	 */
	public static double round(double number, int precision) {
		double cutter = Math.pow(10, precision);
		return (double) ((int) (number * cutter)) / cutter;
	}

	/**
	 * Creates a UTF-8 reader from a given input stream.
	 * @param inputStream
	 * @return reader
	 */
	public static BufferedReader getReader(InputStream inputStream) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			return reader;
		} catch (UnsupportedEncodingException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * Creates a UTF-8 writer from given output stream.
	 * @param outputStream
	 * @return writer
	 */
	public static PrintWriter getWriter(OutputStream outputStream) {
		try {
			return new PrintWriter(new OutputStreamWriter((outputStream),
					"utf-8"), true);
		} catch (UnsupportedEncodingException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * Opens a file with given name for reading.
	 * @param fileName
	 * @return file input stream
	 */
	public static FileInputStream getFileInputStream(String fileName) {
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * Opens a file with given name for writing.
	 * @param fileName
	 * @return file output stream
	 */
	public static FileOutputStream getFileOutputStream(String fileName) {
		try {
			return new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * Finds a resource using class loader and opens it for reading.
	 * 
	 * @param name resource name
	 * @return resource input stream
	 * @throws ResourceNotFoundException when resource could not be found
	 */
	public static InputStream getResourceStream(String name) {
		InputStream inputStream = Util.class.getClassLoader()
				.getResourceAsStream(name);
		if (inputStream == null) {
			throw new ResourceNotFoundException(name);
		}
		return inputStream;
	}

	/**
	 * Reads all reader content into a string. 
	 * Uses {@link #copyAll(Reader, Writer)}.
	 * 
	 * @param reader input reader
	 * @return string
	 * @throws IORuntimeException when IO error occurs
	 */
	public static String readAll(Reader reader) {
		StringWriter writer = new StringWriter();
		copyAll(reader, writer);
		return writer.toString();
	}

	/**
	 * Copies all the content of given reader to given writer. Uses internal
	 * {@value #READ_BUFFER_SIZE} byte buffer to speed up the operation. 
	 * @param reader
	 * @param writer
	 * @throws IORuntimeException when error occurs
	 */
	public static void copyAll(Reader reader, Writer writer) {
		try {
			char[] readBuffer = new char[READ_BUFFER_SIZE];
			int count;
			while ((count = reader.read(readBuffer)) != -1) {
				writer.write(readBuffer, 0, count);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
	
}
