package regions;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ParentRegion extends Region {

	public ParentRegion(ParentRegion parent) {
		super(parent);
		this.children = new LinkedList<Region>();
	}
	
	public String getContent() {
		Iterator<Region> i = getChildren().iterator();
		if (i.hasNext()) {
			StringBuilder content = new StringBuilder();
			content.append(i.next().getContent());
			while (i.hasNext()) {
				content.append(" " + i.next().getContent());
			}
			return content.toString();
		} else {
			return "";
		}
	}

	public int getLength() {
		int length = 0;
		for (Region child : getChildren()) {
			length += child.getLength();
		}
		return length;
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
	
	private List<Region> children;
	
}
