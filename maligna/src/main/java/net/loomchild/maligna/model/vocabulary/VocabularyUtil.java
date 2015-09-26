package net.loomchild.maligna.model.vocabulary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.modifier.modify.split.WordSplitAlgorithm;
import net.loomchild.maligna.filter.modifier.modify.split.FilterNonWordsSplitAlgorithmDecorator;
import net.loomchild.maligna.filter.modifier.modify.split.SplitAlgorithm;
import net.loomchild.maligna.model.ModelParseException;

public class VocabularyUtil {
	
	/**
	 * Parser vocabulary from given reader using simple text format. The
	 * vocabulary can be formatted using 
	 * {@link Vocabulary#format(java.io.Writer)} method.
	 * 
	 * @param reader input stream
	 * @return parsed vocabulary
	 */
	public static Vocabulary parse(Reader reader) {
		try {
			BufferedReader bufferedReader = new BufferedReader(reader);
			Vocabulary vocabulary = new Vocabulary();
			String line;
			int expectedWid = vocabulary.getWordCount() + 1;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split("\\s");
				if (parts.length == 2) {
					int wid = Integer.parseInt(parts[0]);
					String word = parts[1];
					if (wid != expectedWid) {
						throw new ModelParseException("Word ordering error");
					}
					vocabulary.putWord(word);
					++expectedWid;
				} else if (parts.length != 0) {
					throw new ModelParseException("Bad number of " +
							"line parts.");
				}
			}
			return vocabulary;
		} catch (NumberFormatException e) {
			throw new ModelParseException("Part format error", e);
		} catch (IOException e) {
			throw new ModelParseException("IO error", e);
		}
	}

	/**
	 * Default word tokenization algorithm.
	 */
	public static final SplitAlgorithm DEFAULT_TOKENIZE_ALGORITHM =
		new FilterNonWordsSplitAlgorithmDecorator(new WordSplitAlgorithm());

	/**
	 * For each alignment on input alignment list tokenizes all the segments
	 * into words, inserts the words into appropriate vocabulary 
	 * (source or target) and returns a list of lists of source and target 
	 * word ids grouped by alignment.
	 * 
	 * @param splitAlgorithm
	 * @param alignmentList
	 * @param sourceVocabulary
	 * @param targetVocabulary
	 * @param sourceWidList
	 * @param targetWidList
	 */
	public static void tokenize(SplitAlgorithm splitAlgorithm, 
			List<Alignment> alignmentList,
			Vocabulary sourceVocabulary, 
			Vocabulary targetVocabulary,
			List<List<Integer>> sourceWidList,
			List<List<Integer>> targetWidList) {

		for (Alignment alignment : alignmentList) {
			sourceWidList.add(tokenizePutGet(splitAlgorithm,
					alignment.getSourceSegmentList(), sourceVocabulary));
			targetWidList.add(tokenizePutGet(splitAlgorithm,
					alignment.getTargetSegmentList(), targetVocabulary));
		}
		
	}
	
	/**
	 * Tokenizes given segment list into words, puts them into given 
	 * vocabulary and returns their identifiers.
	 *  
	 * @param splitAlgorithm algorithm used to split segments into words
	 * @param segmentList list of input segments
	 * @param vocabulary vocabulary which will be expanded
	 * @return list of word ids
	 */
	private static List<Integer> tokenizePutGet(SplitAlgorithm splitAlgorithm, 
			List<String> segmentList, Vocabulary vocabulary) {
		List<String> wordList = splitAlgorithm.modify(segmentList);
		vocabulary.putWordList(wordList);
		List<Integer> widList = vocabulary.getWidList(wordList);
		return widList;
	}

	/**
	 * Tokenizes given segment list into words and retrieves their identifiers
	 * from given vocabulary.
	 *  
	 * @param splitAlgorithm algorithm used to split segments into words
	 * @param segmentList list of input segments
	 * @param vocabulary vocabulary from which word ids will be retrieved
	 * @return list of word ids
	 */
	public static List<Integer> tokenize(SplitAlgorithm splitAlgorithm, 
			List<String> segmentList, Vocabulary vocabulary) {
		List<String> wordList = splitAlgorithm.modify(segmentList);
		List<Integer> widList = vocabulary.getWidList(wordList);
		return widList;
	}
	
	
	public static final int DEFAULT_MAX_WORD_COUNT = 5000;
	
	public static final int DEFAULT_MIN_OCCURRENCE_COUNT = 2;

	/**
	 * Creates new vocabulary from given one with the most popular words
	 * from given word id list. Only the words that occur at least
	 * given minOccurrenceCount times will be preserved. Also resulting 
	 * vocabulary can not contain more words than given maxWordCount,
	 * so the less popular ones will be omitted.
	 * 
	 * @param widList word id list
	 * @param vocabulary initial vocabulary; it will not be modified
	 * @param maxWordCount maximum word count for the new vocabulary
	 * @param minOccurrenceCount minimum number of times the word must occur
	 * 		in reference word id list to be included in truncated vocabulary
	 * @return truncated vocabulary
	 */
	public static Vocabulary createTruncatedVocabulary(
			List<List<Integer>> widList, Vocabulary vocabulary, 
			int maxWordCount, int minOccurrenceCount) {
		
		// Occurrence threshold over which words are considered not rare.
		int occurrenceThreshold = minOccurrenceCount;
		
		// Number of words to leave with occurrence count equal to threshold.
		int occurrenceCountThreshold = Integer.MAX_VALUE;
		
		int[] occurrenceCountArray = new int[vocabulary.getWordCount() + 1];
		Arrays.fill(occurrenceCountArray, 0);
		// Ignore null word
		occurrenceCountArray[0] = -1;
		
		for (List<Integer> widSegment : widList) {
			for (int wid : widSegment) {
				++occurrenceCountArray[wid];
			}
		}
		
		// If there's too much words.
		if (vocabulary.getWordCount() > maxWordCount) {
			if (maxWordCount == 0) {
				occurrenceThreshold = Integer.MAX_VALUE;
			} else {
				int[] occurrenceCountArrayCopy = Arrays.copyOf(
						occurrenceCountArray, occurrenceCountArray.length); 
				Arrays.sort(occurrenceCountArrayCopy);
	
				// If the minOccurenceCount is too small to remove enough words.
				int index = occurrenceCountArrayCopy.length - maxWordCount;
				if (occurrenceCountArrayCopy[index] >= minOccurrenceCount) {
					occurrenceThreshold = occurrenceCountArrayCopy[index];
					++index;
					while ((index < occurrenceCountArrayCopy.length) && 
							(occurrenceCountArrayCopy[index] == 
								occurrenceThreshold)) {
						++index;
					}
					occurrenceCountThreshold = index - 
							(occurrenceCountArrayCopy.length - maxWordCount);
				}
			}
		}
		
		Vocabulary resultVocabulary = new Vocabulary();
		
		// Start from 1, null word will be in truncated vocabulary anyway.
		for (int wid = 1; wid < occurrenceCountArray.length; ++wid) {
			int occurenceCount = occurrenceCountArray[wid];
			String word = vocabulary.getWord(wid);
			if (occurenceCount > occurrenceThreshold) {
				resultVocabulary.putWord(word);
			} else if (occurenceCount == occurrenceThreshold
					&& occurrenceCountThreshold > 0) {
				resultVocabulary.putWord(word);
				--occurrenceCountThreshold;
			}
		}

		return resultVocabulary;	
	}

	/**
	 * 
	 * @param widList
	 * @param vocabulary
	 * @return
	 */
	/**
	 * Creates new vocabulary from given one with the most popular words
	 * from given word id list. Uses 
	 * {@link #createTruncatedVocabulary(List, Vocabulary, int, int)}
	 * with {@link #DEFAULT_MAX_WORD_COUNT} and 
	 * {@link #DEFAULT_MIN_OCCURRENCE_COUNT}.
	 * 
	 * @param widList word id list
	 * @param vocabulary initial vocabulary; it will not be modified
	 * @return truncated vocabulary
	 */
	public static Vocabulary createTruncatedVocabulary(
			List<List<Integer>> widList, Vocabulary vocabulary) {
		return createTruncatedVocabulary(widList, vocabulary, 
				DEFAULT_MAX_WORD_COUNT, DEFAULT_MIN_OCCURRENCE_COUNT);
	}
	
}
