package net.sourceforge.align.calculator.meta;

import java.util.List;

import net.sourceforge.align.calculator.Calculator;

/**
 * Represents composite calculator. Alignment score is a sum of
 * scores returned by all calculators (equivalent of product of prababilities 
 * returned by all calculators). 
 * @author loomchild
 */
public class CompositeCalculator implements Calculator {

	private List<Calculator> calculatorList;

	public CompositeCalculator(List<Calculator> calculatorList) {
		this.calculatorList = calculatorList;
	}

	public float calculateScore(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		float score = 0.0f;
		for (Calculator calculator : calculatorList) {
			score += calculator.calculateScore(sourceSegmentList, 
					targetSegmentList);
			if (score == Float.POSITIVE_INFINITY) {
				break;
			}
		}
		assert score >= 0;
		return score;
	}

}
