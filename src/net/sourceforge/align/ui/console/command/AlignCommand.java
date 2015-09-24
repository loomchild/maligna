package net.sourceforge.align.ui.console.command;

import static net.sourceforge.align.util.Util.getFileInputStream;
import static net.sourceforge.align.util.Util.getReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.calculator.content.OracleCalculator;
import net.sourceforge.align.calculator.content.TranslationCalculator;
import net.sourceforge.align.calculator.length.NormalDistributionCalculator;
import net.sourceforge.align.calculator.length.PoissonDistributionCalculator;
import net.sourceforge.align.calculator.length.counter.CharCounter;
import net.sourceforge.align.calculator.length.counter.Counter;
import net.sourceforge.align.calculator.length.counter.SplitCounter;
import net.sourceforge.align.calculator.meta.CompositeCalculator;
import net.sourceforge.align.calculator.meta.MinimumCalculator;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.coretypes.CategoryDefaults;
import net.sourceforge.align.filter.Filter;
import net.sourceforge.align.filter.aligner.Aligner;
import net.sourceforge.align.filter.aligner.UnifyAligner;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.hmm.adaptive.AdaptiveBandAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.hmm.viterbi.ViterbiAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.viterbi.ViterbiAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.onetoone.OneToOneAlgorithm;
import net.sourceforge.align.filter.meta.FilterDecorators;
import net.sourceforge.align.formatter.AlFormatter;
import net.sourceforge.align.formatter.Formatter;
import net.sourceforge.align.matrix.BandMatrixFactory;
import net.sourceforge.align.matrix.FullMatrixFactory;
import net.sourceforge.align.matrix.MatrixFactory;
import net.sourceforge.align.model.language.LanguageModel;
import net.sourceforge.align.model.language.LanguageModelUtil;
import net.sourceforge.align.model.translation.TranslationModel;
import net.sourceforge.align.model.translation.TranslationModelUtil;
import net.sourceforge.align.model.vocabulary.Vocabulary;
import net.sourceforge.align.model.vocabulary.VocabularyUtil;
import net.sourceforge.align.parser.AlParser;
import net.sourceforge.align.parser.Parser;
import net.sourceforge.align.ui.console.command.exception.MissingParameterException;
import net.sourceforge.align.ui.console.command.exception.ParameterFormatException;
import net.sourceforge.align.ui.console.command.exception.UnknownParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;



public class AlignCommand extends AbstractCommand {
	
	protected void initOptions(Options options) {
		options.addOption("c", "class", true, "Algorithm class. Valid values are: viterbi, fb, one-to-one, unify.");
		options.addOption("o", "one", false, "Strict one to one alignment.");
		options.addOption("s", "search", true, "Search method. Valid values are: exhaustive, band, iterative-band. Required by viterbi and fb algorithms.");
		options.addOption("r", "radius", true, "Band radius in segments. Optional for band, iterband search method, default " + BandMatrixFactory.DEFAULT_BAND_RADIUS + ".");
		options.addOption("e", "increment", true, "Band increment ratio in each pass. Optional for iterband search method, default " + AdaptiveBandAlgorithm.DEFAULT_BAND_INCREMENT_RATIO + ".");
		options.addOption("m", "margin", true, "Band minimum acceptable margin. Optional for iterband search method, default " + AdaptiveBandAlgorithm.DEFAULT_MIN_BAND_MARGIN + ".");
		options.addOption("a", "calculator", true, "Calculator classes separated by commas. Valid values are: normal, poisson, translation, oracle. Required by viterbi and fb algorithms.");
		options.addOption("n", "counter", true, "Length counter, Valid values are: char, word. Required by normal and poisson calculators.");
		options.addOption("l", "length-corpus", true, "Length model training corpus. Optional for poisson calculator.");
		options.addOption("t", "translation-corpus", true, "Translation model training corpus. Optional for translation calculator.");
		options.addOption("d", "oracle-corpus", true, "Oracle calculator corpus. Required by oracle calculator.");
		options.addOption("x", "language-models", true, "Source and target language model separated by comma. Optional for translation calculator.");
		options.addOption("y", "translation-model", true, "Translation model. Optional for translation calculator.");
		options.addOption("i", "iterations", true, "Translation model train iteration count. Optional for translation calculator, default " + TranslationModelUtil.DEFAULT_TRAIN_ITERATION_COUNT + ".");
		options.addOption("u", "unification-corpus", true, "Unification reference corpus. Required by unify algorithm.");
	}
	
