package net.sourceforge.align.model.translation;

import java.util.Collections;
import java.util.List;


/**
 * Represents source translation data in initial model before first training
 * iteration. In this state all translations of this source word are equally 
 * probable and probability is equal to 1. This object is immutable.
 *
 * @author Jarek Lipski (loomchild)
 */
class InitialSourceData implements SourceData {

	/**
	 * Always returns one for any word id.
	 * @param targetWid target word id, >= 0
	 * @return always one
	 */
	public double getTranslationProbability(int targetWid) {
		assert targetWid >= 0;
		return 1;
	}

	/**
	 * @return empty translation list
	 */
	public List<TargetData> getTranslationList() {
		return Collections.emptyList();
	}

}
