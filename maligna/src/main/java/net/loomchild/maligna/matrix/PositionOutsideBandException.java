package net.loomchild.maligna.matrix;

/**
 * Represents an exception when user tries to access {@link BandMatrix}
 * element outside matrix band.
 * @see BandMatrix
 * @author loomchild
 */
public class PositionOutsideBandException extends RuntimeException {
	
	private static final long serialVersionUID = 4329499541774129117L;

	public PositionOutsideBandException(int x, int y, int width, int height, 
			int bandWidth) {
		super("Position " + "(" + x + ", " + y + ") in matrix of size (" +
				width + ", " + height + ") outside band of width " + bandWidth);
	}


}
