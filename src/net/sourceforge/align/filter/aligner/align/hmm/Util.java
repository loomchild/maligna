package net.sourceforge.align.filter.aligner.align.hmm;

import net.sourceforge.align.matrix.Matrix;

/**
 * Represents alignment algorithm utilities.
 * @author loomchild
 */
public class Util {

	/**
	 * Checks if x (y) is greater than 0 and less than matrix width (height) 
	 * and if the element stored at this position is not null. 
	 * @param matrix
	 * @param x
	 * @param y
	 * @return true if element exists at given position
	 */
	public static boolean elementExists(Matrix<?> matrix, int x, int y) {
		return (x >= 0 && y >= 0 
				&& x < matrix.getWidth() && y < matrix.getHeight() 
				&& matrix.get(x, y) != null);
	}


}
