package regions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Region {

	public Region(Region parent, String content) {
		this.parent = parent;
		this.content = content;
		this.children = new LinkedList<Region>();
	}

	public Region getParent() {
		return parent;
	}
	
	public void setParent(Region parent) {
		this.parent = parent;
	}

	public String getContent() {
		return content;
	}

	public int getLength() {
		return content.length();
	}

	public List<Region> getChildren() {
		return Collections.unmodifiableList(children);
	}

	/**
	 * Adds child.
	 * @param child
	 */
	public void addChild(Region child) {
		children.add(child);
	}

	/**
	 * Removes child.
	 * @param child
	 * @return Returns true if child was removed.
	 */
	public boolean removeChild(Region child) {
		return children.remove(child);
	}
	
	/**
	 * Remove all children from this node.
	 */
	public void removeAllChildren() {
		children.clear();
	}
	
	private Region parent;
	
	private List<Region> children;

	//Performance optimization, addtional memory cost
	private String content;

}
