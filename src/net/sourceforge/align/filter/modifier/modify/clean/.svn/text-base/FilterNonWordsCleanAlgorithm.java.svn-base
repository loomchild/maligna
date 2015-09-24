package net.sourceforge.align.filter.modifier.modify.clean;


/**
 * Represents clean algorithm removing all segments that do not contain any
 * letters.
 * @author loomchild
 */
public class FilterNonWordsCleanAlgorithm extends CleanAlgorithm {
	
	public String clean(String segment) {
		for (int i = 0; i < segment.length(); ++i) {
			if (Character.isLetter(segment.charAt(i))) {
				return segment;
			}
		}
		return null;
	}

}
