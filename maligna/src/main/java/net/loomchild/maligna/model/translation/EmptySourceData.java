package net.loomchild.maligna.model.translation;

import java.util.Collections;
import java.util.List;


/**
 * Represents empty translation data of source word - 
 * essentially it means that source word has no translations.
 * Always returns empty translation list and probability of translating to 
 * any word is always zero. 
 *
 * @author Jarek Lipski (loomchild)
 */
class EmptySourceData implements SourceData {

	/**
	 * @param targetWid target word id
	 * @return always zero
	 */
	public double getTranslationProbability(int targetWid) {
		assert targetWid >= 0;
		return 0;
	}

	/**
	 * @return empty translation list
	 */
	public List<TargetData> getTranslationList() {
		return Collections.emptyList();
	}

}
