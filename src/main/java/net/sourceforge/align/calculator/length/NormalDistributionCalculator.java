package net.sourceforge.align.calculator.length;

import java.util.List;

import net.sourceforge.align.calculator.length.counter.Counter;


/**
 * Represents calculator computing alignment score assuming that 
 * segment lengths have normal distribution with given constant parameters.
 * 
 * TODO: describe distribution parameters.
 * 
 * @see "A Program for Aligning Sentences in Bilingual Corpora, 
 * 		William A. Gale, Kenneth W. Church"
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Normal_distribution">Normal Distribution</a>
 * 
 * @author loomchild
 */
public class NormalDistributionCalculator extends 
		LengthCalculator {

	public static final float PARAMETER_C = 1.0f;

	public static final float PARAMETER_S_SQUARE = 6.8f;
	
	/**
	 * Creates calculator.
	 * @param counter segment length counter
	 */
	public NormalDistributionCalculator(Counter counter) {
		super(counter);
	}

	/**
	 * Calculates alignment score.
	 */
	protected float calculateLengthScore(List<Integer> sourceLengthList, 
			List<Integer> targetLengthList) {
		int sourceSegmentLength = calculateTotalLength(sourceLengthList);
		int targetSegmentLength = calculateTotalLength(targetLengthList);
		return calculateScore(sourceSegmentLength, targetSegmentLength);
	}
	
	/**
	 * Calculates probability of target segment of given length being 
	 * translation of source segment with given length. If both length are
	 * equal to zero returns zero.
	 *  
	 * @param sourceSegmentLength
	 * @param targetSegmentLength
	 * @return
	 */
	private float calculateScore(int sourceSegmentLength, 
			int targetSegmentLength) {
		if (sourceSegmentLength == 0 && targetSegmentLength == 0) {
			return 0.0f;
		} else {
			double mean = (sourceSegmentLength + targetSegmentLength / 
					PARAMETER_C) / 2.0;
			double z = Math.abs((PARAMETER_C * sourceSegmentLength - 
					targetSegmentLength) / Math.sqrt(PARAMETER_S_SQUARE * mean));
			double pd = 2.0 * (1.0 - cumulativeNormalDistribution(z));
			// Needed because sometimes returns zero
			pd = Math.max(pd, Float.MIN_VALUE);
			assert pd > 0.0;
			return (float)-Math.log(pd);
		}
	}

	/**
	 * Calculates value of normal distribution for given z position (z >= 0).
	 * 
	 * @see "Handbook of Mathematical Functions, Abrahamowitz, Stegun" 
	 * @param z random variable
	 * @return random variable distribution value
	 */
	private double cumulativeNormalDistribution(double z) {
		assert z >= 0.0;
		double t = 1.0 / (1.0 + 0.2316419 * z);
		double pd = 1.0 - 0.3989423 * Math.exp(-z * z / 2.0) *
			((((1.330274429 * t - 1.821255978) * t
					+ 1.781477937) * t - 0.356563782) * t + 0.319381530) * t;
		return pd;
	}


}
