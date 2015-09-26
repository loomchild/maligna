package net.loomchild.maligna.filter.modifier.modify.clean;


/**
 * Represents clean algorithm trimming all the segments (removing leading and
 * trailing whitespace). 
 * It also omits segments that are empty after trimming.
 * 
 * @author loomchild
 */
public class TrimCleanAlgorithm extends CleanAlgorithm {
	
	public String clean(String segment) {
		String newSegment = segment.trim();
		if (newSegment.length() == 0) {
			newSegment = null;
		}
		return newSegment;
	}

}
