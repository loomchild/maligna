package net.sourceforge.align.matrix;

/**
 * Represents {@link BandMatrix} factory.
 * Responsible for creating {@link BandMatrix} objects.
 *  
 * @author loomchild
 */
public class BandMatrixFactory implements MatrixFactory {

	public static final int DEFAULT_BAND_RADIUS = 20;

	private int bandRadius;
	
	/**
	 * Creates band matrix factory producing matrices with given radius.
	 * @param bandRadius
	 */
	public BandMatrixFactory(int bandRadius) {
		this.bandRadius = bandRadius;
	}

	/**
	 * Creates band matrix factory producing matrices with 
	 * {@value #DEFAULT_BAND_RADIUS}.
	 */
	public BandMatrixFactory() {
		this(DEFAULT_BAND_RADIUS);
	}

	public <T> Matrix<T> createMatrix(int width, int height) {
		return new BandMatrix<T>(width, height, bandRadius);
	}

	
	
}
