package net.sourceforge.align.model.translation;

import java.io.Writer;

import net.sourceforge.align.model.vocabulary.Vocabulary;



/**
 * Represents initial translation model before first training iteration.
 * In this state translation probabilities from any word to any word are 
 * equal to one.
 * This object is immutable.
 *
 * @author Jarek Lipski (loomchild)
 */
class InitialTranslationModel implements TranslationModel {

	private InitialSourceData translationData;;
	
	/**
	 * Creates initial translation model.
	 */
	public InitialTranslationModel() {
		this.translationData = new InitialSourceData();
	}
	
	/**
	 * Always returns {@link InitialSourceData}, which means that for any source
	 * word it translates to any target word with probability equal to 1.
	 * @param sourceWid source word id, >= 0.
	 * @return immutable instance of {@link InitialSourceData}
	 */
	public SourceData get(int sourceWid) {
		assert sourceWid >= 0;
		return translationData;
	}

	/**
	 * @throws UnsupportedOperationException always because it is not possible
	 * 		to format initial model. 
	 */
	public void format(Writer writer, Vocabulary sourceVocabulary,
			Vocabulary targetVocabulary) {
		throw new UnsupportedOperationException();
	}

}
