package net.loomchild.maligna.model.translation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.loomchild.maligna.model.vocabulary.Vocabulary;
import net.loomchild.maligna.model.ModelParseException;

/**
 * Represents translation model utilities. 
 * Responsible most importantly for training a {@link TranslationModel}, 
 * but also for parsing it from input stream, etc. 
 * 
 * @author Jarek Lipski (loomchild)
 */
public class TranslationModelUtil {
	
	public static final int DEFAULT_TRAIN_ITERATION_COUNT = 4;
	
	/**
	 * Trains translation model using given source and target segments
	 * (training corpus).
	 * These source and target segment lists must have the same number of 
	 * segments (in other words they must be aligned one-to-one).
	 * 
	 * @see "The Mathematics of Statistical Machine Translation: Parameter Estimation, 
	 * 		Brown, P. F., Della Pietra S. A., Della Pietra, V. J., Mercer, R. L." 
	 * 
	 * @param iterationCount number of training iterations
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return new trained translation model
	 */
	public static TranslationModel train(int iterationCount, 
			List< List<Integer> > sourceSegmentList, 
			List< List<Integer> > targetSegmentList) {
		assert sourceSegmentList.size() == targetSegmentList.size();
		assert iterationCount >= 1;
		
		TranslationModel model = new InitialTranslationModel();
		MutableTranslationModel newModel = null;
		
		for (int iteration = 0; iteration < iterationCount; ++iteration) {
			newModel = performTrainingIteration(model, sourceSegmentList, 
				targetSegmentList);
			model = newModel;
		}

		newModel.sort();
		
		return newModel;
	}
	
	/**
	 * Trains translation model using {@link #DEFAULT_TRAIN_ITERATION_COUNT}
	 * number of iterations. 
	 * 
	 * @see #train(int, List, List)
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return new trained translation model
	 */
	public static TranslationModel train(List< List<Integer> > sourceSegmentList, 
			List< List<Integer> > targetSegmentList) {
		return train(DEFAULT_TRAIN_ITERATION_COUNT, sourceSegmentList, 
				targetSegmentList);
	}

	/**
	 * Performs single translation model training iteration. 
	 * 
	 * Given source and target segment lists must have the same number of 
	 * segments (in other words they must be aligned one-to-one).
	 * 
	 * TODO: This method needs to be described in more detail.
	 * 
	 * @param model input translation model
	 * @param sourceSegmentList source segment list
	 * @param targetSegmentList target segment list
	 * @return new trained translation model
	 */
	private static MutableTranslationModel performTrainingIteration(
			TranslationModel model,
			List< List<Integer> > sourceSegmentList, 
			List< List<Integer> > targetSegmentList) {
		MutableTranslationModel newModel = new MutableTranslationModel();
		Iterator< List<Integer> > sourceSegmentIterator = 
				sourceSegmentList.iterator();
		Iterator< List<Integer> > targetSegmentIterator = 
				targetSegmentList.iterator();
		
		while(sourceSegmentIterator.hasNext() 
				&& targetSegmentIterator.hasNext()) {
			List<Integer> sourceSegment = sourceSegmentIterator.next();
			List<Integer> sourceSegmentAndNull = 
					new ArrayList<Integer>(sourceSegment.size() + 1);
			sourceSegmentAndNull.addAll(sourceSegment);
			sourceSegmentAndNull.add(Vocabulary.NULL_WID);
			List<Integer> targetSegment = targetSegmentIterator.next();

			for (int targetWid : targetSegment) {
				double probabilitySum = 0;
				
				for (int sourceWid : sourceSegmentAndNull) {
					probabilitySum += model.get(sourceWid).
							getTranslationProbability(targetWid);
				}
				assert probabilitySum > 0;

				// Commented out Moore optimization. 
				double minProbabilityChange = 
					1.0 / (double)sourceSegmentAndNull.size();
				for (int sourceWid : sourceSegmentAndNull) {
					double oldModelProbability = model.get(
							sourceWid).getTranslationProbability(targetWid);
					double probabilityChange = 
						oldModelProbability / probabilitySum;
					MutableSourceData newModelData;
					if (probabilityChange >= minProbabilityChange) {
						newModelData = newModel.getMutable(sourceWid);
					} else {
						newModelData = newModel.getMutable(Vocabulary.NULL_WID);
					}
					double newModelProbability = 
							newModelData.getTranslationProbability(targetWid);
					newModelData.setTranslationProbability(targetWid, 
							newModelProbability + probabilityChange);
				}
			}
		}

		newModel.normalize();
		return newModel;
	}
	
	/**
	 * Parses translation model from input reader. Uses simple plaintext 
	 * format where each line represents a translation and consists of
	 * source word, target word and translation probability.
	 * @return parsed translation model
	 */
	public static TranslationModel parse(Reader reader, 
			Vocabulary sourceVocabulary, Vocabulary targetVocabulary) {
		try {
			BufferedReader bufferedReader = new BufferedReader(reader);
			MutableTranslationModel translationModel = new MutableTranslationModel();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split("\\s");
				if (parts.length == 3) {
					String sourceWord = parts[0];
					String targetWord = parts[1];
					double probability = Double.parseDouble(parts[2]);
					
					int sourceWid = sourceVocabulary.putWord(sourceWord);
					int targetWid = targetVocabulary.putWord(targetWord);

					MutableSourceData sourceData = 
						translationModel.getMutable(sourceWid);
					sourceData.setTranslationProbability(targetWid, probability);
				} else if (parts.length != 0) {
					throw new ModelParseException("Bad number of " +
							"line parts.");
				}
			}
			translationModel.normalize();
			translationModel.sort();
			return translationModel;
		} catch (NumberFormatException e) {
			throw new ModelParseException("Part format error", e);
		} catch (IOException e) {
			throw new ModelParseException("IO error", e);
		}
	}
		
}
