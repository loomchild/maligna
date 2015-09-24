package net.sourceforge.align.comparator;

import java.util.List;

import net.sourceforge.align.Alignment;


/**
 * Reprezentuje wynik porównania dopasowań. Odpowiada za przechowywanie 
 * różnic w dopasowaniach.
 *
 * @author Jarek Lipski (loomchild)
 */
public class Diff {

	private List<Alignment> leftOnlyAlignmentList;

	private List<Alignment> rightOnlyAlignmentList;

	public Diff(List<Alignment> leftOnlyAlignmentList, 
			List<Alignment> rightOnlyAlignmentList) {
		this.leftOnlyAlignmentList = leftOnlyAlignmentList;
		this.rightOnlyAlignmentList = rightOnlyAlignmentList;
	}

	public List<Alignment> getLeftOnlyAlignmentList() {
		return leftOnlyAlignmentList;
	}

	public List<Alignment> getRightOnlyAlignmentList() {
		return rightOnlyAlignmentList;
	}
	
}
