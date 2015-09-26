package net.loomchild.maligna.calculator.length;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.model.length.LengthModelUtil;
import net.loomchild.maligna.calculator.length.counter.Counter;
import net.loomchild.maligna.model.length.LengthModel;


/**
 * Represents length-base calculator that computes alignment score 
 * assuming that alignment lengths have Poisson distribution.
 * Calculates length models (models based on frequency of occurrence of 
 * segment lengths) using training corpus (can be the same as the input corpus).
 * 
 * @author loomchild
 */
public class PoissonDistributionCalculator 
		extends LengthCalculator {
	
	private LengthModel sourceLengthModel;

	private LengthModel targetLengthModel;

	private float meanLengthRatio;
	
	/**
	 * Creates calculator. Calculates source and target {@link LengthModel} 
	 * using reference corpus (can be the same as actual corpus being aligned).
	 * 
	 * @param counter length counter
	 * @param alignmentList reference alignment
	 */
	public PoissonDistributionCalculator(Counter counter,
			List<Alignment> alignmentList) {
		super(counter);
		List<String> sourceSegmentList = new ArrayList<String>();
		List<String> targetSegmentList = new ArrayList<String>();
		for (Alignment alignment : alignmentList) {
			sourceSegmentList.addAll(alignment.getSourceSegmentList());
			targetSegmentList.addAll(alignment.getTargetSegmentList());
		}
		this.sourceLengthModel = trainLengthModel(sourceSegmentList);
		this.targetLengthModel = trainLengthModel(targetSegmentList);
		this.meanLengthRatio = targetLengthModel.getMeanLength() / 
				sourceLengthModel.getMeanLength();
	}
	
	/**
	 * Calculates length model of given segment list.
	 * @param segmentList
	 * @return
	 */
	private LengthModel trainLengthModel(List<String> segmentList) {
		List<Integer> lengthList = calculateLengthList(segmentList);
		LengthModel lengthModel = LengthModelUtil.train(lengthList);
		return lengthModel;
	}

	/**
	 * Calculates alignment score. If both input lists are empty returns zero.
	 * If only one of them is empty returns the other language score 
	 * (probability of segment being part of language). If both are non-zero
	 * then returns translation score (probability of target segments being 
	 * translations of source segments) added to language score of source 
	 * segments.
	 */
	protected float calculateLengthScore(List<Integer> sourceLengthList, 
			List<Integer> targetLengthList) {
		float score;
		if (sourceLengthList.size() == 0 && targetLengthList.size() == 0) {
			score = 0.0f;
		} else if (sourceLengthList.size() == 0) {
			score = calculateLanguageScore(targetLengthList, targetLengthModel);
		} else {
			score = calculateLanguageScore(sourceLengthList, sourceLengthModel);
			if (targetLengthList.size() > 0) {
				score += calculateTranslationScore(sourceLengthList,
						targetLengthList);
			}
		}
		assert score >= 0;
		return score;
	}
		
	/**
	 * Calculates the score (equal to -ln(probability)) of segments of given 
	 * lengths being part of given language (occur in given length model).
	 *  
	 * @param lengthList list of segment lengths
	 * @param lengthModel length model for a language
	 * @return score of length list matching with length model
	 */
	private float calculateLanguageScore(List<Integer> lengthList, 
			LengthModel lengthModel) {
		float score = 0.0f;
		for (int length : lengthList) {
			score += -Math.log(lengthModel.getLengthProbability(length));
		}
		assert score >= 0;
		return score;		
	}
	
	/**
	 * Calculates score of target segments of given lengths being translations
	 * of source segments of given lengths. 
	 *  
	 * @param sourceLengthList lengths of source segments
	 * @param targetLengthList lengths of target segments
	 * @return score
	 */
	private float calculateTranslationScore(List<Integer> sourceLengthList, 
			List<Integer> targetLengthList) {
		int sourceTotalLength = calculateTotalLength(sourceLengthList);
		int targetTotalLength = calculateTotalLength(targetLengthList);
		float mean = sourceTotalLength * meanLengthRatio;
		float score = poissonDistribution(mean, targetTotalLength);
		assert score >= 0;
		return score;
	}
	
	/**
	 * Calculates value of a Poisson distribution function at given point (x). 
	 * @param mean poisson distribution mean
	 * @param x x value
	 * @return y value
	 */
	static float poissonDistribution(float mean, int x) {
		assert mean > 0;
		return mean + -x * (float)Math.log(mean) + factorial(x);
	}
	
	/**
	 * Returns logarithm from factorial of a given number ln(x!).
	 * @param x number
	 * @return ln(x!)
	 */
	static float factorial(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("Cannot calculate factorial " +
					"for a negative number: " + x + ".");
		} else {
			float y = 0;
			for (int i = 2; i <= x; ++i) {
				y += Math.log(i);
			}
			return y;
		}
	}
	
}
