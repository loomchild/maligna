package net.loomchild.maligna.calculator.length.counter;

import net.loomchild.maligna.filter.modifier.modify.split.SplitAlgorithm;
import net.loomchild.maligna.model.vocabulary.VocabularyUtil;

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
		this(VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM);
	}

	/**
	 * Calculates length of a segment in words.
	 */
	public int calculateLength(String segment) {
		return splitAlgorithm.split(segment).size();
	}

}
