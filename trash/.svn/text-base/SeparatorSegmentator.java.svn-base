package segmentators;

import java.util.ArrayList;
import java.util.List;

import regions.Region;

public class SeparatorSegmentator extends Segamentator {

	public SeparatorSegmentator(String separator) {
		this.separator = separator;
	}
	
	protected List<String> split(String text) {
		String[] childContentList = text.split(separator);
		List<String> list = new ArrayList<String>(childContentList.length);
		for(String childContent : childContentList) {
			list.add(childContent);
		}
		return list;
	}
	
	private String separator;

}
