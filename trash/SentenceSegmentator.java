package segmentators;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import regions.Region;

public class SentenceSegmentator extends Segamentator {
	
	protected List<String> split(String text) {
		int start, end;
		ArrayList<String> sentenceList = new ArrayList<String>();
		//Uses default locale, change...
		BreakIterator breakIterator = BreakIterator.getSentenceInstance();
		breakIterator.setText(text);
		start = breakIterator.first();
		for (end = breakIterator.next(); end != BreakIterator.DONE; 
				start = end, end = breakIterator.next()){
			sentenceList.add(text.substring(start, end));
		}
		return sentenceList;	
	}

}
