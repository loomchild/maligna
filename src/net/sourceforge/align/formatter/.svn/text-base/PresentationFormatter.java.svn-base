package net.sourceforge.align.formatter;

import static java.lang.Math.min;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;

/**
 * Represents a plaintext formatter that produces human-readable output.
 * 
 * The output is stored using configured writer in two columns
 * representing source and target texts.
 * 
 * @author loomchild
 */
public class PresentationFormatter implements Formatter {
	
	public static final int DEFAULT_WIDTH = 79;
	
	public static final int MIN_WIDTH = 5;
	
	private PrintWriter writer;
	
	private int width;
	
	private int maxLength;
	
	private String emptyString;
	
	/**
	 * Constructs the formatter.
	 * @param writer writer that will be used as output
	 * @param width output text width (usually console width)
	 */
	public PresentationFormatter(Writer writer, int width) {
		if (width < MIN_WIDTH) {
			throw new IllegalArgumentException("Width is: " + width + 
					" and must be at least: " + MIN_WIDTH);
		}
		this.writer = new PrintWriter(writer, true);
		this.width = width;
		this.maxLength = (this.width - 3) / 2;
		this.emptyString = buildString(' ', maxLength);
	}

	/**
	 * Constructs the formatter with width equal to {@link #DEFAULT_WIDTH}}.
	 * @param writer writer that will be used as output
	 */
	public PresentationFormatter(Writer writer) {
		this(writer, DEFAULT_WIDTH);
	}

	/**
	 * Formats the alignment list using configured writer. The output
	 * is stored in two columns. Total width of both columns including frames
	 * is defined in constructor.
	 * @see #PresentationFormatter(Writer, int)
	 * 
	 * @param alignmentList input alignment list
	 */
	public void format(List<Alignment> alignmentList) {
		int index = 0;
		for (Alignment alignment : alignmentList) {
			List<String> sourceList = 
					splitStringList(alignment.getSourceSegmentList(), maxLength);
			List<String> targetList = 
					splitStringList(alignment.getTargetSegmentList(), maxLength);
			Iterator<String> sourceIterator = sourceList.iterator();
			Iterator<String> targetIterator = targetList.iterator();
			while(sourceIterator.hasNext() && targetIterator.hasNext()) {
				String string = formatString(sourceIterator.next(), 
						targetIterator.next(), maxLength);
				writer.println(string);
			}
			while(sourceIterator.hasNext()) {
				String string = formatString(sourceIterator.next(), emptyString, 
						maxLength);
				writer.println(string);
			}
			while(targetIterator.hasNext()) {
				String string = formatString(emptyString, targetIterator.next(), 
						maxLength);
				writer.println(string);
			}
			if (index < alignmentList.size() - 1) {
				writer.print(buildString('_', maxLength + 1));
				writer.print('|');
				writer.println(buildString('_', maxLength + 1));
			}
			++index;
		}
	}
	
	private List<String> splitStringList(List<String> stringList, 
			int maxLength) {
		List<String> splitStringList = new ArrayList<String>();
		int index = 0;
		for (String string : stringList) {
			splitStringList.addAll(splitString(string, maxLength));
			if (index < stringList.size() - 1) {
				splitStringList.add(emptyString);
			}
			++index;
		}
		return splitStringList;
	}
	
	private List<String> splitString(String string, int maxLength) {
		List<String> stringList = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		for (int position = 0; position < string.length(); ++position) {
			char ch = string.charAt(position);
			if (ch == '\n') {
				int eolWidth = maxLength - builder.length();
				builder.append(buildString(' ', eolWidth));
			} else if (ch == '\t') {
				int tabWidth = min(4, maxLength - builder.length());
				builder.append(buildString(' ', tabWidth));
			} else {
				builder.append(ch);
			}
			if (builder.length() >= maxLength) {
				String line = builder.substring(0, maxLength);
				stringList.add(line);
				builder.delete(0, maxLength);
			}
		}
		if (builder.length() > 0) {
			builder.append(buildString(' ', maxLength - builder.length()));
			stringList.add(builder.toString());
		}
		return stringList;
	}

	private String formatString(String sourceString, String targetString, 
			int maxLength) {
		return sourceString + " | " + targetString;
	}
	
	private String buildString(char character, int length) {
		StringBuilder builder = new StringBuilder();
		builder.setLength(length);
		for (int i = 0; i < length; ++i) {
			builder.setCharAt(i, character);
		}
		return builder.toString();
	}


}
