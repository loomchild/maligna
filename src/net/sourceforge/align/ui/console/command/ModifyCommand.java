package net.sourceforge.align.ui.console.command;


import static net.sourceforge.align.model.vocabulary.VocabularyUtil.DEFAULT_MAX_WORD_COUNT;
import static net.sourceforge.align.model.vocabulary.VocabularyUtil.DEFAULT_MIN_OCCURRENCE_COUNT;
import static net.sourceforge.align.model.vocabulary.VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM;
import static net.sourceforge.align.util.Util.getFileInputStream;
import static net.sourceforge.align.util.Util.getReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.meta.FilterDecorators;
import net.sourceforge.align.filter.modifier.Modifier;
import net.sourceforge.align.filter.modifier.modify.ModifyAlgorithm;
import net.sourceforge.align.filter.modifier.modify.NullModifyAlgorithm;
import net.sourceforge.align.filter.modifier.modify.clean.FilterNonWordsCleanAlgorithm;
import net.sourceforge.align.filter.modifier.modify.clean.LowercaseCleanAlgorithm;
import net.sourceforge.align.filter.modifier.modify.clean.TrimCleanAlgorithm;
import net.sourceforge.align.filter.modifier.modify.clean.UnifyRareWordsCleanAlgorithm;
import net.sourceforge.align.filter.modifier.modify.merge.SeparatorMergeAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.ParagraphSplitAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.SentenceSplitAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.SplitAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.SrxSplitAlgorithm;
import net.sourceforge.align.filter.modifier.modify.split.WordSplitAlgorithm;
import net.sourceforge.align.formatter.AlFormatter;
import net.sourceforge.align.formatter.Formatter;
import net.sourceforge.align.model.vocabulary.Vocabulary;
import net.sourceforge.align.model.vocabulary.VocabularyUtil;
import net.sourceforge.align.parser.AlParser;
import net.sourceforge.align.parser.Parser;
import net.sourceforge.align.ui.console.command.exception.MissingParameterException;
import net.sourceforge.align.ui.console.command.exception.ParameterFormatException;
import net.sourceforge.align.ui.console.command.exception.UnknownParameterException;
import net.sourceforge.align.util.Pair;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


