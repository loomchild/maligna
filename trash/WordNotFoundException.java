package align.aligner.aligners.moore.models;

import loomchild.util.exceptions.LogicException;

public class WordNotFoundException extends LogicException {

	private static final long serialVersionUID = 1474443021847245849L;

	public WordNotFoundException(String word) {
		super("No such word: " + word);
	}

}
