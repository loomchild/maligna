package align.comparator;

import junit.framework.TestSuite;

public class ComparatorTestSuite extends TestSuite {

	public static junit.framework.Test suite() {		
		TestSuite suite = new TestSuite();

		suite.addTestSuite(SequentialComparatorTest.class);
		
		return suite;
	}
	
}
