package net.loomchild.maligna.model.language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import net.loomchild.maligna.model.ModelParseException;

/**
 * Represents {@link LanguageModel} utility methods - training and parsing.
 *
 * @author Jarek Lipski (loomchild)
 */
public class LanguageModelUtil {
	
	/**
	 * Trains language model by adding all words from given segment list
	 * (training corpus) to it and after that calculating the probabilities
	 * by calling {@link MutableLanguageModel#normalize()}.
	 * @param segmentList training corpus
	 */
	public static LanguageModel train(List< List<Integer> > segmentList) {
		MutableLanguageModel model = new MutableLanguageModel();

		for (List<Integer> segment : segmentList) {
			for (int wid : segment) {
				model.addWordOccurence(wid);
			}
		}
		model.normalize();
		return model;
	}
		
	/**
	 * Parses language model from  input stream. Uses simple plaintext format
	 * where each line consists of word, whitespace and probability.
	 * @param reader reader from which model should be read
	 * @return parsed language model
	 */
	public static LanguageModel parse(Reader reader) {
		try {
			BufferedReader bufferedReader = new BufferedReader(reader);
			MutableLanguageModel languageModel = new MutableLanguageModel();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split("\\s");
				if (parts.length == 2) {
					int wid = Integer.parseInt(parts[0]);
					int count = Integer.parseInt(parts[1]);
					languageModel.addWordOccurence(wid, count);
				} else if (parts.length != 0) {
					throw new ModelParseException("Bad number of line parts.");
				}
			}
			languageModel.normalize();
			return languageModel;
		} catch (NumberFormatException e) {
			throw new ModelParseException("Part format error", e);
		} catch (IOException e) {
			throw new ModelParseException("IO error", e);
		}
	}
		
}
