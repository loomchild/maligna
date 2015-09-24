package net.sourceforge.align.filter.aligner;

import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.onetoone.OneToOneAlgorithm;


/**
 * Represents an exception used by {@link AlignAlgorithm} to indicate that text 
 * alignment is impossible.
 * 
 * It is thrown for example when using {@link OneToOneAlgorithm} but the text
 * does not have equal number of source and target alignments, text is too
 * short to apply given algorithm, segment counts are different
 * in reference and input alignments in {@link UnifyAligner}, etc.
 *
 * @author Jarek Lipski (loomchild)
 */
public class AlignmentImpossibleException extends RuntimeException {

	private static final long serialVersionUID = 101L; 

	/**
	 * Creates an exception.
	 * @param message
	 */
	public AlignmentImpossibleException(String message) {
		super(message);
	}

}
