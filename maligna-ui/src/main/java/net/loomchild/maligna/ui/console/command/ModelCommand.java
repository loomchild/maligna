package net.loomchild.maligna.ui.console.command;

import java.util.ArrayList;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.model.translation.TranslationModelUtil;
import net.loomchild.maligna.model.vocabulary.Vocabulary;
import net.loomchild.maligna.model.vocabulary.VocabularyUtil;
import net.loomchild.maligna.parser.AlParser;
import net.loomchild.maligna.parser.Parser;
import net.loomchild.maligna.ui.console.command.exception.MissingParameterException;
import net.loomchild.maligna.filter.modifier.modify.split.SplitAlgorithm;
import net.loomchild.maligna.ui.console.command.exception.UnknownParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


public class ModelCommand extends AbstractCommand {
	
	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Modeller class. Valid values are: length, language-translation.");
		options.addOption("i", "iterations", true, "Translation model train iteration count. Optional for language-translation modeller, default " + TranslationModelUtil.DEFAULT_TRAIN_ITERATION_COUNT + ".");
	}
	
	public void run(CommandLine commandLine) {
		String cls = commandLine.getOptionValue('c');
		if (cls == null) {
			throw new MissingParameterException("class");
		} else if (cls.equals("language-translation")) {

			//int iterations = createInt(commandLine, "iterations", 
			//		TranslationModelUtil.DEFAULT_TRAIN_ITERATION_COUNT);
	
			//Prosty algorytm tokenizujący dlatego że wejście powinno być już 
			//stokenizowane - tokeny rozdzielone spacją.
			//TODO: Czy dobrze? nie może być tokenów ze spacją w środku.
			SplitAlgorithm splitAlgorithm = 
				VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM;
			Vocabulary sourceVocabulary = new Vocabulary();
			Vocabulary targetVocabulary = new Vocabulary();
			
			Parser parser = new AlParser(getIn());
			List<Alignment> alignmentList = parser.parse();

			List<List<Integer>> sourceWidList = new ArrayList<List<Integer>>();
			List<List<Integer>> targetWidList = new ArrayList<List<Integer>>();
			for (Alignment alignment : alignmentList) {
				sourceWidList.add(tokenizePutGet(
						alignment.getSourceSegmentList(), 
						sourceVocabulary, splitAlgorithm));
				targetWidList.add(tokenizePutGet(
						alignment.getTargetSegmentList(), 
						targetVocabulary, splitAlgorithm));
			}
			
			//LanguageModel sourceLanguageModel = 
			//	LanguageModelUtil.train(sourceWidList);
			//LanguageModel targetLanguageModel = 
			//	LanguageModelUtil.train(targetWidList);
			//TranslationModel translationModel = 
			//	TranslationModelUtil.train(iterations, sourceWidList, targetWidList);
			
		} else {
			throw new UnknownParameterException("class");
		} 
		
	}
	
	private List<Integer> tokenizePutGet(List<String> segmentList, 
			Vocabulary vocabulary, SplitAlgorithm splitAlgorithm) {
		List<String> wordList = splitAlgorithm.modify(segmentList);
		vocabulary.putWordList(wordList);
		List<Integer> widList = vocabulary.getWidList(wordList);
		return widList;
	}

}
