package net.sourceforge.align.matrix;


/**
 * Represents generic abstract two-dimensional matrix.
 * Useful because dimensions can be big but the data is usually sparse, 
 * so more sophisticated implementations than normal two-dimensional array 
 * are desired.
 * 
 * @author Jarek Lipski (loomchild)
 */
public interface Matrix<T> {
	
	/**
	 * @return matrix width (number of columns)
	 */
	public int getWidth();
	
	/**
	 * @return matrix height (number of rows)
	 */
	public int getHeight();
	
	/**
	 * @return real matrix size (number of stored elements, <= width * height
	 */
	public int getSize();
	
	/**
	 * Returns matrix element at given position.
	 * @param x column
	 * @param y row
	 * @return element
	 */
	public T get(int x, int y);

	/**
	 * Sets the matrix element at given position.
	 * @param x column
	 * @param y row
	 * @param data element
	 */
	public void set(int x, int y, T data);
	
	/**
	 * @see MatrixIterator
	 * @return matrix iterator that will iterate over whole matrix
	 * 		from top left to bottom right corner.
	 */
	public MatrixIterator<T> getIterator();
	
}
