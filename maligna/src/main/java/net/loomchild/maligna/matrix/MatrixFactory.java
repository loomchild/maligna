package net.loomchild.maligna.matrix;

/**
 * Represents matrix factory. 
 * Enables to create a matrix of given size without knowing the actual 
 * matrix type.
 * 
 * @author loomchild
 */
public interface MatrixFactory {

	public <T> Matrix<T> createMatrix(int width, int height);
	
}
