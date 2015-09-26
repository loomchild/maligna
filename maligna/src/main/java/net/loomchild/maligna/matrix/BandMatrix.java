package net.loomchild.maligna.matrix;


/**
 * <p>Represents matrix that stores only elements within given radius around 
 * the diagonal.</p>
 * 
 * <p>This way the required memory is reduced to height * band width. 
 * For example: 
 * [X represents stored elements, 0 represents not stored elements.]</p>
 * <pre>
 * XX000
 * XXX00
 * 0XXX0
 * 00XXX
 * 000XX
 * </pre>
 * 
 * <p>If user tries to access element outside band then 
 * {@link PositionOutsideBandException} will be thrown. Matrix iterator 
 * ({@link BandMatrixIterator}) iterates only on the elements inside band. 
 * 
 * @author loomchild
 */
public class BandMatrix<T> implements Matrix<T> {
	
	private Object[][] dataArray;
	
	private int width;
	
	private int height;
	
	private int bandWidth;
	
	private int bandRadius;
	
	float widthHeightRatio;

	/**
	 * Creates matrix.
	 * @param width width of matrix (columns), >= 1
	 * @param height height of matrix (rows), >= 1
	 * @param bandRadius radius
	 */
	public BandMatrix(int width, int height, int bandRadius) {
		assert bandRadius >= 1;
		this.width = width;
		this.height = height;
		this.bandRadius = bandRadius;
		this.bandWidth = bandRadius * 2 + 1;
		this.dataArray = new Object[bandWidth][height];
		this.widthHeightRatio = (float)width / (float)height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getSize() {
		return bandWidth * height - 
				(int)(bandRadius * bandRadius * widthHeightRatio);
	}

	@SuppressWarnings("unchecked")
	public T get(int x, int y) {
		assert x >= 0 && x < width;
		assert y >= 0 && y < height;
		int actualX = getActualX(x, y);
		if (actualX >= 0 && actualX < bandWidth) {
			return (T)dataArray[actualX][y];
		} else {
			return null;
		}
	}

	public void set(int x, int y, T data) {
		assert x >= 0 && x < width;
		assert y >= 0 && y < height;
		int actualX = getActualX(x, y);
		if (actualX >= 0 && actualX < bandWidth) {
			dataArray[actualX][y] = data;
		} else {
			throw new PositionOutsideBandException(x, y, width, height, 
					bandWidth);
		}
	}
	
	int getBandRadius() {
		return bandRadius;
	}
	
	/**
	 * Gets the x position on the diagonal corresponding to given y postion.
	 * Used by {@link BandMatrixIterator}.
	 * @param y y position (row number)
	 * @return x position (column number) of the element on the diagonal
	 * 		at given y position (row number) 
	 */
	int getDiagonalX(int y) {
		return (int)((float)y * widthHeightRatio);
	}
	
	/**
	 * @param x x position (column number)
	 * @param y y position (row number)
	 * @return gets the x position of element in the storage data array
	 * 		corresponding to x, y positions in the abstract matrix 
	 */
	private int getActualX(int x, int y) {
		return x - getDiagonalX(y) + bandRadius;
	}

	public MatrixIterator<T> getIterator() {
		return new BandMatrixIterator<T>(this);
	}
	
	
}
