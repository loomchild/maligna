package regions;

import java.util.List;

/**
 * Represents text region in document. Contains InternalRegions 
 * or is ChildRegion.
 * @author loomchild
 */
public abstract class Region {
	
	public Region(ParentRegion parent) {
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}
	
	/**
	 * @return Returns region's parent (null if region is root)
	 */
	public ParentRegion getParent() {
		return parent;
	}
	
	/**
	 * @return Returns const list of region children.
	 */
	public abstract List<Region> getChildren();

	/**
	 * @return Returns region's content.
	 */
	public abstract String getContent();
	
	/**
	 * *@return Returns content lenght (== getContent.lenght()), but more efficient.
	 */
	public abstract int getLength();
	
	private ParentRegion parent;
	
}
