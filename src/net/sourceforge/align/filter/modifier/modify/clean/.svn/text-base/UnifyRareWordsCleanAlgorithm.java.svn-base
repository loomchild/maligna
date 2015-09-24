package net.sourceforge.align.filter.modifier.modify.clean;

import static net.sourceforge.align.model.vocabulary.VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM;

import java.util.List;

import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithm;
import net.sourceforge.align.model.vocabulary.Vocabulary;


/**
 * <p>Represents clean algorithm changing all words in segments that are not 
 * present in given vocabulary to given string (in other words replacing
 * all unknown words with predefined string).</p>
 * 
 * <p>To split segments into words uses given splitting algorithm or default,
 * simple one.</p>
 * 
 * @author loomchild
 */
public class UnifyRareWordsCleanAlgorithm extends CleanAlgorithm {
	
	public static final String DEFAULT_OTHER_WORD = "{OTHER}";

	private Vocabulary vocabulary;
	
	private SplitAlgorithm splitAlgorithm;

	private String otherWord;

	/**
	 * Creates algorithm.
	 * @param vocabulary vocabulary containing known words
	 * @param splitAlgorithm algorithm used to split segment into words
	 * @param otherWord string that will be used to replace unknown words
	 */
	public UnifyRareWordsCleanAlgorithm(Vocabulary vocabulary, 
			SplitAlgorithm splitAlgorithm, String otherWord) {
		this.vocabulary = vocabulary;
		this.splitAlgorithm = splitAlgorithm;
		this.otherWord = otherWord;
	}

	/**
	 * Creates algorithm with default tokenize algorithm 
	 * ({@link net.sourceforge.align.model.vocabulary.VocabularyUtil#DEFAULT_TOKENIZE_ALGORITHM}) 
	 * and default unknown word replacement ({@link #DEFAULT_OTHER_WORD}).
	 * @param vocabulary known words vocabulary
	 */
	public UnifyRareWordsCleanAlgorithm(Vocabulary vocabulary) {
		this(vocabulary, DEFAULT_TOKENIZE_ALGORITHM, DEFAULT_OTHER_WORD);
	}

	/**
	 * Cleans a segment. Result contains all words separated by a single space.
	 * @param segment
	 * @return cleaned segment
	 */
	public String clean(String segment) {
		List<String> wordList = splitAlgorithm.split(segment);
		StringBuilder resultSegment = new StringBuilder();
		for (String word : wordList) {
			if (resultSegment.length() > 0) {
				resultSegment.append(" ");
			}
			if (vocabulary.containsWord(word)) {
				resultSegment.append(word);
			} else {
				resultSegment.append(otherWord);
			}
		}
		return resultSegment.toString();
	}

}
