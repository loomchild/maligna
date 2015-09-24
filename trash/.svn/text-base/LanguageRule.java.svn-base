package split.srx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import common.Region;

public class LanguageRule {

	
	public LanguageRule() {
		ruleList = new LinkedList<Rule>();
	}
	
	public List<String> split(String text, Settings settings) {
		// TODO zaimplemetnwoać brakujące sytuacje jeśli to konieczne
		assert settings.isSegmentSubflows() == false;
		assert settings.isIncludeIsolated() == true;
		// To byłoby bez sensu (chyba?)
		assert !(settings.isIncludeEnd() && !settings.isIncludeStart());
		
		List<String> segmentList = new LinkedList<String>();
		
		PriorityQueue<RuleMatcher> queue = 
				new PriorityQueue<RuleMatcher>(ruleList.size(), 
				RuleMatcherComparator.getInstance());
		
		for (Rule rule : ruleList) {
			RuleMatcher matcher = rule.getMatcher(text);
			matcher.find();
			queue.add(matcher);
		}

		int start = 0;
		int position = 0;
		while (queue.size() > 0) {
			RuleMatcher matcher = queue.peek();
			if (matcher.getRule().isBreaking()) {
				String segment = text.substring(start, 
						matcher.getBreakPosition());
				segmentList.add(segment);
				if (settings.isIncludeStart()) {
					start = matcher.getStartPosition();
				} else if (settings.isIncludeEnd()) {
					start = matcher.getBreakPosition();
				} else {
					start = matcher.getEndPosition();
				}
				position = start;
			} else {
				position = matcher.getBreakPosition() + 1;
			}
			while((queue.size() > 0) && (matcher.getBreakPosition() 
					< position)) {
				queue.remove();
				matcher.find(position);
				if (!matcher.hitEnd()) {
					queue.add(matcher);
				}
				matcher = queue.peek();
			}
		}
		String segment = text.substring(start);
		segmentList.add(segment);		
		return segmentList;
		
	}
	
	public void addRule(Rule rule) {
		ruleList.add(rule);
	}
	
	private List<Rule> ruleList;
	
}
