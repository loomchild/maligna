package net.loomchild.maligna.coretypes;

import java.util.HashMap;
import java.util.Map;

import net.loomchild.maligna.util.Util;

/**
 * Responsible for storing default categories with scores (-ln probability) 
 * of occurrence of alignment of this category measured experimentally in some 
 * test corpus. 
 */
public class CategoryDefaults {

	public static final Map<Category, Float> BEST_CATEGORY_MAP = 
		createBestCategoryMap();
	
	private static Map<Category, Float> createBestCategoryMap() {
		Map<Category, Float> map = new HashMap<Category, Float>();
		
		map.put(new Category(1, 1), (float)Util.toScore(0.9));
		map.put(new Category(1, 0), (float)Util.toScore(0.005));
		map.put(new Category(0, 1), (float)Util.toScore(0.005));
		map.put(new Category(2, 1), (float)Util.toScore(0.045));
		map.put(new Category(1, 2), (float)Util.toScore(0.045));
		
		return map;
	}

	public static final Map<Category, Float> MOORE_CATEGORY_MAP = 
		createMooreCategoryMap();
	
	private static Map<Category, Float> createMooreCategoryMap() {
		Map<Category, Float> map = new HashMap<Category, Float>();
		
		map.put(new Category(1, 1), (float)Util.toScore(0.94));
		map.put(new Category(1, 0), (float)Util.toScore(0.01));
		map.put(new Category(0, 1), (float)Util.toScore(0.01));
		map.put(new Category(2, 1), (float)Util.toScore(0.02));
		map.put(new Category(1, 2), (float)Util.toScore(0.02));
	
		return map;
	}

}
