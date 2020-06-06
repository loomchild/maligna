package net.loomchild.maligna.calculator.length.counter;

/**
 * Responsible for calculating length of given segment. For example 
 * it can return number of characters (see {@link CharCounter}, 
 * number of words (see {@link SplitCounter}) or any other measure. 
 * @author loomchild
 */
public interface Counter {

	/**
	 * Calculates length of a segment.
	 * 
	 * @param segment segment
	 * @return length of a segment, &gt;= 0
	 */
	public int calculateLength(String segment);
	
}
