package net.loomchild.maligna.model.translation;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import net.loomchild.maligna.model.vocabulary.Vocabulary;


/**
 * Represents mutable translation model (probabilistic dictionary).
 * After model training iteration and before retrieving any data from
 * it {@link #normalize()} and {@link #sort()} methods should be called.
 *
 * @author Jarek Lipski (loomchild)
 */
public class MutableTranslationModel implements TranslationModel {
	
	ArrayList<MutableSourceData> translationArray;
	
	/**
	 * Creates empty translation model.
	 */
	public MutableTranslationModel() {
		this.translationArray = new ArrayList<MutableSourceData>();
	}
	
	public SourceData get(int sourceWid) {
		assert sourceWid >= 0;
		if (sourceWid < translationArray.size()) {
			return translationArray.get(sourceWid);
		} else {
			return new EmptySourceData();
		}
	}
	
	/**
	 * Retrieves mutable translation data including translation probabilities 
	 * to other words for a word with given id. If the source word is not 
	 * present in the model it is automatically created.
	 * @param sourceWid source word id, >= 0.
	 * @return translation data
	 */
	public MutableSourceData getMutable(int sourceWid) {
		assert sourceWid >= 0;
		ensureSize(sourceWid + 1);
		return translationArray.get(sourceWid);
	}
	
	/**
	 * Normalizes all contained source word translations by using 
	 * {@link MutableSourceData#normalize()}.
	 * This method should be called after model training iteration
	 * but before retrieving any data from it.
	 */
	public void normalize() {
		for (MutableSourceData data : translationArray) {
			data.normalize();
		}
	}

	/**
	 * Sorts all contained source word translations by using 
	 * {@link MutableSourceData#sort()}.  
	 * This method should be called after model training iteration
	 * but before retrieving any data from it.
	 */
	public void sort() {
		for (MutableSourceData data : translationArray) {
			data.sort();
		}
	}
	
	/**
	 * Ensures that translation array has given size by expanding
	 * it with empty {@link MutableSourceData} objects.
	 * @param size
	 */
	private void ensureSize(int size) {
		int currentSize = translationArray.size();
		if (size > currentSize) {
			translationArray.ensureCapacity(size);
			for (int i = currentSize; i < size; ++i) {
				translationArray.add(new MutableSourceData());
			}
		}
	}

	public void format(Writer writer, Vocabulary sourceVocabulary,
			Vocabulary targetVocabulary) {
		PrintWriter printWriter = new PrintWriter(writer, true);
		for (int i = 0; i < translationArray.size(); ++i) {
			MutableSourceData sourceData = translationArray.get(i);
			for (TargetData targetData : sourceData.getTranslationList()) {
				printWriter.println(sourceVocabulary.getWord(i) + "\t" + 
						targetVocabulary.getWord(targetData.getWid()) + "\t" + 
						targetData.getProbability());
			}
		}
	}

}
