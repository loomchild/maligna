package net.loomchild.maligna.matrix;

/**
 * Represents {@link FullMatrix} factory.
 * Responsible for creating {@link FullMatrix} objects.
 *  
 * @author loomchild
 */
public class FullMatrixFactory implements MatrixFactory {

	public <T> Matrix<T> createMatrix(int width, int height) {
		return new FullMatrix<T>(width, height);
	}
	
}
