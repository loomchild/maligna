package net.sourceforge.align.filter.aligner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.align.alignment.Alignment;
import net.sourceforge.align.filter.Filter;

/**
 * Hard to test if lexicon alignment occurs more than once, 
 * impossible to have n-0 alignments in lexicon.
 * 
 * @author loomchild
 */
public class LexiconAligner implements Filter {

	private Map<String, List<Alignment>> sourceLexiconMap;

	private Map<String, List<Alignment>> targetLexiconMap;
	

	public LexiconAligner(List<Alignment> lexiconAlignmentList) {
		// check that lexicon contains no alignments with zero source or
		// target segments
	}

	public List<Alignment> apply(List<Alignment> alignmentList) {
		// set all matched alignments in result list to have -Inf score.
		// make rest of the program ignore such alignments.
		List<Alignment> resultList = new ArrayList<Alignment>();
		
		/*for (Alignment alignment : alignmentList) {
			Iterator<String> sourceIterator = 
					alignment.getSourceSegmentList().iterator();
			Iterator<String> targetIterator = 
				alignment.getTargetSegmentList().iterator();
			
			Alignment reference
			
			targetIterator.
			
		}*/
		
		// set marked 
		
		return resultList;
	}

}
