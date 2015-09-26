package net.loomchild.maligna.filter;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;


/**
 * <p>Represents alignment list filter (in a sense of UNIX filter).</p>
 * <p>Allows to perform any operation on alignment list (not only, like name
 * suggests, filter elements from it) - for example it can modify segment
 * contents, join or split alignments, etc.</p> 
 * <p>
 * Filter operation receives alignment list as a parameter and returns 
 * modified alignment list. Thanks to the fact that input and output has the 
 * same type filters can be connected together creating the operation pipeline.
 * </p>
 *
 * @author Jarek Lipski (loomchild)
 */
public interface Filter {

	/**
	 * Performs any transformation on alignment list.
	 * @param alignmentList input alignment list
	 * @return output alignment list
	 */
	public List<Alignment> apply(List<Alignment> alignmentList);

}
