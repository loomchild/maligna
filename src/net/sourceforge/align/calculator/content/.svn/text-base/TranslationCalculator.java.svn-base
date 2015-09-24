package net.sourceforge.align.calculator.content;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithm;
import net.sourceforge.align.model.language.LanguageModel;
import net.sourceforge.align.model.language.LanguageModelUtil;
import net.sourceforge.align.model.translation.SourceData;
import net.sourceforge.align.model.translation.TranslationModel;
import net.sourceforge.align.model.translation.TranslationModelUtil;
import net.sourceforge.align.model.vocabulary.Vocabulary;
import net.sourceforge.align.model.vocabulary.VocabularyUtil;
import net.sourceforge.align.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Represents calculator calculating probability of concatenation of target 
 * segments being translation of concatenation of source segments.</p> 
 * 
 * <p>To do this uses given translation and language models or trains
 * them using {@link TranslationModelUtil} and {@link LanguageModelUtil} 
 * with given reference corpus.</p>
 * 
 * @see "Machine Translation: an Introductory Guide, D. Arnold, L. Balkan, 
 * 		S. Meijer, R. Lee Humphreys, L. Sadler"
 * 
 * @author loomchild
 */
public class TranslationCalculator implements Calculator {

	private static Log log = LogFactory.getLog(TranslationCalculator.class);
	
	public static final float MINIMUM_TRANSLATION_PROBABILITY = 1e-38f;

	private Vocabulary sourceVocabulary;

	private Vocabulary targetVocabulary;

	private LanguageModel sourceLanguageModel;

	private LanguageModel targetLanguageModel;

	private TranslationModel translationModel;
	
	private SplitAlgorithm splitAlgorithm;

	/**
	 * Creates translation calculator.
	 * 
	 * @param sourceVocabulary vocabulary mapping source words to identifiers
	 * @param targetVocabulary vocabulary mapping target words to identifiers
	 * @param sourceLanguageModel source language model
	 * @param targetLanguageModel target language model
	 * @param translationModel translation model from source to target
	 * @param splitAlgorithm algorithm used to split segments into words
	 */
	public TranslationCalculator( 
			Vocabulary sourceVocabulary, Vocabulary targetVocabulary, 
			LanguageModel sourceLanguageModel, LanguageModel targetLanguageModel, 
			TranslationModel translationModel, SplitAlgorithm splitAlgorithm) {
		this.splitAlgorithm = splitAlgorithm;
		this.sourceVocabulary = sourceVocabulary;
		this.targetVocabulary = targetVocabulary;
		this.sourceLanguageModel = sourceLanguageModel;
		this.targetLanguageModel = targetLanguageModel;
		this.translationModel = translationModel;
	}
	
	/**
	 * Creates translation calculator with split algorithm equal to 
	 * {@link VocabularyUtil#DEFAULT_TOKENIZE_ALGORITHM}.
	 * 
	 * @param sourceVocabulary vocabulary mapping source words to identifiers
	 * @param targetVocabulary vocabulary mapping target words to identifiers
	 * @param sourceLanguageModel source language model
	 * @param targetLanguageModel target language model
	 * @param translationModel translation model from source to target
	 */
	public TranslationCalculator(
			Vocabulary sourceVocabulary, Vocabulary targetVocabulary, 
			LanguageModel sourceLanguageModel, LanguageModel targetLanguageModel, 
			TranslationModel translationModel) {
		this(sourceVocabulary, targetVocabulary, sourceLanguageModel, 
				targetLanguageModel, translationModel, 
				VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM);
	}
	
	/**
	 * Creates translation calculator. Trains translation and language models 
	 * based on given reference alignment list. Also creates its own 
	 * vocabulary based on all the words in reference alignment list.
	 *  
	 * @param alignmentList reference corpus
	 * @param trainIterationCount number of translation model training iterations
	 * @param splitAlgorithm algorithm used to split segments into words
	 */
	public TranslationCalculator(List<Alignment> alignmentList, 
			int trainIterationCount, SplitAlgorithm splitAlgorithm) {
		if (alignmentList.size() == 0) {
			throw new IllegalArgumentException("Reference corpus cannot be empty");
		}
		
		this.splitAlgorithm = splitAlgorithm;
		
		sourceVocabulary = new Vocabulary();
		targetVocabulary = new Vocabulary();
		List<List<Integer>> sourceWidList = new ArrayList<List<Integer>>();
		List<List<Integer>> targetWidList = new ArrayList<List<Integer>>();

		VocabularyUtil.tokenize(splitAlgorithm, alignmentList, sourceVocabulary, 
				targetVocabulary, sourceWidList, targetWidList);
		
		sourceLanguageModel = 
			LanguageModelUtil.train(sourceWidList);
		targetLanguageModel = 
			LanguageModelUtil.train(targetWidList);
		
		translationModel = 
			TranslationModelUtil.train(
			trainIterationCount, sourceWidList, targetWidList);
		
		if (log.isTraceEnabled()) {
			StringWriter writer = new StringWriter();
			translationModel.format(writer, sourceVocabulary, targetVocabulary);
			log.trace(writer.toString());
		}
	}

