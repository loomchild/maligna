package net.loomchild.maligna.comparator;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;


/**
 * Represents the result of alignment compare. 
 * Contains common alignment list and non matching, corresponding left and right
 * alignment list groups. Number of elements on leftList and rightList is equal.
 *
 * @author Jarek Lipski (loomchild)
 */
public class Diff {

	private List<Alignment> commonList;
	
	private List<List<Alignment>> leftGroupList;

	private List<List<Alignment>> rightGroupList;
	
	private List<Alignment> leftList;
	
	private List<Alignment> rightList;

	public Diff(List<Alignment> commonList, List<List<Alignment>> leftGroupList,
			List<List<Alignment>> rightGroupList) {
		
		if (leftGroupList.size() != rightGroupList.size()) {
			throw new IllegalArgumentException(
					"Left and right list lengths must be equal.");
		}
		
		this.commonList = commonList;
		this.leftGroupList = leftGroupList;
		this.rightGroupList = rightGroupList;
		
		this.leftList = mergeGroups(leftGroupList);
		this.rightList = mergeGroups(rightGroupList);
		
	}
	
	private List<Alignment> mergeGroups(List<List<Alignment>> groupList) {
		List<Alignment> list = new ArrayList<Alignment>();
		for (List<Alignment> alignmentList : groupList) {
			list.addAll(alignmentList);
		}	
		return list;
	}

	/**
	 * @return Returns list common to left and right alignments.
	 */
	public List<Alignment> getCommonList() {
		return commonList;
	}

	/**
	 * @return Returns list of alignment groups occurring only in left list.
	 */
	public List<List<Alignment>> getLeftGroupList() {
		return leftGroupList;
	}

	/**
	 * @return Returns list of alignment groups occurring only in right list.
	 */
	public List<List<Alignment>> getRightGroupList() {
		return rightGroupList;
	}

	/**
	 * @return Returns list of alignments occurring only in left list. 
	 */
	public List<Alignment> getLeftList() {
		return leftList;
	}

	/**
	 * @return Returns list of alignments occurring only in right list. 
	 */
	public List<Alignment> getRightList() {
		return rightList;
	}
	
}
