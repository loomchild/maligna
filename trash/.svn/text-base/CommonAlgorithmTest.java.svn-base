package align.filter.aligner.align;

import static loomchild.util.testing.Utils.assertListEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import align.Alignment;
import align.filter.aligner.align.algorithm.galechurch.GaleChurchAlignAlgorithm;
import align.filter.aligner.align.algorithm.moore.MooreAlignAlgorithm;
import align.filter.aligner.align.onetoone.OneToOneAlgorithm;

/**
 * Test wykonywany dla wszystkich alignerów, testujący ich wspólne cechy.
 * Nowe alignery trzeba dodać w odpowidniej metodzie inicjalizującej.
 *
 * @author Jarek Lipski (loomchild)
 */
public class CommonAlgorithmTest {

	private List<AlignAlgorithm> alignerList;
	
	@Before
	public void setUp() {
		this.alignerList = createAlignerList();
	}
	
	/**
	 * Testuje czy próba dopasowania pustych list zwraca pustą liste dopasowań.
	 */
	@Test
	public void alignZeroToZeroReturnsEmptyList() {
		for (AlignAlgorithm aligner : alignerList) {
			checkAlignZeroToZeroReturnsEmptyList(aligner);
		}
	}
	
	public void checkAlignZeroToZeroReturnsEmptyList(AlignAlgorithm aligner) {
		List<Alignment> alignmentList = aligner.align(
				Arrays.asList(new String[]{}), Arrays.asList(new String[]{}));
		assertEquals(0, alignmentList.size());
	}

	@Test
	public void noOmissions() {
		for (AlignAlgorithm aligner : alignerList) {
			try {
				checkNoOmissions(aligner);
			} catch (AlignmentImpossibleException e) {
				//ignoruje alignery które nie mogą dopasowywać danych testowych
				//może zrobić oddzielną grupę alignerów albo zmienić test
			}
		}
	}

	public void checkNoOmissions(AlignAlgorithm aligner) {
		String[] sourceArray = new String[]{"a", "b", "c", "d", "ee"};
		String[] targetArray = new String[]{"1", "2", ""};
		List<Alignment> alignmentList = aligner.align(
				Arrays.asList(sourceArray), Arrays.asList(targetArray));
		List<String> alignedSourceList = new LinkedList<String>();
		List<String> alignedTargetList = new LinkedList<String>();
		for (Alignment alignment : alignmentList) {
			alignedSourceList.addAll(alignment.getSourceSegmentList());
			alignedTargetList.addAll(alignment.getTargetSegmentList());
		}
		assertListEquals(sourceArray, alignedSourceList);
		assertListEquals(targetArray, alignedTargetList);
	}
	
	/**
	 * Miejsce do umieszczania wszystkich alignerów do przetestowania.
	 */
	private List<AlignAlgorithm> createAlignerList() {
		List<AlignAlgorithm> alignerList = new LinkedList<AlignAlgorithm>();
		
		alignerList.add(new OneToOneAlgorithm());
		alignerList.add(new GaleChurchAlignAlgorithm());
		alignerList.add(new MooreAlignAlgorithm());
		
		return alignerList;
	}
	
}