	/**
	 * Calls {@link #TranslationCalculator(List, int, SplitAlgorithm)} 
	 * with split algorithm equal to 
	 * {@link VocabularyUtil#DEFAULT_TOKENIZE_ALGORITHM}.
	 *  
	 * @param alignmentList reference corpus
	 * @param trainIterationCount number of translation model training iterations
	 */
	public TranslationCalculator(List<Alignment> alignmentList, 
			int trainIterationCount) {
		this(alignmentList, trainIterationCount, VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM);
	}

	/**
	 * Calls {@link #TranslationCalculator(List, int, SplitAlgorithm)} 
	 * with translation model training iteration count equal to 
	 * {@link TranslationModelUtil#DEFAULT_TRAIN_ITERATION_COUNT} and 
	 * split algorithm equal to 
	 * {@link VocabularyUtil#DEFAULT_TOKENIZE_ALGORITHM}.
	 *  
	 * @param alignmentList reference corpus
	 */
	public TranslationCalculator(List<Alignment> alignmentList) {
		this(alignmentList, TranslationModelUtil.DEFAULT_TRAIN_ITERATION_COUNT);
	}

	/**
	 * Calculates translation score. First it tokenizes source and target 
	 * segment and replaces the words with identifiers from {@link Vocabulary}.
	 * If both segment lists are empty returns zero, if only one of them is
	 * empty returns language score of it using 
	 * {@link #calculateLanguageScore(List, LanguageModel)}.
	 * If both are not empty then returns translation score 
	 * using {@link #calculateTranslationScore(List, List, TranslationModel)}
	 * and adds language score of source segments to it.
	 *
	 * @param sourceSegmentList list of source segmnets
	 * @param targetSegmentList list of target segments
	 * @return translation score
	 */
	public float calculateScore(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		List<Integer> sourceWidList = VocabularyUtil.tokenize(splitAlgorithm, 
				sourceSegmentList, sourceVocabulary);
		List<Integer> targetWidList = VocabularyUtil.tokenize(splitAlgorithm,
				targetSegmentList, targetVocabulary);
		float score;
		if (sourceWidList.size() == 0 && targetWidList.size() == 0) {
			score = 0.0f;
		} else if (sourceWidList.size() == 0) {
			score = calculateLanguageScore(targetWidList, targetLanguageModel);
		} else {
			score = calculateLanguageScore(sourceWidList, sourceLanguageModel);
			if (targetWidList.size() > 0) {
				List<Integer> newSourceWidList = 
					new ArrayList<Integer>(sourceWidList);
				newSourceWidList.add(Vocabulary.NULL_WID);
				score += calculateTranslationScore(newSourceWidList,
						targetWidList, translationModel);
			}
		}
		//score = (float)Math.pow(score, 1.0 / (double)sourceWidList.size()) / 10.0f;
		assert score >= 0 : score;
		return score;
	}
	
	/**
	 * Calculates probability (converted to score equal to -ln(probability)) 
	 * of given segment (represented as word id list) 
	 * is correct in according to given language model. In other words
	 * calculates probability of given text being correct in given language. 
	 * 
	 * @param widList
	 * @param languageModel
	 * @return
	 */
	private float calculateLanguageScore(List<Integer> widList,
			LanguageModel languageModel) {
		assert widList.size() > 0;
		float score = 0.0f; 
		for (Integer wid : widList) {
			float wordProbability;
			if (wid != null) {
				wordProbability = languageModel.getWordProbability(wid);
			} else {
				wordProbability = languageModel.getSingletonWordProbability();
			}
			score += Util.toScore(wordProbability);
		}
		assert score >= 0 : score;
		return score;
	}
	
	/**
	 * Calculates probability (converted to score equal to -ln(probability)) 
	 * of given target segments (represented as word id list) being a 
	 * translation of given source segments according to given translation
	 * model. 
	 * Cannot return probability less than constant 
	 * {@link #MINIMUM_TRANSLATION_PROBABILITY}. 
	 * 
	 * @param sourceWidList
	 * @param targetWidList
	 * @param translationModel
	 * @return
	 */
	private float calculateTranslationScore(List<Integer> sourceWidList, 
			List<Integer> targetWidList, TranslationModel translationModel) {
		assert targetWidList.size() > 0;
		float score = -(float)Math.log(1.0 / sourceWidList.size()) *   
				targetWidList.size();
		for (Integer targetWid : targetWidList) {
			float translationProbability = 0.0f;
			if (targetWid != null) {
				for (Integer sourceWid : sourceWidList) {
					if (sourceWid != null) {
						SourceData sourceData = translationModel.get(sourceWid);
						translationProbability += 
							sourceData.getTranslationProbability(targetWid);
					}
				}
			}
			translationProbability = Math.max(translationProbability, 
					MINIMUM_TRANSLATION_PROBABILITY);
			score += -(float)Math.log(translationProbability);
		}
		return score;
	}
	
}
