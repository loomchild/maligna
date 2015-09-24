package net.sourceforge.align.filter.modifier.modify.split;

import java.io.IOException;
import java.io.Reader;

import net.sourceforge.align.util.IORuntimeException;
import net.sourceforge.align.util.ImpossibleException;


/**
 * Represents simple sentence splitter.
 * Split a text after end-of-line character and after end of sentence 
 * character (.?!) if the next characters are whitespace and a capital letter.
 * Works on a character stream. 
 *
 * @author loomchild
 */
public class SimpleSplitter {

	/**
	 * End of sentence characters.
	 */
	public static final char[] BREAK_CHARACTER_LIST = {'.', '!', '?'};

	public enum State {READY, AFTER_BREAK, AFTER_SPACE, END};
	
	/**
	 * Initializes splitter.
	 * @param reader input stream containing the text to be splitted
	 */
	public SimpleSplitter(Reader reader) {
		this.reader = reader;
		this.state = State.READY;
		this.builder = new StringBuilder();
		this.segment = null;
	}

	/**
	 * Iterates to the next segment.
	 * @return next segment or null if there are no more segments
	 */
	public String next() {
		if (hasNext()) {
			readSegment(true);
			String newSegment = segment;
			segment = null;
			return newSegment;
		} else {
			return null;
		}
	}

	/**
	 * @return false if there are no more segments
	 */
	public boolean hasNext() {
		return (state != State.END) || (segment != null);
	}
	
	/**
	 * @return true if subsequent {@link #next()} call will not block the
	 * 		thread waiting for more characters from input stream
	 * @throws IORuntimeException when IO error reading stream occurs
	 */
	public boolean isReady() {
		if (hasNext()) {
			return (segment != null) || (readSegment(false));
		} else {
			return false;
		}
	}

	/**
	 * Reads next segment.
	 * 
	 * @param canBlock if this is true then this method cannot block the
	 * 		thread waiting for next character from a stream
	 * @return false if it cannot read more characters because it would 
	 * 		block the stream
	 * @throws IORuntimeException when IO error reading input stream occurs
	 */
	private boolean readSegment(boolean canBlock) {
		try {
			if (segment != null) {
				return true;
			}
			int leftCharacters = 0;
			while (state != State.END) {
				if (!(canBlock || reader.ready())) {
					return false;
				}
				int readResult = reader.read();
				if (readResult == -1) {
					state = State.END;
					leftCharacters = 0;
				} else {
					char character = (char)readResult;
					builder.append(character);
					if (character == '\n') {
						state = State.READY;
						leftCharacters = 0;
						break;
					} else if (state == State.READY) {
						if (isBreakCharacter(character)) {
							state = State.AFTER_BREAK;
						}
					} else if (state == State.AFTER_BREAK) {
						if (Character.isWhitespace(character)) {
							++leftCharacters;
							state = State.AFTER_SPACE;
						} else if (isBreakCharacter(character)) {
							//Do nothing
						} else {
							state = State.READY;
							leftCharacters = 0;
						}
					} else if (state == State.AFTER_SPACE) {
						if (Character.isUpperCase(character)) {
							state = State.READY;
							++leftCharacters;
							break;
						} else if (isBreakCharacter(character)) {
							state = State.AFTER_BREAK;
							++leftCharacters;
							break;
						} else if (Character.isWhitespace(character)) {
							++leftCharacters;
						} else {
							state = State.READY;
							leftCharacters = 0;
						}
					} else {
						throw new ImpossibleException(
						"Impossible SimpleSplitter state.");
					}
				}
			}
			segment = separateString(leftCharacters);	
			return true;
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * Retrieves a string from a buffer leaving given number of characters
	 * in the buffer (string length is equal to buffer length - leftCharacters).
	 * Removes the returned string from a buffer.
	 * @param leftCharacters how many characters to leave in the buffer
	 * @return retrieved segment
	 */
	private String separateString(int leftCharacters) {
		String result = builder.substring(0, builder.length() - leftCharacters);
		builder.delete(0, builder.length() - leftCharacters);
		return result;
	}

	/**
	 * @param character
	 * @return true if given character is end of sentence character 
	 * 		(is present on {@link #BREAK_CHARACTER_LIST}).
	 */
	private boolean isBreakCharacter(char character) {
		for (char ch : BREAK_CHARACTER_LIST) {
			if (ch == character) {
				return true;
			}
		}
		return false;
	}
	
	private Reader reader;
	
	private State state;
	
	private StringBuilder builder;
	
	private String segment;

}
