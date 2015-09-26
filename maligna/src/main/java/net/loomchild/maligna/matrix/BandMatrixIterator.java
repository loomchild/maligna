package net.loomchild.maligna.matrix;

import java.util.NoSuchElementException;

/**
 * Represents {@link BandMatrix} iterator.
 * 
 * @author loomchild
 * @param <T> matrix data type 
 */
public class BandMatrixIterator<T> implements MatrixIterator<T> {

	private BandMatrix<T> matrix;
	
	private int x, y, maxX, minX;
	
	public BandMatrixIterator(BandMatrix<T> matrix) {
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
		calculateMinMaxX();
	}

	public boolean hasNext() {
		return !((y >= matrix.getHeight() - 1) && (x >= matrix.getWidth() - 1));
	}

	public void next() {
		++x;
		if (x > maxX) {
			++y;
			calculateMinMaxX();
			x = minX;
			if (y >= matrix.getHeight()) {
				throw new NoSuchElementException();
			}
		}
	}

	public void afterLast() {
		x = matrix.getWidth();
		y = matrix.getHeight() - 1;
		calculateMinMaxX();
	}

	public boolean hasPrevious() {
		return !((y <= 0) && (x <= 0));
	}

	public void previous() {
		--x;
		if (x < minX) {
			--y;
			calculateMinMaxX();
			x = maxX;
			if (y < 0) {
				throw new NoSuchElementException();
			}
		}
	}
	
	/**
	 * Calculates minimum and maximum x position (column number) at current 
	 * y position (row number) and stores then in {@link #minX} and 
	 * {@link #maxX}.
	 */
	private void calculateMinMaxX() {
		int diagonalX = matrix.getDiagonalX(y);
		minX = Math.max(0, diagonalX - matrix.getBandRadius());
		maxX = Math.min(matrix.getWidth() - 1, 
				diagonalX + matrix.getBandRadius());
	}

}
