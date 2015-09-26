package net.loomchild.maligna.filter.macro;

import java.util.List;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.length.counter.CharCounter;
import net.loomchild.maligna.calculator.length.counter.Counter;
import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;
import net.loomchild.maligna.filter.aligner.Aligner;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.loomchild.maligna.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.loomchild.maligna.calculator.length.NormalDistributionCalculator;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.filter.aligner.align.hmm.viterbi.ViterbiAlgorithmFactory;

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
