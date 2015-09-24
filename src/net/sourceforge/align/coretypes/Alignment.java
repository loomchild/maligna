package net.sourceforge.align.coretypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Represents a mapping of n segments in source text to m segments in target text.
 * Responsible for storing the segment contents and alignment score equal to 
 * -ln(probability). 
 *
 * @author Jarek Lipski (loomchild)
 */
public class Alignment {
	
	public static final float DEFAULT_SCORE = 0.0f;

	private List<String> sourceSegmentList;
	
	private List<String> targetSegmentList;
	
	private float score;
	
	/**
	 * Creates empty alignment (0 - 0) with default score equal to 
	 * {@value #DEFAULT_SCORE}.
	 */
	public Alignment() {
		this.sourceSegmentList = new ArrayList<String>();
		this.targetSegmentList = new ArrayList<String>();
		this.score = DEFAULT_SCORE;
	}
	
	/**
	 * Creates alignment of a given list of source segments to a given list 
	 * of target segments with a given score. Creates copies of the lists.
	 * 
	 * @param sourceSegmentList Source segment list
	 * @param targetSegmentList Target segment list
	 * @param score Score of alignment
	 */
	public Alignment(List<String> sourceSegmentList, 
			List<String> targetSegmentList, float score) {
		this();
		addSourceSegmentList(sourceSegmentList);
		addTargetSegmentList(targetSegmentList);
		this.score = score;
	}

	/**
	 * Creates alignment of a given list of source segments to a given list 
	 * of target segments with score equal to {@value #DEFAULT_SCORE}.
	 * Creates copies of the lists.
	 * 
	 * @param sourceSegmentList Source segment list
	 * @param targetSegmentList Target segment list
	 */
	public Alignment(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		this(sourceSegmentList, targetSegmentList, DEFAULT_SCORE);
	}

	public Alignment(String[] sourceSegmentArray, 
			String[] targetSegmentArray, float score) {
		this(Arrays.asList(sourceSegmentArray), 
				Arrays.asList(targetSegmentArray), score);
	}

	public Alignment(String[] sourceSegmentArray, 
			String[] targetSegmentArray) {
		this(sourceSegmentArray, targetSegmentArray, DEFAULT_SCORE);
	}

	/**
	 * Adds a segment to source segment list.
	 * @param segment Source segment
	 */
	public void addSourceSegment(String segment) {
		sourceSegmentList.add(segment);
	}

	/**
	 * Adds a list of segments to source segment list.
	 * @param segmentList Source segment list
	 */
	public void addSourceSegmentList(List<String> segmentList) {
		sourceSegmentList.addAll(segmentList);
	}

	/**
	 * Adds a segment to target segment list.
	 * @param segment Target segment
	 */
	public void addTargetSegment(String segment) {
		targetSegmentList.add(segment);
	}
	
	/**
	 * Adds a list of segments to target segment list.
	 * @param segmentList Target segment list
	 */
	public void addTargetSegmentList(List<String> segmentList) {
		targetSegmentList.addAll(segmentList);
	}

	/**
	 * @return Returns an unmodifiable source segment list 
	 */
	public List<String> getSourceSegmentList() {
		return Collections.unmodifiableList(sourceSegmentList);
	}

	/**
	 * @return Returns an unmodifiable target segment list 
	 */
	public List<String> getTargetSegmentList() {
		return Collections.unmodifiableList(targetSegmentList);
	}
	
	/**
	 * @return Returns alignment score.
	 */
	public float getScore() {
		return score;
	}

	/**
	 * Sets alignment score 
	 * @param score Score
	 */
	public void setScore(float score) {
		this.score = score;
	}
	
	/**
	 * Retrieves alignment category (1-1, 2-1, etc.)
	 * @return category
	 */
	public Category getCategory() {
		return new Category(sourceSegmentList.size(), targetSegmentList.size());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((sourceSegmentList == null) ? 0 : sourceSegmentList
						.hashCode());
		result = prime
				* result
				+ ((targetSegmentList == null) ? 0 : targetSegmentList
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alignment other = (Alignment) obj;
		if (sourceSegmentList == null) {
			if (other.sourceSegmentList != null)
				return false;
		} else if (!sourceSegmentList.equals(other.sourceSegmentList))
			return false;
		if (targetSegmentList == null) {
			if (other.targetSegmentList != null)
				return false;
		} else if (!targetSegmentList.equals(other.targetSegmentList))
			return false;
		return true;
	}

}
