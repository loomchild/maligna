package regions;

import java.util.Collections;
import java.util.List;


/**
 * Represents leaf region in document (is plain text), has no children 
 * and no name. .
 * @author loomchild
 */
public class ChildRegion extends Region {

	public ChildRegion(ParentRegion parent, String content) {
		super(parent);
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public int getLength() {
		return content.length();
	}

	public List<Region> getChildren() {
		return Collections.emptyList();
	}
	
	private String content;

}
