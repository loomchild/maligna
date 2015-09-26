package net.loomchild.maligna.util;

import java.io.IOException;

public class IORuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6587044052300876023L;

	public IORuntimeException(IOException exception) {
		super(exception);
	}

	public void rethrow() throws IOException {
		throw (IOException) getCause();
	}

}
