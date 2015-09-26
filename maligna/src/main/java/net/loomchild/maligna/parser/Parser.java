package net.loomchild.maligna.parser;

import java.util.List;

import net.loomchild.maligna.coretypes.Alignment;


/**
 * Represents input text(s) parser that creates alignment list.
 * Input file or files are configured in constructor of a concrete parser
 * implementation.
 * 
 * @author loomchild
  */
public interface Parser {

	/**
	 * Parses input document into an alignment list.
	 * @return parsed alignment list
	 */
	public List<Alignment> parse();
	
}
