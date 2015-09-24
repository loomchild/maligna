package net.sourceforge.align.filter.macro;

import static net.sourceforge.align.model.vocabulary.VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.calculator.content.TranslationCalculator;
import net.sourceforge.align.calculator.length.PoissonDistributionCalculator;
import net.sourceforge.align.calculator.length.counter.Counter;
import net.sourceforge.align.calculator.length.counter.SplitCounter;
import net.sourceforge.align.calculator.meta.CompositeCalculator;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.Aligner;
import net.sourceforge.align.filter.aligner.UnifyAligner;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithmFactory;
import net.sourceforge.align.filter.meta.CompositeFilter;
import net.sourceforge.align.filter.modifier.Modifier;
import net.sourceforge.align.filter.modifier.modify.ModifyAlgorithm;
import net.sourceforge.align.filter.modifier.modify.clean.UnifyRareWordsCleanAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithm;
import net.sourceforge.align.filter.selector.FractionSelector;
import net.sourceforge.align.filter.selector.OneToOneSelector;
import net.sourceforge.align.model.vocabulary.Vocabulary;
import net.sourceforge.align.model.vocabulary.VocabularyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents macro to align a text using Moore's algorithm.
 * Actual implementation can be slightly different (for example does not 
 * normalize scores, selects a fraction instead, or uses different distribution
 * because results seem to be better), but the result should be
 * very similar (in reality they are worse, investigating the issue).
 * 
 * @see "Fast and Accurate Sentence Alignment of Bilingual 
 * 		Corpora, Robert C. Moore"
 * 
 * @see <a href="http://iis.ipipan.waw.pl/2008/proceedings/iis08-27.pdf">
 * 		A new tool for the bilingual text aligning at the sentence level,
 * 		Krzystof Jassem, Jarek Lipski</a>
 * 
 * @author loomchild
 */
public class MooreMacro implements Macro {
	
	private static final Log log = LogFactory.getLog(MooreMacro.class);

	public static final float SELECT_FRACTION = 0.85f;
	
	/**
	 * Performs the alignment:
	 * <ol>
	 * <li>Removes rare words from the input</li> 
	 * <li>Aligns by length and selects best alignments from the result</li>
	 * <li>Trains language and translation models based on initial alignment</li>
	 * <li>Performs actual alignment using the models</li>
	 * <li>Unifies initial alignment with resulting one to recover rare words</li>
	 * </ol> 
	 */
	public List<Alignment> apply(List<Alignment> alignmentList) {
		List<Alignment> unifiedAlignmentList = unifyRareWords(alignmentList);
		
		List<Alignment> lengthAlignmentList = lengthAlign(unifiedAlignmentList);
		
		List<Alignment> bestAlignmentList = selectBestAlignments(lengthAlignmentList);
		
		if (bestAlignmentList.size() == 0) {
			log.warn("Content alignment is impossible because zero " +
					"best alignments were selected from length alignment. " +
					"Returning result of length alignment only.");
			return unifyAlignments(alignmentList, lengthAlignmentList);
		}

		List<Alignment> contentAlignmentList = 
			contentAlign(unifiedAlignmentList, bestAlignmentList);
		
		return unifyAlignments(alignmentList, contentAlignmentList);
		
	}
	
	/**
	 * Changes rare words in the input to some predefined unknown 
	 * word. This improves alignment speed and reduces translation and language
	 * models size.
	 * 
	 * @param alignmentList input alignment list
	 * @return modified alignment list
	 */
	private List<Alignment> unifyRareWords(List<Alignment> alignmentList) {
		
		SplitAlgorithm splitAlgorithm = DEFAULT_TOKENIZE_ALGORITHM;

		Vocabulary sourceVocabulary = new Vocabulary();
		Vocabulary targetVocabulary = new Vocabulary();
		List<List<Integer>> sourceWidList = new ArrayList<List<Integer>>(); 
		List<List<Integer>> targetWidList = new ArrayList<List<Integer>>();

		VocabularyUtil.tokenize(splitAlgorithm, alignmentList, 
				sourceVocabulary, targetVocabulary, 
				sourceWidList, targetWidList);

		sourceVocabulary = VocabularyUtil.createTruncatedVocabulary(
				sourceWidList, sourceVocabulary);
		targetVocabulary = VocabularyUtil.createTruncatedVocabulary(
				targetWidList, targetVocabulary);

		ModifyAlgorithm sourceAlgorithm = 
			new UnifyRareWordsCleanAlgorithm(sourceVocabulary);
		ModifyAlgorithm targetAlgorithm = 
			new UnifyRareWordsCleanAlgorithm(targetVocabulary);

		Filter filter = new Modifier(sourceAlgorithm, targetAlgorithm);
		
		return filter.apply(alignmentList);
		
	}
	
	/**
	 * First algorithm phase - align text by segment length (using similar 
	 * method to the one used in {@link PoissonMacro}). 
	 * @param alignmentList input alignment list
	 * @return aligned list
	 */
	private List<Alignment> lengthAlign(List<Alignment> alignmentList) {
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

	/**
	 * Selects only {@link #SELECT_FRACTION} one-to-one alignments from the
	 * result.
	 * @param alignmentList
	 * @return
	 */
	private List<Alignment> selectBestAlignments(List<Alignment> alignmentList) {
		List<Filter> filterList = new ArrayList<Filter>();
		filterList.add(new OneToOneSelector());
		filterList.add(new FractionSelector(SELECT_FRACTION));
		Filter filter = new CompositeFilter(filterList);		
		return filter.apply(alignmentList);
	}
	
	/**
	 * Second algorithm phase - align by segment contents. First trains
	 * translation model and language models using alignment obtained in first 
	 * phase, and after that aligns by calculating translation probability.
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
	
	/**
	 * Unifies alignments from alignment list with reference alignment list.
	 * @param alignmentList alignment list
	 * @param referenceAlignmentList reference alignment list
	 * @return unified alignment list
	 * @see UnifyAligner
	 */
	private List<Alignment> unifyAlignments(List<Alignment> alignmentList, 
			List<Alignment> referenceAlignmentList) {
		
		Filter filter = new UnifyAligner(referenceAlignmentList);
		
		return filter.apply(alignmentList);
		
	}
	
	
}