	protected void run(CommandLine commandLine) {
		if (commandLine.getOptions().length == 0) {
			throw new MissingParameterException("class");
		}
		Filter filter;
		Parser parser = new AlParser(getIn());
		List<Alignment> alignmentList = null;
		String cls = commandLine.getOptionValue('c');
		if (cls == null) {
			throw new MissingParameterException("class");
		}
		if (cls.equals("unify")) {
			String unificationCorpus = commandLine.getOptionValue('u');
			if (unificationCorpus == null) {
				throw new MissingParameterException("unification-corpus");
			}
			List<Alignment> unificationAlignmentList = 
				loadAlignmentList(unificationCorpus);
			filter = new UnifyAligner(unificationAlignmentList);
		} else {
			alignmentList = parser.parse();
			AlignAlgorithm algorithm = createAlgorithm(commandLine, alignmentList);
			filter = new Aligner(algorithm);
		}
		filter = FilterDecorators.decorate(filter);
		Formatter formatter = new AlFormatter(getOut());
		if (alignmentList == null) {
			alignmentList = parser.parse();
		}
		alignmentList = filter.apply(alignmentList);
		formatter.format(alignmentList);
	}
	
	private AlignAlgorithm createAlgorithm(CommandLine commandLine,
			List<Alignment> alignmentList) {
		String cls = commandLine.getOptionValue('c');
		AlignAlgorithm algorithm;
		if (cls.equals("fb") || cls.equals("viterbi")) {
			Calculator calculator = createCalculator(commandLine, 
					alignmentList);
			Map<Category, Float> categoryMap = 
				CategoryDefaults.BEST_CATEGORY_MAP;
			String search = commandLine.getOptionValue('s');
			if (search == null) {
				throw new MissingParameterException("search");
			}
			if (search.equals("exhaustive") || search.equals("band")) {
				MatrixFactory matrixFactory;
				if (search.equals("exhaustive")) {
					matrixFactory = new FullMatrixFactory();
				} else if (search.equals("band")) {
					int radius = createInt(commandLine, "radius", 
							BandMatrixFactory.DEFAULT_BAND_RADIUS);
					matrixFactory = new BandMatrixFactory(radius);
				} else {
					throw new UnknownParameterException("search");
				}
				if (cls.equals("viterbi")) {
					algorithm = new ViterbiAlgorithm(calculator, 
							categoryMap, matrixFactory);
				} else if (cls.equals("fb")) {
					algorithm = new ForwardBackwardAlgorithm(calculator, 
							categoryMap, matrixFactory);
				} else {
					throw new UnknownParameterException("class");
				}			
			} else if (search.equals("iterative-band")) {
				int radius = createInt(commandLine, "radius", 
						BandMatrixFactory.DEFAULT_BAND_RADIUS);
				int margin = createInt(commandLine, "margin", 
						AdaptiveBandAlgorithm.DEFAULT_MIN_BAND_MARGIN);
				float increment = createFloat(commandLine, "increment",
						AdaptiveBandAlgorithm.DEFAULT_BAND_INCREMENT_RATIO);
				HmmAlignAlgorithmFactory algorithmFactory;
				if (cls.equals("viterbi")) {
					algorithmFactory = new ViterbiAlgorithmFactory();
				} else if (cls.equals("fb")) {
					algorithmFactory = new ForwardBackwardAlgorithmFactory();
				} else {
					throw new UnknownParameterException("class");
				}
				algorithm = new AdaptiveBandAlgorithm(algorithmFactory,
						calculator, radius, increment, margin, 
						categoryMap);
			} else {
				throw new UnknownParameterException("search");
			}
		} else if (cls.equals("one-to-one")) {
			boolean one = commandLine.hasOption('o');
			algorithm = new OneToOneAlgorithm(one);
		} else {
			throw new UnknownParameterException("class");
		}
		return algorithm;
	}
		
	private Counter createCounter(CommandLine commandLine) {
		String ctr = commandLine.getOptionValue('n');
		Counter counter;
		if (ctr == null) {
			counter = null;
		} else if (ctr.equals("word")) {
			counter = new SplitCounter();
		} else if (ctr.equals("char")) {
			counter = new CharCounter();
		} else {
			throw new UnknownParameterException("counter");
		}
		return counter;
	}
	
	private Calculator createCalculator(CommandLine commandLine, 
			List<Alignment> alignmentList) {
		String calculatorString = commandLine.getOptionValue('a');
		if (calculatorString == null) {
			throw new MissingParameterException("calculator");
		}
		List<String> calculatorStringList = 
			Arrays.asList(calculatorString.split(","));
		return createCalculator(commandLine, alignmentList, calculatorStringList);
	}
	
