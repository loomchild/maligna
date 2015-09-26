package net.loomchild.maligna.calculator.meta;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.CalculatorMock;

import org.junit.Test;

/**
 * Represents unit test of {@link CompositeCalculator}.
 * @author loomchild
 */
public class CompositeCalculatorTest {

	/**
	 * Checks using {@link CalculatorMock} that composite really returns
	 * the sum of scores of all contained calculators.
	 */
	@Test
	public void calculate() {
		List<Calculator> calculatorList = Arrays.asList(new Calculator[] {
				new CalculatorMock(0.5f), new CalculatorMock(0.25f)});
		Calculator calculator = new CompositeCalculator(calculatorList);
		assertEquals(0.75f, calculator.calculateScore(null, null), 0.75f);
	}
	
}
