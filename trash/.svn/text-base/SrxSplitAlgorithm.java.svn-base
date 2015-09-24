package align.filter.modifier.modify.split;


import java.io.Reader;
import java.util.List;

/**
 * Reprezentuje splitter dzielący tekst za pomocą reguł zapisanych w formacie 
 * SRX.
 *
 * @author Jarek Lipski (loomchild)
 */
public class SrxSplitAlgorithm extends SplitAlgorithm {
	
	{
		isSplitPresent = isSplitPresent();
	}
	
	private static boolean isSplitPresent;
	
	private SplitAlgorithm algorithm;
	
	public SrxSplitAlgorithm(Reader reader, String languageCode) {
		if (isSplitPresent) {
			this.algorithm = new SrxSplitAlgorithmImpl(reader, languageCode);
		} else {
			this.algorithm = new SrxSplitAlgorithmMock();
		}
	}
	
	private static boolean isSplitPresent() {
		try {
			Class.forName("split.srx.SrxSplitter");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
	public List<String> split(String string) {
		return algorithm.split(string);
    }

}
