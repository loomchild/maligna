package net.sourceforge.align.comparator;

import java.util.List;

import net.sourceforge.align.Alignment;

/**
 * Reprezentuje porównywacz list dopasowań.
 *
 * @author Jarek Lipski (loomchild)
 */
public interface Comparator {

	public Diff compare(List<Alignment> leftAlignmentList,
			List<Alignment> rightAlignmentList);

}
