package net.loomchild.maligna.filter.meta;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;

/**
 * Represents a pipeline consisting of many filters but behaving as single
 * filter.
 * Transforms the input by executing all filters in sequence. 
 * Basically implements composite design pattern.
 *
 * @author Jarek Lipski (loomchild)
 */
public class CompositeFilter implements Filter {
	
	private List<Filter> filterList;
	
	/**
	 * Creates composite filter.
	 * @param filterList filter list; filters will be applied in the same order
	 * 		as they appear on this list 
	 */
	public CompositeFilter(List<Filter> filterList) {
		this.filterList = filterList;
	}
    
	/**
	 * Applies the composite filter by executing all the configured filters
	 * is sequence, where output of previous filter is input of the next
	 * filter.  
	 * @param alignmentList input alignment list
	 * @return transformed alignment list
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		for (Filter filter : filterList) {
			alignmentList = filter.apply(alignmentList);
		}
		return alignmentList;
	}
	
}
