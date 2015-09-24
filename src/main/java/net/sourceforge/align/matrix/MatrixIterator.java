package net.sourceforge.align.matrix;

import java.util.NoSuchElementException;


/**
 * Represents generic matrix iterator.
 * Iterates the matrix from top left to bottom right corner, increasing 
 * row number first, and if it reaches maximum increasing column number
 * ([0,0], [1,0], ... [n,0], [0,1],...). 
 * Some of the elements on the matrix may be ignored if it does not
 * store them, but the overall order must be preserved.
 * Also enables iterating the matrix in reverse order.
 * 
 * @author loomchild
 * @param <T> data type stored in the matrix
 */
public interface MatrixIterator<T> {
	
	/**
	 * @return x position of the iterator (column)
	 */
	public int getX();
	
	/**
	 * @return y position of the iterator (row)
	 */
	public int getY();
	
	/**
	 * Resets the iterator - sets its position to before first element. 
	 */
	public void beforeFirst();

	/**
	 * @return true if iterator has next element (hasn't reached bottom 
	 * 		left corner)
	 */
	public boolean hasNext();
		
	/**
	 * Advances the iterator to the next element. If this is not possible
	 * because iterator hasn't got the next element ({@link #hasNext()} 
	 * returns false) it throws an exception.
	 * 
	 * @throws NoSuchElementException when there are no more elements
	 */
	public void next();
	
	/**
	 * Sets the position to after last element - subsequent calls to 
	 * {@link #hasNext()} will return false.
	 */
	public void afterLast();

	/**
	 * @return true if iterator has previous element (hasn't reached top left
	 * 		corner).
	 */
	public boolean hasPrevious() ;

	/**
	 * Moves the iterator to the previous element. If this is not possible
	 * because iterator hasn't got the previous element ({@link #hasPrevious()} 
	 * returns false) it throws an exception.
	 * 
	 * @throws NoSuchElementException when there is not previous elements
	 */
	public void previous();

}