public class ModifyCommand extends AbstractCommand {
	
	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Modifier class. Valid values are: merge, split-word, split-sentence, split-paragraph, split-srx, trim, lowercase, filter-non-words, unify-rare-words.");
		options.addOption("p", "part", true, "Affected segment part. Valid values are: both(default), source, target.");
		options.addOption("f", "file", true, "File containing modification information. Required by split-srx modifier.");
		options.addOption("l", "languages", true, "Source and target language separated by comma. Required for split-srx modifier.");
		options.addOption("s", "separator", true, "Merge separator string. Optional for merge modifier, default \"\".");
		options.addOption("w", "max-word-count", true, "Maximum number of words preserved. Optional for unify-rare-words modifier, default " + DEFAULT_MAX_WORD_COUNT + ".");
		options.addOption("o", "min-occurrence-count", true, "Minimum number of occurences to preserve a word. Optional for unify-rare-words modifier, default " + DEFAULT_MIN_OCCURRENCE_COUNT + ".");
		options.addOption("h", "help", false, "Display this help message.");
	}
	
	protected void run(CommandLine commandLine) {
		String cls = commandLine.getOptionValue('c');
		if (cls == null) {
			throw new MissingParameterException("class");
		}
		ModifyAlgorithm sourceAlgorithm;
		ModifyAlgorithm targetAlgorithm = null;
		Parser parser = new AlParser(getIn());
		List<Alignment> alignmentList = null;
		if (cls.equals("split-word")) {
			sourceAlgorithm = new WordSplitAlgorithm();
		} else if (cls.equals("split-sentence")) {
			sourceAlgorithm = new SentenceSplitAlgorithm();
		} else if (cls.equals("split-paragraph")) {
			sourceAlgorithm = new ParagraphSplitAlgorithm();
		} else if (cls.equals("split-srx")) {
			String fileName = commandLine.getOptionValue('f');
			if (fileName == null) {
				throw new MissingParameterException("file");
			}
			String languages = commandLine.getOptionValue('l');
			if (languages == null) {
				throw new MissingParameterException("languages");
			}
			String[] languageArray = languages.split(",");
			if (languageArray.length != 2) {
				throw new ParameterFormatException("languages");
			}
			Reader reader = getReader(getFileInputStream(fileName));
			sourceAlgorithm = new SrxSplitAlgorithm(reader, languageArray[0]);
			reader = getReader(getFileInputStream(fileName));
			targetAlgorithm = new SrxSplitAlgorithm(reader, languageArray[1]);
		} else if (cls.equals("merge")) {
			String separator = commandLine.getOptionValue('s');
			if (separator == null) {
				sourceAlgorithm = new SeparatorMergeAlgorithm();
			} else {
				separator = separator.replaceAll("\\\\t", "\t");
				separator = separator.replaceAll("\\\\n", "\n");
				sourceAlgorithm = new SeparatorMergeAlgorithm(separator);
			}
		} else if (cls.equals("trim")) {
			sourceAlgorithm = new TrimCleanAlgorithm();
		} else if (cls.equals("lowercase")) {
			sourceAlgorithm = new LowercaseCleanAlgorithm();
		} else if (cls.equals("filter-non-words")) {
			sourceAlgorithm = new FilterNonWordsCleanAlgorithm();
		} else if (cls.equals("unify-rare-words")) {
			alignmentList = parser.parse();
			Pair<ModifyAlgorithm, ModifyAlgorithm> algorithmPair =
				createUnifyRareWordsAlgorithms(commandLine, alignmentList);
			sourceAlgorithm = algorithmPair.first;
			targetAlgorithm = algorithmPair.second;
		} else {
			throw new UnknownParameterException("class");
		}
		String part = commandLine.getOptionValue('p');
		if (part == null) {
			part = "both";
		}
		if (part.equals("both")) {
			if (targetAlgorithm == null) {
				targetAlgorithm = sourceAlgorithm;
			}
		} else if (part.equals("source")) {
			targetAlgorithm = new NullModifyAlgorithm();
		} else if (part.equals("target")) {
			sourceAlgorithm = new NullModifyAlgorithm();
		} else {
			throw new UnknownParameterException("part");
		}
		
		Formatter formatter = new AlFormatter(getOut());
		
		Filter filter = new Modifier(sourceAlgorithm, targetAlgorithm);
		filter = FilterDecorators.decorate(filter);

		if (alignmentList == null) {
			alignmentList = parser.parse();
		}
		
		alignmentList = filter.apply(alignmentList);
		formatter.format(alignmentList);
	}
	
	private Pair<ModifyAlgorithm, ModifyAlgorithm> createUnifyRareWordsAlgorithms(
			CommandLine commandLine, List<Alignment> alignmentList) {

		SplitAlgorithm splitAlgorithm = DEFAULT_TOKENIZE_ALGORITHM;
		int maxWordCount = createInt(commandLine, "max-word-count", 
				DEFAULT_MAX_WORD_COUNT);
		int minOccurenceCount = createInt(commandLine, "min-occurrence-count", 
				DEFAULT_MIN_OCCURRENCE_COUNT);

		Vocabulary sourceVocabulary = new Vocabulary();
		Vocabulary targetVocabulary = new Vocabulary();
		List<List<Integer>> sourceWidList = new ArrayList<List<Integer>>(); 
		List<List<Integer>> targetWidList = new ArrayList<List<Integer>>();

		VocabularyUtil.tokenize(splitAlgorithm, alignmentList, 
				sourceVocabulary, targetVocabulary, 
				sourceWidList, targetWidList);

		sourceVocabulary = VocabularyUtil.createTruncatedVocabulary(
				sourceWidList, sourceVocabulary, 
				maxWordCount, minOccurenceCount);
		targetVocabulary = VocabularyUtil.createTruncatedVocabulary(
				targetWidList, targetVocabulary, 
				maxWordCount, minOccurenceCount);

		ModifyAlgorithm sourceAlgorithm = 
			new UnifyRareWordsCleanAlgorithm(sourceVocabulary);
		ModifyAlgorithm targetAlgorithm = 
			new UnifyRareWordsCleanAlgorithm(targetVocabulary);

		return new Pair<ModifyAlgorithm, ModifyAlgorithm>(
				sourceAlgorithm, targetAlgorithm);
	}

}
