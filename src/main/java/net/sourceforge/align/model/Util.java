package net.sourceforge.align.model;

import java.util.ArrayList;
import java.util.List;

public class Util {

	/**
	 * Converts two dimensional integer (representing word ids) array into a 
	 * list of lists. Used for testing purposes.
	 * 
	 * @param widArray two dimensional interger array
	 * @return list of lists.
	 */
	public static List< List<Integer> > createWidList(int[][] widArray) {
		List< List<Integer> > widList = new ArrayList< List<Integer> >();
		for (int[] widArrayGroup : widArray) {
			List<Integer> widListGroup = new ArrayList<Integer>();
			for (int wid : widArrayGroup) {
				widListGroup.add(wid);
			}
			widList.add(widListGroup);
		}
		return widList;
	}
	
	
}
