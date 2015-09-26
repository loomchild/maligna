package net.loomchild.maligna.comparator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

import org.junit.Assert;
import org.junit.Test;

/**
 * Represents {@link Comparator} test.
 * 
 * @author loomchild
 */
public class ComparatorTest {

	@Test
	public void testEmpty() {
		
		List<Alignment> leftAlignmentList = Collections.singletonList(
				new Alignment(new String[]{"a"}, new String[]{"b"}));
		List<Alignment> rightAlignmentList = Collections.emptyList();
		
		Diff diff = Comparator.compare(leftAlignmentList, rightAlignmentList);
		
		assertEquals(0, diff.getCommonList().size());

		assertEquals(1, diff.getLeftGroupList().size());
		assertEquals(1, diff.getLeftGroupList().get(0).size());

		assertEquals(1, diff.getRightGroupList().size());
		assertEquals(0, diff.getRightGroupList().get(0).size());
		
	}

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

		Diff diff = Comparator.compare(leftAlignmentList, rightAlignmentList);

		assertEquals(2, diff.getCommonList().size());
		Assert.assertEquals(rightAlignmentList.get(0), diff.getCommonList().get(0));
		Assert.assertEquals(rightAlignmentList.get(2), diff.getCommonList().get(1));

		assertEquals(1, diff.getLeftGroupList().size());
		assertEquals(leftAlignmentList.subList(1, 2), diff.getLeftGroupList().get(0));

		assertEquals(1, diff.getRightGroupList().size());
		assertEquals(rightAlignmentList.subList(1, 2), diff.getRightGroupList().get(0));
		
	}

	@Test
	public void testRepetitions() {

		List<Alignment> leftAlignmentList = new ArrayList<Alignment>();
		leftAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		leftAlignmentList.add(new Alignment(new String[]{"b"}, new String[]{"2"}));
		leftAlignmentList.add(new Alignment(new String[]{"c"}, new String[]{"3"}));

		List<Alignment> rightAlignmentList = new ArrayList<Alignment>();
		rightAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		rightAlignmentList.add(new Alignment(new String[]{"b"}, new String[]{"2"}));
		rightAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		rightAlignmentList.add(new Alignment(new String[]{"c"}, new String[]{"3"}));

		Diff diff = Comparator.compare(leftAlignmentList, rightAlignmentList);

		assertEquals(3, diff.getCommonList().size());
		Assert.assertEquals(rightAlignmentList.get(0), diff.getCommonList().get(0));
		Assert.assertEquals(rightAlignmentList.get(1), diff.getCommonList().get(1));
		Assert.assertEquals(rightAlignmentList.get(3), diff.getCommonList().get(2));
		
		assertEquals(1, diff.getLeftGroupList().size());
		assertEquals(0, diff.getLeftGroupList().get(0).size());
		
		assertEquals(1, diff.getRightGroupList().size());
		assertEquals(rightAlignmentList.subList(2, 3), diff.getRightGroupList().get(0));
		
	}

	@Test
	public void testInversions() {

		List<Alignment> leftAlignmentList = new ArrayList<Alignment>();
		leftAlignmentList.add(new Alignment(new String[]{"b"}, new String[]{"2"}));
		leftAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		leftAlignmentList.add(new Alignment(new String[]{"c"}, new String[]{"3"}));

		List<Alignment> rightAlignmentList = new ArrayList<Alignment>();
		rightAlignmentList.add(new Alignment(new String[]{"a"}, new String[]{"1"}));
		rightAlignmentList.add(new Alignment(new String[]{"b"}, new String[]{"2"}));
		rightAlignmentList.add(new Alignment(new String[]{"c"}, new String[]{"3"}));

		Diff diff = Comparator.compare(leftAlignmentList, rightAlignmentList);

		assertEquals(2, diff.getCommonList().size());
		Assert.assertEquals(rightAlignmentList.get(1), diff.getCommonList().get(0));
		Assert.assertEquals(rightAlignmentList.get(2), diff.getCommonList().get(1));
		
		assertEquals(2, diff.getLeftGroupList().size());
		assertEquals(0, diff.getLeftGroupList().get(0).size());
		assertEquals(leftAlignmentList.subList(1, 2), diff.getLeftGroupList().get(1));

		assertEquals(2, diff.getRightGroupList().size());
		assertEquals(rightAlignmentList.subList(0, 1), diff.getRightGroupList().get(0));
		assertEquals(0, diff.getRightGroupList().get(1).size());

	}
	
}
