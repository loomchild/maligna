package net.loomchild.maligna.filter.macro;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.loomchild.maligna.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.length.PoissonDistributionCalculator;
import net.loomchild.maligna.calculator.length.counter.Counter;
import net.loomchild.maligna.calculator.length.counter.SplitCounter;
import net.loomchild.maligna.filter.Filter;
import net.loomchild.maligna.filter.aligner.Aligner;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithmFactory;

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
