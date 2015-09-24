package net.sourceforge.align.model;

import net.sourceforge.align.model.language.LanguageTestSuite;
import net.sourceforge.align.model.length.LengthTestSuite;
import net.sourceforge.align.model.translation.TranslationTestSuite;
import net.sourceforge.align.model.vocabulary.VocabularyTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test suite for model package.
 * @author loomchild
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	VocabularyTestSuite.class, LengthTestSuite.class, 
	LanguageTestSuite.class, TranslationTestSuite.class
})
public class ModelTestSuite {
	
}
