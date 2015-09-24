package net.sourceforge.align.filter.modifier.modify.merge;

import java.util.Iterator;
import java.util.List;

/**
 * Represents and algorithm merging a list of segments into one segment by
 * concatenating them. It can insert given separator string between segments. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class SeparatorMergeAlgorithm extends MergeAlgorithm {

	/**
	 * Default segment separator.
	 */
	public static final String DEFAULT_SEPARATOR = "";

	private String separator;
	
	/**
	 * Creates merge algorithm.
	 * @param separator separator
	 */
	public SeparatorMergeAlgorithm(String separator) {
		this.separator = separator;
	}

	/**
	 * Creates merge algoruthm with {@link #DEFAULT_SEPARATOR}.
	 */
	public SeparatorMergeAlgorithm() {
		this(DEFAULT_SEPARATOR);
	}
	
	/**
	 * Merges list of segments into one segment by concatenating them and
	 * inserting separator between.
	 * @param segmentList input segment list
	 * @return merged segment
	 */
	public String merge(List<String> segmentList) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> i = segmentList.iterator();
		while (i.hasNext()) {
			String segment = i.next();
			builder.append(segment);
			if (i.hasNext()) {
				builder.append(separator);
			}
		}
		return builder.toString();
	}

}
