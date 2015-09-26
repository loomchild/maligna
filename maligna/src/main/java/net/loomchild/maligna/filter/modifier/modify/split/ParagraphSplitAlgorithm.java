package net.loomchild.maligna.filter.modifier.modify.split;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents simple split algorithm dividing input segment into paragraphs, 
 * ending with end-of-line character ('\n').
 * TODO: merge with WordSplitAlgorithm
 * @author Jarek Lipski (loomchild)
 */
public class ParagraphSplitAlgorithm extends SplitAlgorithm {

	public List<String> split(String string) {
		List<String> paragraphList = new ArrayList<String>();
		int start = 0;
		for (int end = 0; end < string.length(); ++end) {
			if (string.charAt(end) == '\n') {
				String paragraph = string.substring(start, end);
				paragraphList.add(paragraph);
				start = end + 1;
			}
		}
		if (start < string.length()) {
			String paragraph = string.substring(start);
			paragraphList.add(paragraph);
		}
		return paragraphList;
	}

}
