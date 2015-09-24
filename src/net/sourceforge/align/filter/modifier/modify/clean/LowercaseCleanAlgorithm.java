package net.sourceforge.align.filter.modifier.modify.clean;


/**
 * Represents clean algorithm changing input segment to lower case.
 * @author loomchild
 */
public class LowercaseCleanAlgorithm extends CleanAlgorithm {
	
	public String clean(String segment) {
		String newSegment = segment.toLowerCase();
		return newSegment;
	}

}