	private Calculator createCalculator(CommandLine commandLine, 
			List<Alignment> alignmentList, List<String> calculatorStringList) {

		List<Calculator> calculatorList = new ArrayList<Calculator>();
		Iterator<String> calculatorStringIterator = calculatorStringList.iterator();
		while (calculatorStringIterator.hasNext()) {
			String calculatorString = calculatorStringIterator.next();
			Calculator calculator;
			if (calculatorString.equals("normal")) {
				calculator = createNormalCalculator(commandLine);
			} else if(calculatorString.equals("poisson")) {
				calculator = createPoissonCalculator(commandLine, alignmentList);
			} else if(calculatorString.equals("translation")) {
				calculator = createTranslationCalculator(commandLine);
			} else if(calculatorString.equals("oracle")) {
				List<String> remainingCalculatorStringList = 
					new ArrayList<String>();
				while (calculatorStringIterator.hasNext()) {
					remainingCalculatorStringList.add(calculatorStringIterator.next());
				}
				Calculator remainingCalculator = createCalculator(
						commandLine, alignmentList, remainingCalculatorStringList);
				calculator = createOracleCalculator(commandLine, remainingCalculator);
			} else {
				throw new UnknownParameterException("calculator");
			}
			calculatorList.add(calculator);
		}
		
		Calculator calculator;
		if (calculatorList.size() == 1) {
			calculator = calculatorList.get(0);
		} else {
			calculator = new CompositeCalculator(calculatorList);
		}
		return calculator;
	}
	
	
	private Calculator createNormalCalculator(CommandLine commandLine) {
		Counter counter = createCounter(commandLine);
		if (counter == null) {
			throw new MissingParameterException("counter");
		}
		Calculator calculator = new NormalDistributionCalculator(counter);
		return calculator;
	}

	private Calculator createPoissonCalculator(CommandLine commandLine, 
			List<Alignment> alignmentList) {
		Counter counter = createCounter(commandLine);
		if (counter == null) {
			throw new MissingParameterException("counter");
		}
		String lengthCorpus = commandLine.getOptionValue('l');
		List<Alignment> lengthAlignmentList; 
		if (lengthCorpus != null) {
			lengthAlignmentList = loadAlignmentList(lengthCorpus);
		} else {
			lengthAlignmentList = alignmentList;
		}
		Calculator calculator = new PoissonDistributionCalculator(counter, 
				lengthAlignmentList);
		return calculator;
	}

	private Calculator createTranslationCalculator(CommandLine commandLine) {
		Calculator calculator;
		int iterations = createInt(commandLine, "iterations", 
				TranslationModelUtil.DEFAULT_TRAIN_ITERATION_COUNT);
		String translationCorpus = commandLine.getOptionValue('t');
		String languageModels = commandLine.getOptionValue("x");
		String transModel = commandLine.getOptionValue("y");
		
		Vocabulary sourceVocabulary = new Vocabulary();
		Vocabulary targetVocabulary = new Vocabulary();
		List<List<Integer>> sourceWidList = new ArrayList<List<Integer>>();
		List<List<Integer>> targetWidList = new ArrayList<List<Integer>>();

		if (translationCorpus != null) {
			List<Alignment> translationAlignmentList = loadAlignmentList(translationCorpus); 
			VocabularyUtil.tokenize(VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM, 
					translationAlignmentList, sourceVocabulary, 
					targetVocabulary, sourceWidList, targetWidList);
		}

		LanguageModel sourceLanguageModel = null;
		LanguageModel targetLanguageModel = null; 
		if (languageModels != null) {
			String[] languageModelArray = languageModels.split(",");
			if (languageModelArray.length != 2) {
				throw new ParameterFormatException("language-models");
			}
			sourceLanguageModel = loadLanguageModel(languageModelArray[0]);
			targetLanguageModel = loadLanguageModel(languageModelArray[1]);			
		} else {
			sourceLanguageModel = 
				LanguageModelUtil.train(sourceWidList);
			targetLanguageModel = 
				LanguageModelUtil.train(targetWidList);
		}

		TranslationModel translationModel = null;
		if (transModel != null) {
			translationModel = loadTranslationModel(
					transModel, sourceVocabulary, targetVocabulary);
		} else {
			translationModel = TranslationModelUtil.train(
					iterations, sourceWidList, targetWidList);
		}

		calculator = new TranslationCalculator(sourceVocabulary, 
					targetVocabulary, sourceLanguageModel, targetLanguageModel,
					translationModel, VocabularyUtil.DEFAULT_TOKENIZE_ALGORITHM);
		
		return calculator;
	}

	private Calculator createOracleCalculator(CommandLine commandLine,
			Calculator calculator) {
		Calculator resultCalculator;
		String oracleCorpus = commandLine.getOptionValue("oracle-corpus");
		if (oracleCorpus == null) {
			throw new MissingParameterException("oracle-corpus");
		}
		List<Alignment> oracleAlignmentList = loadAlignmentList(oracleCorpus);
		resultCalculator = new MinimumCalculator(new OracleCalculator(oracleAlignmentList), 
				calculator, OracleCalculator.DEFAULT_SUCCESS_SCORE);
		return resultCalculator;
		
	}

	private LanguageModel loadLanguageModel(String fileName) {
		Reader reader = getReader(getFileInputStream(fileName));
		return LanguageModelUtil.parse(reader);
	}

	private TranslationModel loadTranslationModel(String fileName, 
			Vocabulary sourceVocabulary, Vocabulary targetVocabulary) {
		Reader reader = getReader(getFileInputStream(fileName));
		return TranslationModelUtil.parse(reader, sourceVocabulary, 
				targetVocabulary);
	}
	
	private List<Alignment> loadAlignmentList(String fileName) {
		Reader reader = getReader(getFileInputStream(fileName));
		Parser parser = new AlParser(reader);
		return parser.parse();
	}

}
