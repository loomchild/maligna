package net.loomchild.maligna.model.vocabulary;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Represents a vocabulary mapping words to identifiers.
 *
 * @author Jarek Lipski (loomchild)
 */
public class Vocabulary {
	
	public static final int NULL_WID = 0;
	
	public static final String NULL_WORD = "{NULL}";
	
	private List<String> wordArray;
	
	private Map<String, Integer> wordMap;
	
	/**
	 * Creates vocabulary containing just special null word {@link #NULL_WORD}
	 * with {@link #NULL_WID} identifier.
	 */
	public Vocabulary() {
		this.wordArray = new ArrayList<String>();
		this.wordMap = new HashMap<String, Integer>();
		putWord(NULL_WORD);
	}
	
	/**
	 * Creates a vocabulary containing special null word and inserts all
	 * words from the collections to it, generating word ids.
	 * @param wordCollection
	 */
	public Vocabulary(Collection<String> wordCollection) {
		this();
		for (String word : wordCollection) {
			putWord(word);
		}
	}
	
	/**
	 * Finds an identifier for a given word. If the word does not exist 
	 * in the vocabulary returns null.
	 * 
	 * @param word word
	 * @return word id or null
	 */
	public Integer getWid(String word) {
		Integer wid = wordMap.get(word);
		return wid;
	}
	
	/**
	 * Convenience method that retrieves all the words from the list using
	 * {@link #getWid(String)} and returns a list of identifiers. If some 
	 * words cannot be found in the vocabulary then corresponding identifiers
	 * on the resulting list will be null.
	 * 
	 * @param wordList list of words
	 * @return list of word ids
	 */
	public List<Integer> getWidList(List<String> wordList) {
		List<Integer> widList = new ArrayList<Integer>();
		for (String word : wordList) {
			widList.add(getWid(word));
		}
		return widList;
	}

	/**
	 * Checks if given word id is present in the vocabulary.
	 * @param wid word id
	 * @return true if given word id is present in the vocabulary
	 */
	public boolean containsWid(int wid) {
		return wid >= 0 && wid < wordArray.size();
	}

	/**
	 * Returns a word by given identifier. If the word is not present in the
	 * vocabulary returns null.
	 * 
	 * @param wid word id
	 * @return word or null
	 */
	public String getWord(int wid) {
		if (wid < wordArray.size()) {
			return wordArray.get(wid); 
		} else {
			return null;
		}
	}

	/**
	 * Checks if given word is present in the vocabulary.
	 * @param word word
	 * @return true if given word is present in the vocabulary
	 */
	public boolean containsWord(String word) {
		return wordMap.containsKey(word);
	}
	
	/**
	 * Adds a new word to the vocabulary if it is not present, otherwise
	 * does nothing.
	 * 
	 * @param word word
	 * @return returns word id
	 */
	public int putWord(String word) {
		Integer wid = wordMap.get(word);
		if (wid == null) {
			wid = wordArray.size();
			wordArray.add(word);
			wordMap.put(word, wid);
		}
		return wid;
	}
	
	/**
	 * Convenience method to add words from given list to the vocabulary using
	 * {@link #putWord(String)}.
	 * @param wordList word list
	 */
	public void putWordList(List<String> wordList) {
		for (String word : wordList) {
			putWord(word);
		}
	}
	
	/**
	 * @return number of words in the vocabulary
	 */
	public int getWordCount() {
		return wordArray.size() - 1;
	}
	
	/**
	 * Formats the vocabulary to given writer in plaintext format. 
	 * Used for logging purposes and for storing models.
	 * @param writer writer
	 */
	public void format(Writer writer) {
		PrintWriter printWriter = new PrintWriter(writer, true);
		for (int i = 0; i < wordArray.size(); ++i) {
			printWriter.println(i + "\t" + wordArray.get(i));
		}
	}

	
}
