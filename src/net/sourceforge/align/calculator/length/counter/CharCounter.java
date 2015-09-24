package net.sourceforge.align.calculator.length.counter;

/**
 * Responsible for calculating length of a segment in characters.
 * @author loomchild
 */
public class CharCounter implements Counter {

	/**
	 * Returns segment length.
	 */
	public int calculateLength(String segment) {
		return segment.length();
	}

}
