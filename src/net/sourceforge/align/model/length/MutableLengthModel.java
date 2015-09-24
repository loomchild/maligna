package net.sourceforge.align.model.length;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Represents length model that can be changed after it was created.
 * After the model has been populated {@link #normalize()} method must
 * be called to calculate the probabilities.
 * 
 * @author loomchild
 */
public class MutableLengthModel implements LengthModel {

	private ArrayList<Float> lengthProbabilityArray;

	float meanLength;
	
	int totalLength;

	int lengthOccurenceCount;

	/**
	 * Creates empty length model.
	 */
	public MutableLengthModel() {
		this.lengthProbabilityArray = new ArrayList<Float>();
		this.meanLength = 0.0f;
		this.lengthOccurenceCount = 0;
		this.totalLength = 0;
	}

	public float getLengthProbability(int length) {
		assert length >= 0;
		if (length < lengthProbabilityArray.size()) {
			return lengthProbabilityArray.get(length);
		} else {
			return 0;
		}
	}

	public float getMeanLength() {
		return meanLength;
	}

	/**
	 * Adds occurrence of segment length to the model.
	 * @param length segment length
	 */
	public void addLengthOccurence(int length) {
		assert length >= 0;
		ensureSize(length + 1);
		lengthProbabilityArray.set(length, lengthProbabilityArray.get(length) + 1);
		++lengthOccurenceCount;
		totalLength += length;
	}

	/**
	 * Calculates the occurrence probabilities. This method should be called
	 * after model has been populated.
	 */
	public void normalize() {
		for (int i = 0; i < lengthProbabilityArray.size(); ++i) {
			float probability = lengthProbabilityArray.get(i) / 
					(float)lengthOccurenceCount; 
			lengthProbabilityArray.set(i, probability);
		}
		meanLength = (float)totalLength / (float)lengthOccurenceCount;
	}
	
	/**
	 * Ensures that length probability array has given size by expanding
	 * it with zeros if required.
	 * @param size
	 */
	private void ensureSize(int size) {
		int currentSize = lengthProbabilityArray.size();
		if (size > currentSize) {
			lengthProbabilityArray.ensureCapacity(size);
			for (int i = currentSize; i < size; ++i) {
				lengthProbabilityArray.add(0.0f);
			}
		}
	}
	
	public void format(Writer writer) {
		PrintWriter printWriter = new PrintWriter(writer, true);
		for (int i = 0; i < lengthProbabilityArray.size(); ++i) {
			printWriter.println(i + "\t" + lengthProbabilityArray.get(i));
		}
	}

}
