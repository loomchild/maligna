package net.loomchild.maligna.formatter;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;


/**
 * Represents a alignment list formatter / writer.
 * Responsible for writing alignment list in specific format. 
 * The output location (file, set of files) are defined in individual subclasses
 * and should be configured in class constructor.
 *
 * @author Jarek Lipski (loomchild)
 */
public interface Formatter {

	/**
	 * Formats alignment list to previously defined location depending 
	 * on concrete implementation.
	 * @param alignmentList alignment list
	 */
	public void format(List<Alignment> alignmentList);

}
