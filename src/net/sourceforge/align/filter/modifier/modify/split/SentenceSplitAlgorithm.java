 package net.sourceforge.align.filter.modifier.modify.split;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.util.ImpossibleException;


/**
 * <p>Represents simple sentence splitter using hardcoded rules.</p>
 *  
 * <p>Splitting does not omit any characters. Uses {@link SimpleSplitter}.</p>
 * 
 * <p>For more accurate sentence segmentation see {@link SrxSplitAlgorithm}.</p>
 *
 * @author Jarek Lipski (loomchild)
 */
public class SentenceSplitAlgorithm extends SplitAlgorithm {

	/**
	 * Splits input segment to a list of sentences. Spltting occurrs 
	 * after end-of-line character and after end of sentence character (.?!),
	 * if the next character is capital letter.
	 * 
	 * @param string input segment
	 * @return list of sentences
	 */
	public List<String> split(String string) {
		Reader stringReader = new StringReader(string);
		List<String> segmentList = null;
		try {
			segmentList = split(stringReader);
		} catch (IOException e) {
			throw new ImpossibleException("IOException reading StringReader", e);
		}
		return segmentList;
    }

	private List<String> split(Reader reader) throws IOException {
		List<String> segmentList = new ArrayList<String>();
		SimpleSplitter splitter = new SimpleSplitter(reader);
		while(splitter.hasNext()) {
			String segment = splitter.next();
			segmentList.add(segment);
		}
		return segmentList;
	}

}
