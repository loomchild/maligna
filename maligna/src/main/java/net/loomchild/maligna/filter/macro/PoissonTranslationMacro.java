package net.loomchild.maligna.filter.macro;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.calculator.content.TranslationCalculator;
import net.loomchild.maligna.calculator.length.PoissonDistributionCalculator;
import net.loomchild.maligna.calculator.length.counter.Counter;
import net.loomchild.maligna.calculator.meta.CompositeCalculator;
import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.filter.Filter;
import net.loomchild.maligna.filter.aligner.Aligner;
import net.loomchild.maligna.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.loomchild.maligna.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.loomchild.maligna.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithmFactory;
import net.loomchild.maligna.filter.selector.FractionSelector;
import net.loomchild.maligna.filter.selector.OneToOneSelector;
import net.loomchild.maligna.calculator.length.counter.SplitCounter;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.filter.meta.CompositeFilter;

/**
 * Uses algorithm very similar to {@link TranslationMacro} but when calculating
 * final alignment phase combines translation probability and 
 * length probability {using @link PoissonMacro}.
 * 
 * @author loomchild
 */

public class PoissonTranslationMacro implements Macro {

	public static final float SELECT_FRACTION = 0.9f;
	
	/**
	 * Performs the alignment:
	 * <ol>
	 * <li>Aligns by length and selects best alignments from the result</li>
	 * <li>Trains language and translation models based on initial alignment</li>
	 * <li>Performs actual alignment using the models and lengths combined</li>
	 * </ol> 
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {

		List<Alignment> bestAlignmentList = lengthAlign(alignmentList);
		
		return contentAlign(alignmentList, bestAlignmentList);
		
	}
	
	/**
	 * First algorithm phase - align text by segment length (using similar 
	 * method to the one used in {@link PoissonMacro}). 
	 * Selects only {@link #SELECT_FRACTION} one-to-one alignments from the
	 * result.
	 * @param alignmentList input alignment list
	 * @return aligned list
	 */
	private List<Alignment> lengthAlign(List<Alignment> alignmentList) {

		List<Filter> filterList = new ArrayList<Filter>();
		
		Counter counter = new SplitCounter();
		Calculator calculator =
			new PoissonDistributionCalculator(counter, alignmentList);
		
		HmmAlignAlgorithmFactory algorithmFactory =
			new ForwardBackwardAlgorithmFactory();
		
		AlignAlgorithm algorithm = 
			new AdaptiveBandAlgorithm(algorithmFactory, calculator);
		
		filterList.add(new Aligner(algorithm));
		
		filterList.add(new OneToOneSelector());
		
		filterList.add(new FractionSelector(SELECT_FRACTION));
		
		Filter filter = new CompositeFilter(filterList);		
		
		return filter.apply(alignmentList);
		
	}
	
	/**
	 * Second algorithm phase - align by segment contents and. First trains
	 * translation model and language models using alignment obtained in first 
	 * phase, and after that aligns by calculating translation probability
	 * combined with length probability using {@link CompositeCalculator}.
	 * 
	 * @param alignmentList
	 * @param bestAlignmentList
	 * @return
	 */
	private List<Alignment> contentAlign(List<Alignment> alignmentList, 
			List<Alignment> bestAlignmentList) {
		
		List<Calculator> calculatorList = new ArrayList<Calculator>();
		
		Counter counter = new SplitCounter();
		calculatorList.add(new PoissonDistributionCalculator(counter, alignmentList));
		
		calculatorList.add(new TranslationCalculator(bestAlignmentList));

		Calculator calculator = new CompositeCalculator(calculatorList);
		
		HmmAlignAlgorithmFactory algorithmFactory = 
			new ForwardBackwardAlgorithmFactory();
		
		AlignAlgorithm algorithm = 
			new AdaptiveBandAlgorithm(algorithmFactory, calculator);

		Filter filter = new Aligner(algorithm);
		
		return filter.apply(alignmentList);
		 
	}
	
}
