package net.sourceforge.align.filter.macro;

import java.util.List;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.calculator.length.PoissonDistributionCalculator;
import net.sourceforge.align.calculator.length.counter.Counter;
import net.sourceforge.align.calculator.length.counter.SplitCounter;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.Aligner;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithmFactory;

/**
 * Uses algorithm similar to Gale and Church (see {@link GaleAndChurchMacro}),
 * but instead of normal distribution uses Poisson distribution and 
 * measures length of sentence in words instead of characters as in original.
 * 
 * Seems to give better results than Gale and Church algorithm.
 * 
 * @author loomchild
 */
public class PoissonMacro implements Macro {

	public List<Alignment> apply(List<Alignment> alignmentList) {
		
		Counter counter = new SplitCounter();
		Calculator calculator = 
			new PoissonDistributionCalculator(counter, alignmentList);
		
		HmmAlignAlgorithmFactory algorithmFactory = 
			new ForwardBackwardAlgorithmFactory();
		
		AlignAlgorithm algorithm = 
			new AdaptiveBandAlgorithm(algorithmFactory, calculator);
		
		Filter filter = new Aligner(algorithm);
		
		return filter.apply(alignmentList);

	}

}
