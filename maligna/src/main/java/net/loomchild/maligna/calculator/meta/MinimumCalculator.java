package net.loomchild.maligna.calculator.meta;

import java.util.List;

import net.loomchild.maligna.calculator.Calculator;

/**
 * Represents conditional calculator.
 * If score calculated by given testCalculator is equal or less than the 
 * given threshold returns given minimum score, 
 * otherwise returns score calculated by calculator.
 * 
 * TODO: generalize to n calculators, simplify that no default is defined -
 * just if score returned by any calculator is zero then do not try calculate
 * others because score by definition cannot be less than zero.
 * 
 * @author loomchild
 *
 */
public class MinimumCalculator implements Calculator {
	
	private static final float DEFAULT_MINIMUM_SCORE = 0.0f;
	
	private Calculator testCalculator;
	
	private Calculator calculator;
	
	private float scoreThreshold;
	
	private float minimumScore;
	
	public MinimumCalculator(Calculator testCalculator, 
			Calculator calculator, float scoreThreshold, float minumumScore) {
		this.testCalculator = testCalculator;
		this.calculator = calculator;
		this.scoreThreshold = scoreThreshold;
		this.minimumScore = minumumScore;
	}

	public MinimumCalculator(Calculator testCalculator, 
			Calculator calculator, float scoreThreshold) {
		this(testCalculator,calculator, scoreThreshold, DEFAULT_MINIMUM_SCORE);
	}

	public float calculateScore(List<String> sourceSegmentList,
			List<String> targetSegmentList) {

		float testScore = 
			testCalculator.calculateScore(sourceSegmentList, targetSegmentList);
		float score;
		
		if (testScore <= scoreThreshold) {
			score = minimumScore;
		} else {
			score = calculator.calculateScore(sourceSegmentList, targetSegmentList);
		}
		
		return score;
		
	}

}
