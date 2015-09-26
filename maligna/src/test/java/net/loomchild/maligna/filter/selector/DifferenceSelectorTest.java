package net.loomchild.maligna.filter.selector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;

import org.junit.Test;

/**
 * Represents {@link DifferenceSelector} unit test.
 * @author loomchild
 */
public class DifferenceSelectorTest {

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

		Filter filter = new DifferenceSelector(rightAlignmentList);
		List<Alignment> resultAlignmentList = filter.apply(leftAlignmentList);

		assertEquals(1, resultAlignmentList.size());
		assertEquals(leftAlignmentList.get(1), resultAlignmentList.get(0));

	}
}
