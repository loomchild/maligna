package net.loomchild.maligna.filter.modifier.modify.split;


import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.loomchild.segment.TextIterator;
import net.loomchild.segment.srx.SrxDocument;
import net.loomchild.segment.srx.SrxParser;
import net.loomchild.segment.srx.SrxTextIterator;
import net.loomchild.segment.srx.io.SrxAnyParser;


/**
 * Represents a sentence splitter using rules defined in SRX format.
 * Uses external segment library. 
 *
 * @see <a href="https://github.com/loomchild/segment">Segment Project</a>
 * @see <a href="http://www.lisa.org/fileadmin/standards/srx20.html">SRX Standard</a> 
 * @author Jarek Lipski (loomchild)
 */
public class SrxSplitAlgorithm extends SplitAlgorithm {

	private SrxDocument document;
	
	private String languageCode;
	
	/**
	 * Creates a SRX splitter using given rules and selecting the ones to apply
	 * using given language code.
	 * @param reader reader containing SRX rules
	 * @param languageCode language code used to select the rules to apply
	 */
	public SrxSplitAlgorithm(Reader reader, String languageCode) {
		SrxParser parser = new SrxAnyParser();
		this.document = parser.parse(reader);
		this.languageCode = languageCode;
	}
	
	public List<String> split(String string) {
		TextIterator textIterator = 
			new SrxTextIterator(document, languageCode, string);
		List<String> segmentList = new ArrayList<String>();
		while(textIterator.hasNext()) {
			String segment = textIterator.next();
			segmentList.add(segment);
		}
		return segmentList;
    }

}
