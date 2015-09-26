package net.loomchild.maligna.calculator.content;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.calculator.Calculator;

/**
 * Represents calculator using reference alignments to calculate score.
 * Returns successScore if measured alignment is present in reference 
 * alignments, failureScore otherwise.
 * 
 * @author loomchild
 */
public class OracleCalculator implements Calculator {
	
	public static final float DEFAULT_FAILURE_SCORE = Float.POSITIVE_INFINITY;

	public static final float DEFAULT_SUCCESS_SCORE = 0.0f;
	
	private Set<Alignment> alignmentSet;

	private float successScore;
	
	private float failureScore;
	
	/**
	 * Creates oracle calculator.
	 * 
	 * @param alignmentCollection reference alignment
	 * @param failureScore score returned when calculated alignment is not
	 * 		in the reference alignment
	 * @param successScore score returned when calculated alignment is in
	 * 		the reference alignment
	 */
	public OracleCalculator(Collection<Alignment> alignmentCollection, 
			float failureScore, float successScore) {
		this.alignmentSet = new HashSet<Alignment>(alignmentCollection);
		this.failureScore = failureScore;
		this.successScore = successScore;
	}

	/**
	 * Creates oracle calculator. Success score is equal to 
	 * {@link #DEFAULT_SUCCESS_SCORE}.
	 * 
	 * @param alignmentCollection reference alignment
	 * @param failureScore score returned when calculated alignment is not
	 * 		in the reference alignment
	 */
	public OracleCalculator(Collection<Alignment> alignmentCollection, 
			float failureScore) {
		this(alignmentCollection, failureScore, DEFAULT_SUCCESS_SCORE);
	}

	/**
	 * Creates oracle calculator. Success score is equal to 
	 * {@link #DEFAULT_SUCCESS_SCORE} and failure score is equal to
	 * {@value #DEFAULT_FAILURE_SCORE}.
	 * 
	 * @param alignmentCollection reference alignment
	 */
	public OracleCalculator(Collection<Alignment> alignmentCollection) {
		this(alignmentCollection, DEFAULT_FAILURE_SCORE, DEFAULT_SUCCESS_SCORE);
	}
	
	/**
	 * Returns successScore if measured alignment is present in reference 
	 * alignments, failureScore otherwise.
	 */
	public float calculateScore(List<String> sourceSegmentList,
			List<String> targetSegmentList) {
		Alignment alignment = new Alignment(sourceSegmentList, targetSegmentList);
		if (alignmentSet.contains(alignment)) {
			return successScore;
		} else {
			return failureScore;
		}
	}

}
