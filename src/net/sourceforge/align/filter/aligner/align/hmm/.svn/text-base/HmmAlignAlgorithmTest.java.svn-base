package net.sourceforge.align.filter.aligner.align.hmm;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.util.Util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Represents abstract unit tests common to all HMM algorithms.
 * @author loomchild
 */
public abstract class HmmAlignAlgorithmTest {
	
	protected abstract AlignAlgorithm getAlgorithm();

	/**
	 * Tests whether one to zero alignment works correctly. 
	 * Encountered a problem that when last alignment is 1-0 then backtrace
	 * ignores it. 
	 */
	@Test
	public void testOneToZero() {
		String[] sourceSegments = new String[]{"Segment 1"};
		String[] targetSegments = new String[]{};
		
		AlignAlgorithm algorithm = getAlgorithm();
		List<Alignment> result = algorithm.align(
				Arrays.asList(sourceSegments), 
				Arrays.asList(targetSegments));
		
		Assert.assertEquals(1, result.size());
		Util.assertAlignmentEquals(sourceSegments, targetSegments, result.get(0));
	}

	/**
	 * Tests if when aligning three to one all segments will be preserved. 
	 */
	@Test
	public void testPreservesAllSegments() {
		String[] sourceSegments = new String[]{
				"He had given up attending to matters of practical importance; he had lost all desire to do so.",
				"Nothing that any landlady could do had a real terror for him.",
				"But to be stopped on the stairs, to be forced to listen to her trivial, irrelevant gossip, to pestering demands for payment, threats and complaints, and to rack his brains for excuses, to prevaricate, to lie—no, rather than that, he would creep down the stairs like a cat and slip out unseen."
		};
		
		String[] targetSegments = new String[]{
				"Aber auf der Treppe stehenzubleiben, allerlei Gewäsch über allen möglichen ihm ganz gleichgültigen Alltagskram, all diese Mahnungen ans Bezahlen, die Drohungen und Klagen anzuhören und dabei selbst sich herauszuwinden, sich zu entschuldigen, zu lügen – nein, da war es schon besser, wie eine Katze auf der Treppe vorbeizuschlüpfen und sich, ohne von jemand gesehen zu werden, flink davonzumachen."
		};
		
		AlignAlgorithm algorithm = getAlgorithm();
		List<Alignment> result = algorithm.align(
				Arrays.asList(sourceSegments), 
				Arrays.asList(targetSegments));
		
		Util.assertAlignmentListContains(sourceSegments, targetSegments, result);
	}

	/**
	 * <p>
	 * This is a little more advanced test for aligner. 
	 * It tests aligning interleaved short and long segments, 
	 * which logically should be aligned fine if aligner is correct.
	 * </p>
	 * <p>Bug reported by Boyan Ivanov Bonev.</p>  
	 */
	@Test
	public void testVeryShortAndLongInterleaved() {
		String[] sourceSegments = {
		        "Presidente",
		        "1.",
		        "Reanudación del período de Sesiones",
		        "Se abre la sesión a las 17.00 horas.",
		        "2.",
		        "Aprobación del Acta de la sesión anterior",
		        "Se aprueba el Acta de la sesión anterior.",
		        "3.",
		        "Composición del Parlamento",
		};
		
		String[] targetSegments = {
		        "President",
		        "1.",
		        "Resumption of the session",
		        "The sitting opened at 17.00.",
		        "2.",
		        "Approval of the Minutes of the previous sitting",
		        "The Minutes of the previous sitting were approved.",
		        "3.",
		        "Composition of Parliament",
		};

		String[][] expectedSourceSegments = 
			createArrayOfSingletons(sourceSegments);

		String[][] expectedTargetSegments = 
			createArrayOfSingletons(targetSegments);

		AlignAlgorithm algorithm = getAlgorithm();
		List<Alignment> result = algorithm.align(
				Arrays.asList(sourceSegments), 
				Arrays.asList(targetSegments));

		Util.assertAlignmentListEquals(expectedSourceSegments, 
				expectedTargetSegments, result);
	}
	
	/**
	 * Creates an array of array of string where every element is a single
	 * element array. Elements are taken from input array.
	 * @return array of singletons
	 */
	private String[][] createArrayOfSingletons(String[] segmentArray) {
		String[][] result = new String[segmentArray.length][];
		for (int i = 0; i < segmentArray.length; ++i) {
			result[i] = new String[]{segmentArray[i]};
		}
		return result;
	}
	
}
