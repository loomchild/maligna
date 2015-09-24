package segmentators;

import java.util.List;

import regions.Region;

public abstract class Segamentator {

	public void split(Region region) {
		String content = region.getContent();
		List<String> childContentList = split(content);
		for(String childContent : childContentList) {
			Region child = new Region(region, childContent);
			region.addChild(child);
		}
	}
	
	protected abstract List<String> split(String text);
	
}
