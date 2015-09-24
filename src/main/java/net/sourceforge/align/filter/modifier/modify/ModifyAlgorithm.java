package net.sourceforge.align.filter.modifier.modify;

import java.util.List;

import net.sourceforge.align.filter.modifier.Modifier;

/**
 * Represents modify algorithm used by {@link Modifier}.
 * @author loomchild
 */
public interface ModifyAlgorithm {

	/**
	 * Returns segment list containing modified input segment list. 
	 * Modification can include merging or splitting of elements (resulting 
	 * list can have different size than input list). 
	 * Does not know distinguish between source and target segments (does not
	 * know which ones are processed).
	 * @param segmentList source segment list
	 * @return modified segment list
	 */
	public List<String> modify(List<String> segmentList);
	
}
