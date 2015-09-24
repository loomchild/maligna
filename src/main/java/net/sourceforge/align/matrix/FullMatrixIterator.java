package net.sourceforge.align.matrix;

import java.util.NoSuchElementException;

/**
 * Represents {@link FullMatrix} iterator.
 * 
 * @author loomchild
 * @param <T> matrix data type 
 */
public class FullMatrixIterator<T> implements MatrixIterator<T> {

	private FullMatrix<T> matrix;
	
	private int x, y;

	public FullMatrixIterator(FullMatrix<T> matrix) {
		this.matrix = matrix;
		beforeFirst();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void beforeFirst() {
		x = -1;
		y = 0;
	}

	public boolean hasNext() {
		return !((y >= matrix.getHeight() - 1) && (x >= matrix.getWidth() - 1));
	}

	public void next() {
		++x;
		if (x >= matrix.getWidth()) {
			++y;
			x = 0;
			if (y >= matrix.getHeight()) {
				throw new NoSuchElementException();
			}
		}
	}

	public void afterLast() {
		x = matrix.getWidth();
		y = matrix.getHeight() - 1;
	}

	public boolean hasPrevious() {
		return !((y <= 0) && (x <= 0));
	}

	public void previous() {
		--x;
		if (x < 0) {
			--y;
			x = matrix.getWidth() - 1;
			if (y < 0) {
				throw new NoSuchElementException();
			}
		}
	}

}
