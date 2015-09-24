package net.sourceforge.align.calculator.length.counter;

import static net.sourceforge.align.model.vocabulary.VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM;
import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithm;
import net.sourceforge.align.model.vocabulary.VocabularyUtil;

/**
 * Responsible for calculating length of segment in words. Uses given word
 * splitting algorithm or {@link VocabularyUtil#DEFAULT_TOKENIZE_ALGORITHM}.
 * 
 * @author loomchild
 */
public class SplitCounter implements Counter {
	
	public SplitAlgorithm splitAlgorithm;
	
	/**
	 * Create calculator using given word split algoprithm.
	 * @param splitAlgorithm
	 */
	public SplitCounter(SplitAlgorithm splitAlgorithm) {
		this.splitAlgorithm = splitAlgorithm;
	}

	/**
	 * Creates calculator using 
	 * {@link VocabularyUtil#DEFAULT_TOKENIZE_ALGORITHM}.
	 */
	public SplitCounter() {
		this(DEFAULT_TOKENIZE_ALGORITHM);
	}

	/**
	 * Calculates length of a segment in words.
	 */
	public int calculateLength(String segment) {
		return splitAlgorithm.split(segment).size();
	}

}
