package net.sourceforge.align.filter.macro;

import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.util.Util;

import org.junit.Before;
import org.junit.Test;

public class MooreMacroTest {

	private MooreMacro macro;
	
	@Before
	public void setUp() {
		this.macro = new MooreMacro();
	}
	
	/**
	 * Tests if when aligning three to one no {@link NullPointerException}
	 * will be thrown, as it was the case.
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
		
		List<Alignment> alignmentList = Util.createAlignmentList(
				new String[][] {sourceSegments}, 
				new String[][] {targetSegments});
		
		List<Alignment> result = macro.apply(alignmentList);
		
		Util.assertAlignmentListContains(sourceSegments, targetSegments, result);
	}
	
}
