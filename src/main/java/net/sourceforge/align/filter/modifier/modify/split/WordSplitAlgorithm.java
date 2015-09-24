package net.sourceforge.align.filter.modifier.modify.split;

import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isWhitespace;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple split algorithm separating input segment into words. 
 * Word boundaries are assumed to be everything that is not a character or
 * digit. Whitespace characters are removed from the output.
 *  
 * @author loomchild
 */
public class WordSplitAlgorithm extends SplitAlgorithm {

	public List<String> split(String string) {
		List<String> wordList = new ArrayList<String>();
		int start = 0;
		for (int end = 0; end < string.length(); ++end) {
			char ch = string.charAt(end);
			if (!isLetterOrDigit(ch)) {
				if ((end - start) > 0) {
					String word = string.substring(start, end);
					wordList.add(word);
				}
				if (!isWhitespace(ch)) {
					String word = string.substring(end, end + 1);
					wordList.add(word);
				}
				start = end + 1;
			}
		}
		if (start < string.length()) {
			String word = string.substring(start);
			wordList.add(word);
		}
		return wordList;
	}
	
}
