package net.loomchild.maligna.model.language;

import java.io.Writer;

import net.loomchild.maligna.model.vocabulary.Vocabulary;


/**
 * Represents simple unigram language model. Responsible for storing
 * word probabilities in given language. Words are represented as 
 * integer word IDs.
 * 
 * @author Jarek Lipski (loomchild)
 */
public interface LanguageModel {

	/**
	 * Returns word occurrence probability in modeled language.
	 * @param wid word id
	 * @return word occurrence probability; [0, 1]
	 */
	public float getWordProbability(int wid);
	
	/**
	 * Returns probability of some word if it occurred only once in 
	 * training corpus. Basically this number is equal to 
	 * 1 / total word number of words in the training corpus.
	 * @return singleton probability
	 */
	public float getSingletonWordProbability();

	/**
	 * Formats the language model to given writer in plaintext format. 
	 * Used for logging purposes and for storing models.
	 * @param writer writer
	 * @param vocabulary this language vocabulary
	 */
	public void format(Writer writer, Vocabulary vocabulary);

}
