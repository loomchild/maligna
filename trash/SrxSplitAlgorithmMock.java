package align.filter.modifier.modify.split;


import java.util.List;

/**
 * Reprezentuje atrape splittera SRX. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class SrxSplitAlgorithmMock extends SplitAlgorithm {
	
	public List<String> split(String string) {
		throw new UnsupportedOperationException("SRX splitter is not installed");
	}

}
