package net.loomchild.maligna.parser;

import static net.loomchild.maligna.util.Util.readAll;

import java.io.Reader;
import java.util.Collections;
import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;

/**
 * Represents plaintext document parser into an alignment.
 * 
 * Can be constructed from strings as well as files.
 * The whole content of each input file (STRING) is treated as a single segment. 
 * Always returns alignment list containing just one alignment.
 * 
 * @author loomchild
 */
public class PlaintextParser implements Parser {
	
	private Reader sourceReader;
	
	private Reader targetReader;
	
	private String sourceString;
	
	private String targetString;
	

	/**
	 * Constructs a parser from source and target string.
	 * @param sourceString source segment
	 * @param targetString target segment
	 */
	public PlaintextParser(String sourceString, String targetString) {
		this.sourceString = sourceString;
		this.targetString = targetString;
	}
	
	/**
	 * Constructs parser from source and target reader.
	 * @param sourceReader source text document
	 * @param targetReader target text document
	 */
	public PlaintextParser(Reader sourceReader, Reader targetReader) {
		this.sourceReader = sourceReader;
		this.targetReader = targetReader;
	}

	/**
	 * Parses input documents into an alignment list
	 * @return alignment list containing just one alignment with one source 
	 * 		and one target segment
	 */
	public List<Alignment> parse() {
		if (sourceString == null) {
			sourceString = readAll(sourceReader);
		}
		if (targetString == null) {
			targetString = readAll(targetReader);
		}

		List<String> sourceSegmentList = Collections.singletonList(sourceString);
		List<String> targetSegmentList = Collections.singletonList(targetString);
		Alignment alignment = new Alignment(sourceSegmentList, targetSegmentList);
		List<Alignment> alignmentList = Collections.singletonList(alignment);
		
		return alignmentList;
	}

}
