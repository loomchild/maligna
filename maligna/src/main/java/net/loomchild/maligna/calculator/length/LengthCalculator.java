package net.loomchild.maligna.calculator.length;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.length.counter.Counter;


/**
 * Represents calculator that computes alignment probability based only on
 * segment length. Implements part of the {@link Calculator} functionality
 * and provides utility functions to inheriting concrete length-based 
 * calculators.
 *  
 * @author loomchild
 */
public abstract class LengthCalculator implements Calculator {
	
	private Counter counter;
	
	/**
	 * Creates a calculator.
	 * @param counter segment length counter (for example character count, 
	 * 		word count)
	 */
	public LengthCalculator(Counter counter) {
		this.counter = counter;
	}

	/**
	 * Calculates alignment score first by computing lengths of all the segments
	 * and later passing the results and control to the subclasses to do 
	 * the actual score calculation.
	 */
	public float calculateScore(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		List<Integer> sourceLengthList = calculateLengthList(sourceSegmentList);
		List<Integer> targetLengthList = calculateLengthList(targetSegmentList);
		return calculateLengthScore(sourceLengthList, targetLengthList);
	}

	/**
	 * Calculates and returns lengths of subsequent segments.
	 * @param segmentList segment list 
	 * @return list of lengths of source segments
	 */
	protected List<Integer> calculateLengthList(List<String> segmentList) {
		List<Integer> lengthList = new ArrayList<Integer>();
		for (String segment : segmentList) {
			int length = counter.calculateLength(segment);
			if (length > 0) {
				lengthList.add(length);
			}
		}
		return lengthList;
	}

	/**
	 * Utility function to calculate total length of the segments. 
	 * Returns sum of the lengths on the input list. Used by subclasses.
	 * 
	 * @param lengthList list containing lengths
	 * @return sum of lengths on the list
	 */
	protected int calculateTotalLength(List<Integer> lengthList) {
		int totalLength = 0;
		for (int length : lengthList) {
			totalLength += length;
		}
		return totalLength;
	}

	/**
	 * Abstract method implemented by subclasses to compute the actual score.
	 * 
	 * @param sourceLengthList lengths of source segments
	 * @param targetLengthList lengths of target segments
	 * @return source to target segments alignment score, &gt;= 0 
	 */
	protected abstract float calculateLengthScore(List<Integer> sourceLengthList, 
			List<Integer> targetLengthList);

}
