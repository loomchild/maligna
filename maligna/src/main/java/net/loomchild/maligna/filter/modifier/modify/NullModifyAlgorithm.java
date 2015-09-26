package net.loomchild.maligna.filter.modifier.modify;

import java.util.List;

/**
 * Represents modify algorithm that is not changing the input in any way.
 * Useful when we want to perform operation just on source or target 
 * segments and leave the other as it is. 
 * Null design pattern. 
 * @author loomchild
 */
public class NullModifyAlgorithm implements ModifyAlgorithm {

	/**
	 * @param segmentList source segment list
	 * @return unmodified source segment list
	 */
	public List<String> modify(List<String> segmentList) {
		return segmentList;
	}
	
}
