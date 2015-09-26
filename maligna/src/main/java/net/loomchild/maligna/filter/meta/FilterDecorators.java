package net.loomchild.maligna.filter.meta;

import net.loomchild.maligna.filter.Filter;

/**
 * Filter decorator helper methods.
 * @author loomchild
 */
public class FilterDecorators {

	/**
	 * Decorate given filter with standard decorators. Currently uses only
	 * {@link IgnoreInfiniteProbabilityAlignmentsFilterDecorator}.
	 * 
	 * @param filter
	 * @return decorated filter
	 */
	public static Filter decorate(Filter filter) {
		filter = new IgnoreInfiniteProbabilityAlignmentsFilterDecorator(filter);
		return filter;
	}
	
}
