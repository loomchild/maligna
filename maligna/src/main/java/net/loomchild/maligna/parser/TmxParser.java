package net.loomchild.maligna.parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.util.bind.tmx.Body;
import net.loomchild.maligna.util.bind.tmx.Seg;
import net.loomchild.maligna.util.bind.tmx.Tmx;
import net.loomchild.maligna.util.bind.tmx.Tuv;
import net.loomchild.maligna.util.bind.TmxMarshallerUnmarshaller;
import net.loomchild.maligna.util.bind.tmx.Tu;

/**
 * Represents TMX document parser.
 * 
 * Each translation unit in TMX is treated as a separate alignment. 
 * From each translation unit only variants in two configured languages are
 * retrieved. If there are no variants in given language the alignment is
 * treated as n to zero, if there is one variant in given language it is 
 * treated as n to one, if there are more than one variant in a given 
 * language an exception is thrown. Ignores translation units that do not
 * contain variants in any of given languages (it would create zero to zero 
 * alignment).
 * 
 * @author loomchild
 */
public class TmxParser implements Parser {
	
	private Reader reader;
	
	private String sourceLanguageCode;

	private String targetLanguageCode;

	/**
	 * Creates TMX document parser.
	 * @param reader Input stream containing TMX document
	 * @param sourceLanguageCode source language code (en, de, pl, etc.)
	 * @param targetLanguageCode target language code
	 */
	public TmxParser(Reader reader, 
			String sourceLanguageCode, String targetLanguageCode) {
		this.reader = reader;
		this.sourceLanguageCode = sourceLanguageCode;
		this.targetLanguageCode = targetLanguageCode;
	}

	/**
	 * Creates TMX parser. Source and target languages are set automatically
	 * if they are the only languages present in input document, 
	 * otherwise exception is thrown. 
	 * Source language code will be lexicographically before target language 
	 * code. 
	 * 
	 * @param reader reader containing TMX document
	 * @throws TmxParseException when document does not contain segments 
	 * 		exactly in two languages 
	 */
	public TmxParser(Reader reader) {
		this.reader = reader;
	}

	/**
	 * Parses TMX document into alignment list.
	 * @return alignment list
	 */
	public List<Alignment> parse() {
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		Tmx tmx = TmxMarshallerUnmarshaller.getInstance().unmarshal(reader);
		Body body = tmx.getBody();
		if (sourceLanguageCode == null && targetLanguageCode == null) {
			initLanguageCodes(body.getTu());
		}
		for (Tu tu : body.getTu()) {
			List<String> sourceSegmentList = 
				createSegmentList(tu, sourceLanguageCode);
			List<String> targetSegmentList = 
				createSegmentList(tu, targetLanguageCode);
			//Ignore empty alignments
			if (sourceSegmentList.size() > 0 || targetSegmentList.size() > 0) {
				Alignment alignment = new Alignment(sourceSegmentList, 
						targetSegmentList);
				alignmentList.add(alignment);
			}
		}
		return alignmentList;
	}

	private List<String> createSegmentList(Tu tu, String languageCode) {
		List<String> segmentList = new ArrayList<String>();
		for (Tuv tuv : tu.getTuv()) {
			if (languageCode.equals(tuv.getLang())) {
				String segment = getSegment(tuv.getSeg());
				segmentList.add(segment);
			}
		}
		if (segmentList.size() > 1) {
			throw new TmxParseException(languageCode + " variant count greater " +
					"than 1");
		}
		return segmentList;
	}
	
	private String getSegment(Seg seg) {
		StringBuilder builder = new StringBuilder();
		for (Object object : seg.getContent()) {
			builder.append(object.toString());
		}
		return builder.toString();
	}
	
	private void initLanguageCodes(List<Tu> tuList) {
		Set<String> languageCodeSet = getLanguageCodeSet(tuList);
		if (languageCodeSet.size() != 2) {
			throw new TmxParseException("Document does not contain units " +
					"exactly in 2 languages");
		}
		String[] languageCodeArray = languageCodeSet.toArray(new String[]{});
		Arrays.sort(languageCodeArray);
		sourceLanguageCode = languageCodeArray[0];
		targetLanguageCode = languageCodeArray[1];
	}
	
	private Set<String> getLanguageCodeSet(List<Tu> tuList) {
		Set<String> languageCodeSet = new HashSet<String>();
		for (Tu tu : tuList) {
			for (Tuv tuv : tu.getTuv()) {
				languageCodeSet.add(tuv.getLang());
			}
		}
		return languageCodeSet;
	}
	
}
