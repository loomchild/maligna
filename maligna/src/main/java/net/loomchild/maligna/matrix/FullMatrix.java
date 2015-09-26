package net.loomchild.maligna.matrix;


/**
 * Represents a two-dimensional matrix that contains all the elements.
 * This matrix use standard two-dimensional array and occupies 
 * row number * column number memory, 
 * which can be quite memory inefficient for sparse matrices. 
 * On the other hand element access should be fast with this matrix. 
 * 
 * @author loomchild
 */
public class FullMatrix<T> implements Matrix<T> {

	private Object[][] dataArray;
	
	private int width;
	
	private int height;
	
	/**
	 * Creates a matrix.
	 * @param width matrix width (number of columns), >= 1.
	 * @param height matrix height (number of rows), >= 1.
	 */
	public FullMatrix(int width, int height) {
		this.width = width;
		this.height = height;
		this.dataArray = new Object[width][height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return width * height;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int x, int y) {
		return (T)dataArray[x][y];
	}

	public void set(int x, int y, T data) {
		dataArray[x][y] = data;
	}

	public MatrixIterator<T> getIterator() {
		return new FullMatrixIterator<T>(this);
	}

}
