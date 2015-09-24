package align.filter.modifier.modify.split;


import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import split.srx.Document;
import split.srx.LanguageRule;
import split.srx.MapRule;
import split.srx.Parser;

/**
 * Reprezentuje splitter dzielący tekst za pomocą reguł zapisanych w formacie 
 * SRX.
 *
 * @author Jarek Lipski (loomchild)
 */
public class SrxSplitAlgorithmImpl extends SplitAlgorithm {
	
	private LanguageRule languageRule;

	private split.srx.SrxSplitter splitter;
	
	public SrxSplitAlgorithmImpl(Reader reader, String languageCode) {
		Document srxDocument = Parser.getInstance().parse(reader);
		MapRule mapRule = srxDocument.getSingletonMapRule();
		languageRule = mapRule.getLanguageMap(languageCode).getLanguageRule();
	}
	
	/**
	 * @inheritDoc
	 */
	public List<String> split(String string) {
		splitter = new split.srx.SrxSplitter(languageRule, string);
		List<String> segmentList = new ArrayList<String>();
		while(splitter.hasNext()) {
			String segment = splitter.next();
			segmentList.add(segment);
		}
		return segmentList;
    }

}
