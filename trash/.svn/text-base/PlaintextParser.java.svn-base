package parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import regions.Region;

public class PlaintextParser implements Parser {

	public PlaintextParser() {
		this.separatorList = new ArrayList<String>();
	}

	public Region parse(BufferedReader input) throws ParserException {
		StringBuilder content = new StringBuilder();
		try {
			String line = input.readLine();
			while(line != null) {
				content.append(line);
				line = input.readLine();
				if (line != null) {
					content.append('\n');
				}
			}
		} catch (IOException e) {
			throw new ParserException("IO error", e);
		}
		Region region = new Region(null, content.toString());
		split(region, 0);
		return region;
	}
	
	public List<String> getSeparatorList() {
		return separatorList;
	}
	
	public void addSeparator(String separator) {
		this.separatorList.add(separator);
	}

	public void removeSeparator(int nr) {
		this.separatorList.remove(nr);
	}

	/**
	 * Splits region using separator with given number from separatorList. 
	 * Recursively calls itself until separatorNr is greater than number of
	 * separators.
	 * @param region Region to be splitted, is modified!
	 * @param separatorNr separator number in separatorList.
	 */
	private void split(Region region, int separatorNr) {
		if (separatorNr < separatorList.size()) {
			String content = region.getContent();
			String separator = separatorList.get(separatorNr);
			String[] childContentList = content.split(separator);
			for(String childContent : childContentList) {
				Region child = new Region(region, childContent);
				split(child, separatorNr + 1);
				region.addChild(child);
			}
		}
	}

	private List<String> separatorList;
	
}
