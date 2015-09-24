package regions;

import java.util.List;

/**
 * Represents text region in document. Contains InternalRegions 
 * or is ChildRegion.
 * @author loomchild
 */
public interface Region {
	
	/**
	 * @return Returns region's parent (null if region is root)
	 */
	ParentRegion getParent();
	
	/**
	 * @return Returns const list of region children.
	 */
	List<Region> getChildren();

	/**
	 * @return Returns region's content.
	 */
	String getContent();
	
	/**
	 * *@return Returns content lenght (== getContent.lenght()), but more efficient.
	 */
	int getLength();
	
}
