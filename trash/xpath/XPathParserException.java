package align.parser;

import loomchild.util.exceptions.LogicException;

/**
 * Reprezentuje wyjątek mówiący o tym że dopasowanie tekstów jest niemożliwe.
 * Zgłaszany np. gdy teksty tak różnią się długością że nie można ich dopasować,
 * bo maksymalne dopasowanie to 1-1 albo tekst jest za krótki by
 * zastosować dany algorytm. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class XPathParserException extends LogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586040420857957984L;

	/**
	 * Tworzy wyjątek.
	 * @param message Wiadomość.
	 */
	public XPathParserException(String message) {
		super(message);
	}


	public XPathParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
