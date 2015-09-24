package net.sourceforge.align.filter.selector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;

import org.junit.Test;

/**
 * Represents {@link IntersectionSelector} unit test.
 * @author loomchild
 */
public class IntersectionSelectorTest {

	@Test
	public void testSimple() {

		List<Alignment> leftAlignmentList = new ArrayList<Alignment>();
		leftAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		leftAlignmentList.add(new Alignment(new String[]{"b"}, new String[]{}));
		leftAlignmentList.add(new Alignment(new String[]{"c"}, new String[]{"3"}));

		List<Alignment> rightAlignmentList = new ArrayList<Alignment>();
		rightAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		rightAlignmentList.add(new Alignment(new String[]{"b"}, new String[]{"2"}));
		rightAlignmentList.add(new Alignment(new String[]{"c"}, new String[]{"3"}));

		Filter filter = new IntersectionSelector(rightAlignmentList);
		List<Alignment> resultAlignmentList = filter.apply(leftAlignmentList);

		assertEquals(2, resultAlignmentList.size());
		assertEquals(rightAlignmentList.get(0), resultAlignmentList.get(0));
		assertEquals(rightAlignmentList.get(2), resultAlignmentList.get(1));

	}
}
