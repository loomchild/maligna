package net.sourceforge.align.model.language;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import net.sourceforge.align.model.vocabulary.Vocabulary;


/**
 * Represents language model that can be modified after it was created.
 * After the model has been populated {@link #normalize()} method must
 * be called to calculate the probabilities.
 * 
 * @author Jarek Lipski (loomchild)
 */
class MutableLanguageModel implements LanguageModel {

	private ArrayList<Float> wordProbabilityArray;
	
	int wordOccurenceCount;

	float singletonWordProbability;
	
	/**
	 * Creates empty language model.
	 */
	public MutableLanguageModel() {
		this.wordProbabilityArray = new ArrayList<Float>();
		this.wordOccurenceCount = 0;
		this.singletonWordProbability = 0;
	}
	
	public float getWordProbability(int wid) {
		assert wid >= 0;
		if (wid < wordProbabilityArray.size()) {
			return wordProbabilityArray.get(wid);
		} else {
			return singletonWordProbability;
		}
	}

	public float getSingletonWordProbability() {
		return singletonWordProbability;
	}
	
	/**
	 * Adds word occurrence to the model. Word is represented as numeric
	 * word id.
	 * @param wid word id
	 */
	public void addWordOccurence(int wid) {
		addWordOccurence(wid, 1);
	}

	/**
	 * Adds given word given occurrence count to the model. 
	 * @param wid word id
	 * @param count number of occurrence to be added
	 */
	public void addWordOccurence(int wid, int count) {
		assert wid >= 0;
		ensureSize(wid + 1);
		wordProbabilityArray.set(wid, wordProbabilityArray.get(wid) + 
				count);
		wordOccurenceCount += count;
	}

	/**
	 * Calculates the occurrence probabilities. This method should be called
	 * after model has been populated.
	 */
	public void normalize() {
		for (int i = 0; i < wordProbabilityArray.size(); ++i) {
			float probability = wordProbabilityArray.get(i) / 
					(float)wordOccurenceCount; 
			wordProbabilityArray.set(i, probability);
		}
		singletonWordProbability = 1.0f / (float)wordOccurenceCount;
	}

	/**
	 * Ensures that word probability array has given size by expanding
	 * it with zeros if required.
	 * @param size
	 */
	private void ensureSize(int size) {
		int currentSize = wordProbabilityArray.size();
		if (size > currentSize) {
			wordProbabilityArray.ensureCapacity(size);
			for (int i = currentSize; i < size; ++i) {
				wordProbabilityArray.add(0.0f);
			}
		}
	}
	
	public void format(Writer writer, Vocabulary vocabulary) {
		PrintWriter printWriter = new PrintWriter(writer, true);
		for (int i = 0; i < wordProbabilityArray.size(); ++i) {
			printWriter.println(vocabulary.getWord(i) + "\t" + 
					wordProbabilityArray.get(i));
		}
	}


}
