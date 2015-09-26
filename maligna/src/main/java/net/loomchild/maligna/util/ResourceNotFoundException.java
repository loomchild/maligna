package net.loomchild.maligna.util;

/**
 * Represents an exception that is thrown when a resource (usually a file)
 * cannot be found.
 * @author loomchild
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 318909218824445026L;

	public ResourceNotFoundException(String name) {
		super(name);
	}

	public ResourceNotFoundException(String name, Throwable cause) {
		super(name, cause);
	}

}
