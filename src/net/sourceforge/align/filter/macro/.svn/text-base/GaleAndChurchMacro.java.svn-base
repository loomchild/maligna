package net.sourceforge.align.filter.macro;

import java.util.List;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.calculator.length.NormalDistributionCalculator;
import net.sourceforge.align.calculator.length.counter.CharCounter;
import net.sourceforge.align.calculator.length.counter.Counter;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.Aligner;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.viterbi.ViterbiAlgorithmFactory;

/**
 * Represents macro to align a text using Gale and Church algorithm.
 * Actual implementation can be slightly different but the result should be
 * very similar.
 * 
 * @see "A Program for Aligning Sentences in Bilingual Corpora, 
 * 		Gale, W.A., Church, K.W."
 * @author loomchild
 */
public class GaleAndChurchMacro implements Macro {

	public List<Alignment> apply(List<Alignment> alignmentList) {
		
		Counter counter = new CharCounter();
		Calculator calculator = new NormalDistributionCalculator(counter);
		
		HmmAlignAlgorithmFactory algorithmFactory = 
			new ViterbiAlgorithmFactory();
		
		AlignAlgorithm algorithm = 
			new AdaptiveBandAlgorithm(algorithmFactory, calculator);
		
		Filter filter = new Aligner(algorithm);
		
		return filter.apply(alignmentList);

	}

}
