package net.sourceforge.align.category.list;

import java.util.AbstractList;

import net.sourceforge.align.category.Category;

/**
 * Represents list of categories described in Moore's paper.
 * 
 * @author loomchild
 */
public class MooreCategoryList extends AbstractList<Category> {
	
	private static final Category[] ARRAY = {
		new Category(1, 1, 0.94f),  
		new Category(1, 0, 0.01f), new Category(0, 1, 0.01f),	
		new Category(2, 1, 0.02f), new Category(1, 2, 0.02f),
	};

	public Category get(int index) {
		return ARRAY[index];
	}

	public int size() {
		return ARRAY.length;
	}

}
