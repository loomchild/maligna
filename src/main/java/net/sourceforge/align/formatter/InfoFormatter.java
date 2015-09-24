package net.sourceforge.align.formatter;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.coretypes.Category;

/**
 * Represents formatter displaying info and statistics of the whole alignment.
 * Stores the result using {@link Writer} defined in constructor.
 * 
 * The statistics include total number of alignments and number of alignments 
 * per category.
 *
 * @author Jarek Lipski (loomchild)
 */
public class InfoFormatter implements Formatter {
	
	private PrintWriter writer;
	
	/**
	 * Creates formatter.
	 * @param writer writer which will be used to store or display the info.
	 */
	public InfoFormatter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	/**
	 * Formats the result using configured writer. 
	 */
	public void format(List<Alignment> alignmentList) {		
		int alignmentCount = alignmentList.size();
		
		Map<Category, Integer> categoryMap = 
			new TreeMap<Category, Integer>(new CategoryComparator());
		for (Alignment alignment : alignmentList) {
			Category category = alignment.getCategory();
			Integer occurenceCount = categoryMap.get(category);
			if (occurenceCount == null) {
				occurenceCount = 1;
			} else {
				++occurenceCount;
			}
			categoryMap.put(category, occurenceCount);
		}
		
		for (Map.Entry<Category, Integer> entry : categoryMap.entrySet()) {
			writer.println(entry.getKey().toString() + "\t" + entry.getValue());
		}
		writer.println("Total\t" + alignmentCount);
		writer.flush();
	}

	/**
	 * Orders categories in the following ascending order. Example:
	 * (0-1), (1-0), (1-1), (1-2), (2-1), (2-2), ...
	 * 
	 * TODO: write test for this class cause there was a problem before
	 * that (0-1) and (1-0) were equal so the order was undetermined.
	 * 
	 * @author loomchild
	 */
	private static class CategoryComparator 
			implements Comparator<Category>, Serializable {

		private static final long serialVersionUID = 2369430623799175503L;

		/**
		 * Returns negative [positive] number if minimum number of segments 
		 * in left category is lesser [greater] than minimum number of segments 
		 * in right category.
		 * 
		 * If the numbers are equal then returns negative [positive] number
		 * if maximum number of segments in left category is lesser [greater]
		 * than maximum numbers in right category.
		 * 
		 * If the numbers are still equal then returns negative [positive]
		 * number if number of source segments in left category is lesser 
		 * [greater] than number of source segments in right category. 
		 */
		public int compare(Category leftCategory, Category rightCategory) {
			int result = getMinimumSegmentCount(leftCategory) -
					getMinimumSegmentCount(rightCategory);
			if (result == 0) {
				result = getMaximumSegmentCount(leftCategory) -
						getMaximumSegmentCount(rightCategory);
				if (result == 0) {
					result = leftCategory.getSourceSegmentCount() 
						- rightCategory.getSourceSegmentCount();
				}
			}
			return result;
		}
		
		/**
		 * Returns minimum count of source and target segments in a category.
		 * @param category
		 * @return
		 */
		private int getMinimumSegmentCount(Category category) {
			return Math.min(category.getSourceSegmentCount(), 
					category.getTargetSegmentCount());
		}

		/**
		 * Returns maximum count of source and target segments in a category.
		 * @param category
		 * @return
		 */
		private int getMaximumSegmentCount(Category category) {
			return Math.max(category.getSourceSegmentCount(), 
					category.getTargetSegmentCount());
		}

	}
	
}
