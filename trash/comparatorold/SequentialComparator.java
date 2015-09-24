package align.comparator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import align.Alignment;
import align.merger.Merger;
import align.merger.SeparatorMerger;

/**
 * Reprezentuje porównywacz sekwencyjny. Odpowiada za wyszukanie różnic w 
 * dopasowaniu ciągłym.
 *
 * @author Jarek Lipski (loomchild)
 */
public class SequentialComparator implements Comparator {

	private Merger merger;
	
	public SequentialComparator() {
		this.merger = new SeparatorMerger();
	}
	
	/**
	 * Porównuje listy dopasowań. Każde dopasowanie powinno reprezentować
	 * ciągłe obszary w tekstach źródłowym i docelowym. Porównywane teksty 
	 * muszą być identyczne i najlepiej żebyt były podzielone tym samym 
	 * splitterem. 
	 * @param leftAlignmentList
	 * @param rightAlignmentList
	 * @return
	 */
	public CompareResult compare(List<Alignment> leftAlignmentList,
			List<Alignment> rightAlignmentList) {
		List<Alignment> leftOnlyAlignmentList = new LinkedList<Alignment>();
		List<Alignment> rightOnlyAlignmentList = new LinkedList<Alignment>();
		Iterator<Alignment> leftIterator = leftAlignmentList.iterator();
		Iterator<Alignment> rightIterator = rightAlignmentList.iterator();
		int leftSourceCharNr = 0;
		int leftTargetCharNr = 0;
		int rightSourceCharNr = 0;
		int rightTargetCharNr = 0;
		Alignment leftAlignment = null;
		Alignment rightAlignment = null;
		String leftMergedSourceSegment = null;
		String leftMergedTargetSegment = null;
		String rightMergedSourceSegment = null;
		String rightMergedTargetSegment = null;
		
		while(leftIterator.hasNext() && rightIterator.hasNext()) {
			//Przechodzi po obu tylko jeśli source i target są równe.
			//W przeciwnym wypadku przechodzi po tym co ma mniejsze source 
			//a gdy są równe to target.
			boolean moveLeft = (leftSourceCharNr < rightSourceCharNr) || 
					(leftSourceCharNr == rightSourceCharNr && 
					leftTargetCharNr <= rightTargetCharNr);
			boolean moveRight = (leftSourceCharNr > rightSourceCharNr) || 
					(leftSourceCharNr == rightSourceCharNr && 
					leftTargetCharNr >= rightTargetCharNr);
			if (moveLeft) {
				leftAlignment = leftIterator.next();
				leftMergedSourceSegment = 
						merger.merge(leftAlignment.getSourceSegmentList());
				leftMergedTargetSegment = 
						merger.merge(leftAlignment.getTargetSegmentList());
				leftSourceCharNr += leftMergedSourceSegment.length();
				leftTargetCharNr += leftMergedTargetSegment.length();
			} 
			if (moveRight) {
				rightAlignment = rightIterator.next();
				rightMergedSourceSegment = 
						merger.merge(rightAlignment.getSourceSegmentList());
				rightMergedTargetSegment = 
						merger.merge(rightAlignment.getTargetSegmentList());
				rightSourceCharNr += rightMergedSourceSegment.length();
				rightTargetCharNr += rightMergedTargetSegment.length();
			} 
			if ((leftMergedSourceSegment.equals(rightMergedSourceSegment)) &&
					(leftMergedTargetSegment.equals(rightMergedTargetSegment))) {
				rightSourceCharNr = leftSourceCharNr;
				rightTargetCharNr = leftTargetCharNr;
			} else {
				if (moveLeft) {
					leftOnlyAlignmentList.add(leftAlignment);
				}
				if (moveRight) {
					rightOnlyAlignmentList.add(rightAlignment);
				}
			}
		}
		if ((leftSourceCharNr != rightSourceCharNr) || 
				(leftTargetCharNr != rightTargetCharNr)){
			throw new IllegalArgumentException("Dopasowania nie są " +
					"identycznej długości");
		}
		while(leftIterator.hasNext()) {
			leftAlignment = leftIterator.next();
			leftOnlyAlignmentList.add(leftAlignment);
		}
		while(rightIterator.hasNext()) {
			rightAlignment = rightIterator.next();
			rightOnlyAlignmentList.add(rightAlignment);
		}
		
		return new CompareResult(leftOnlyAlignmentList, rightOnlyAlignmentList);
	}
	
}
