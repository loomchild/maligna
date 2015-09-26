package net.loomchild.maligna.calculator;

import java.util.List;


/**
 * Calculator mock returning always predefined score, na matter what segments
 * are passed in. Used for testing.
 *
 * @author Jarek Lipski (loomchild)
 */
public class CalculatorMock implements Calculator {

	private float score;
	
	public CalculatorMock(float score) {
		this.score = score;
	}
	
	public float calculateScore(List<String> sourceSegmentList, List<String> targetSegmentList) {
		return score;
	}

}
