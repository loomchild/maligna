package net.sourceforge.align.formatter;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import net.sourceforge.align.coretypes.Alignment;

/**
 * Represents HTML formatter that produces human-readable output.
 * 
 * The output is stored using configured writer in two columns of HTML table.
 * 
 * @author loomchild
 */
public class HtmlFormatter implements Formatter {
	
	private PrintWriter writer;
	
	/**
	 * Constructs a formatter
	 * @param writer writer that will be used as output
	 */
	public HtmlFormatter(Writer writer) {
		this.writer = new PrintWriter(writer, true);
	}

	/**
	 * Formats the alignment into full HTML page containing a table with
	 * two columns representing source and target texts.
	 * 
	 * @param alignmentList input alignment list
	 */
	public void format(List<Alignment> alignmentList) {
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
		writer.println("</head>");

		writer.println("<body>");
		writer.println("<table border=\"1\" cellpadding=\"5\">");
		for (Alignment alignment : alignmentList) {
			writer.println("<tr>");
			formatStrings(alignment.getSourceSegmentList());
			formatStrings(alignment.getTargetSegmentList());
			writer.println("</tr>");
		}
		writer.println("</table>");
		writer.println("</body>");
		writer.println("</html>");
	}
		
	private void formatStrings(List<String> stringList) {
		writer.println("<td>");
		for (String string : stringList) {
			writer.println("<p>" + string + "</p>");
		}
		writer.println("</td>");
	}
	
}
