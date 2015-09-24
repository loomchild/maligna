package net.sourceforge.align.comparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.align.Alignment;

/**
 * Reprezentuje porównywacz sekwencyjny.
 *
 * @author Jarek Lipski (loomchild)
 */
public class SequentialComparator implements Comparator {

	/**
	 * Porównuje listy dopasowań. Zeby dopasowania były uznane za identyczne 
	 * muszą być takie same i występować w takim samym porządku.
	 * Nie działa gdy w tekście występują powtórzone dopasowania ponieważ
	 * może wtedy nie znaleźć najdłuższego możliwego dopasowania. 
	 * @param leftAlignmentList Pierwsza lista dopasowań.
	 * @param rightAlignmentList Druga lista dopasowań.
	 * @return Zwraca listę różnic.
	 */
	public Diff compare(List<Alignment> leftAlignmentList,
			List<Alignment> rightAlignmentList) {
		List<Alignment> leftOnlyAlignmentList = new ArrayList<Alignment>();
		List<Alignment> rightOnlyAlignmentList = new ArrayList<Alignment>();
		int startIndex = 0;
		for (Alignment leftAlignment : leftAlignmentList) {
			int index = startIndex;
			Iterator<Alignment> rightIterator = 
				rightAlignmentList.listIterator(index);
			while(rightIterator.hasNext()) {
				Alignment rightAlignment = rightIterator.next();
				if (leftAlignment.equals(rightAlignment)) {
					rightOnlyAlignmentList.addAll(
							rightAlignmentList.subList(startIndex, index));
					startIndex = index + 1;
					break;
				} else {
					++index;
				}
			}
			//Gdy nie znaleziono identycznego dopasowania
			if (startIndex <= index) {
				leftOnlyAlignmentList.add(leftAlignment);
			}
		}
		if (startIndex < rightAlignmentList.size()) {
			rightOnlyAlignmentList.addAll(
				rightAlignmentList.subList(startIndex, 
						rightAlignmentList.size()));
		}
		return new Diff(leftOnlyAlignmentList, rightOnlyAlignmentList);
	}
	
}
