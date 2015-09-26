package net.loomchild.maligna.model.length;

import java.io.Writer;

/**
 * Represents a model of segment (sentence, paragraph) lengths for a particular
 * language, allowing to calculate given length probability and averages. 
 * 
 * @author loomchild
 */
public interface LengthModel {

	/**
	 * Calculates probability that segment of given length will occur
	 * in modeled language.
	 * @param length segment length
	 * @return length probability
	 */
	public float getLengthProbability(int length);
	
	/**
	 * @return mean segment length in modeled language
	 */
	public float getMeanLength();

	/**
	 * Formats the length model to given writer in plaintext format. 
	 * Used for logging purposes and for storing models.
	 * @param writer writer
	 */
	public void format(Writer writer);
	
}
