package net.sourceforge.align.filter.macro;

import net.sourceforge.align.filter.Filter;

/**
 * Represents a macro filter which consists of multiple filtering operations
 * (for example complete alignment using Moore's algorithm - 
 * see {@link MooreMacro}).
 * Created to simplify complex operations and improve the performance.
 * @author loomchild
 */
public interface Macro extends Filter {

}
