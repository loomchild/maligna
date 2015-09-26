package net.loomchild.maligna.model.translation;

import java.io.Writer;

import net.loomchild.maligna.model.vocabulary.Vocabulary;


/**
 * Represents translation model which is basically probabilistic dictionary 
 * - it stores source word translations to target words along with each 
 * translation probability. 
 *
 * @see <a href="http://en.wikipedia.org/wiki/Statistical_machine_translation">Statistical Machine Translation</a>
 * @author Jarek Lipski (loomchild)
 */
public interface TranslationModel {

	/**
	 * Retrieves translation data including translation probabilities to other
	 * words for a word with given id
	 * @param sourceWid source word id, >= 0.
	 * @return translation data
	 */
	public SourceData get(int sourceWid);
	
	/**
	 * Formats the translation model to given writer in plaintext format. 
	 * Used for logging purposes and for storing models.
	 * @param writer writer
	 * @param sourceVocabulary source language vocabulary
	 * @param targetVocabulary target language vocabulary
	 */
	public void format(Writer writer, Vocabulary sourceVocabulary,
			Vocabulary targetVocabulary);

}
