package net.loomchild.maligna.model.translation;

import java.util.List;


/**
 * Represents source word translations with probabilities. 
 *
 * @author Jarek Lipski (loomchild)
 */
public interface SourceData {

	/**
	 * Returns probability of translating this source word to a target 
	 * word with given id. 
	 * @param targetWid target word id
	 * @return translation probability
	 */
	public double getTranslationProbability(int targetWid);

	/**
	 * Returns immutable list of translations with probability greater than 
	 * zero, sorted by probability descending.
	 * @return list of translations (essentially word, probability pairs)
	 */
	public List<TargetData> getTranslationList();
	
}
