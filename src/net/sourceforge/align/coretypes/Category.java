package net.sourceforge.align.coretypes;

/**
 * Represents alignment category - for example one source segment to 
 * one target segment (1-1), two source segments to zero target segments (2-0),
 * etc.
 * 
 * Immutable - cannot be modified once it is created.
 * 
 * @author Jarek Lipski (loomchild)
 */
public class Category {
	
	private int sourceSegmentCount;

	private int targetSegmentCount;

	public Category(int sourceSegmentCount, int targetSegmentCount) {
		this.sourceSegmentCount = sourceSegmentCount;
		this.targetSegmentCount = targetSegmentCount;
	}
	
	/**
	 * @return Returns count of source segments in this category.
	 */
	public int getSourceSegmentCount() {
		return sourceSegmentCount;
	}
	
	/**
	 * @return Returns count of target segments in this category.
	 */
	public int getTargetSegmentCount() {
		return targetSegmentCount;
	}
	
	public String toString() {
		return "(" + sourceSegmentCount + "-" + targetSegmentCount + ")";
	}

}
