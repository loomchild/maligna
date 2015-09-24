
package net.sourceforge.align.filter.aligner.align.hmm.viterbi;

import static net.sourceforge.align.filter.aligner.align.hmm.Util.elementExists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.matrix.Matrix;
import net.sourceforge.align.matrix.MatrixFactory;
import net.sourceforge.align.matrix.MatrixIterator;
import net.sourceforge.align.progress.ProgressManager;
import net.sourceforge.align.progress.ProgressMeter;


/**
 * Represents alignment algorithm which uses Viterbi algorithm.
 * In simple words it finds a path in alignment matrix (representing
 * all possible alignments) with maximum total probability.
 * 
 * This algorithm is independent of method of calculating the individual
 * alignment probabilities (see {@link Calculator} and the way the 
 * matrix is iterated (some irrelevant elements may be omitted by 
 * {@link MatrixIterator}, implemented by a {@link Matrix}). 
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Viterbi_algorithm">Viterbi Algorithm</a>
 * 
 * @see "A Tutorial on Hidden Markov Models and Selected 
 * 		Applications in Speech Recognition, Lawrence R. Rabiner"
 * 
 * @author loomchild
 *
 */
public class ViterbiAlgorithm implements AlignAlgorithm {
	
	private Map<Category, Float> categoryMap;
	
	private Calculator calculator;

	private MatrixFactory matrixFactory;
	
	/**
	 * Creates an algorithm. 
	 * @param calculator probability calculator
	 * @param categoryMap possible alignment categories with their probabilities
	 * @param matrixFactory factory creating two dimensional matrices
	 */
	public ViterbiAlgorithm(Calculator calculator, 
			Map<Category, Float> categoryMap, MatrixFactory matrixFactory) {
		this.matrixFactory = matrixFactory;
		this.calculator = calculator;
		this.categoryMap = categoryMap;
	}

	/**
	 * Aligns by iterating over the whole matrix created by 
	 * {@link MatrixFactory} (iteration can omit some elements depending on 
	 * matrix implementation) and populating it with {@link ViterbiData} 
	 * elements (obtained by calling 
	 * {@link #createData(int, int, List, List, Matrix)}). After the matrix
	 * is populated calls {@link #backtrace(List, List, Matrix)} to retrieve
	 * the most probable alignment from the matrix. 
	 */
	public List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		
		Matrix<ViterbiData> matrix = matrixFactory.createMatrix(
				sourceSegmentList.size() + 1, targetSegmentList.size() + 1);

		ProgressMeter progress = new ProgressMeter("Viterbi Align", matrix.getSize());
		ProgressManager.getInstance().registerProgressMeter(progress);
		
		MatrixIterator<ViterbiData> iterator = matrix.getIterator();
		while(iterator.hasNext()) {
			iterator.next();
			int x = iterator.getX();
			int y = iterator.getY();
			ViterbiData data = createData(x, y,	sourceSegmentList, 
					targetSegmentList, matrix);
			matrix.set(x, y, data);
			
			progress.completeTask();
		}
		
		List<Alignment> alignmentList = backtrace(sourceSegmentList,
				targetSegmentList, matrix);
		
		ProgressManager.getInstance().unregisterProgressMeter(progress);
		
		return alignmentList;
	}
	
	/**
	 * <p>Creates {@link ViterbiData} object at (sourceNr, targetNr) position 
	 * on the matrix. To do it calculates most probable path to this element
	 * by checking all possible ways (categories) to reach it from previously 
	 * calculated data objects (to the left or up, because they are calculated 
	 * before current one) and adds new alignment score 
	 * (calculated with {@link Calculator}).</p> 
	 * 
	 * <p>When sourceNr == 0 and targetNr == 0 (upper left
	 * corner), then zero-to-zero category alignment is created. 
	 * Does not insert created object into the matrix.</p>
	 * 
	 * @param sourceNr source segment position in new alignment (x position)
	 * @param targetNr target segment position in new alignment (y position)
	 * @param sourceSegmentList list of source segments
	 * @param targetSegmentList list of target segments
	 * @param matrix matrix where the data object will be later stored, used
	 * 		to calculate best alignment and total score
	 * @return data object, should never return null
	 */
	private ViterbiData createData(int sourceNr, int targetNr, 
			List<String> sourceSegmentList, List<String> targetSegmentList, 
			Matrix<ViterbiData> matrix) {
		if (sourceNr == 0 && targetNr == 0) {
			return new ViterbiData(new Category(0, 0), 0, 0);
		}
		Category bestCategory = null;
		float minScore = Float.POSITIVE_INFINITY;
		float minTotalScore = Float.POSITIVE_INFINITY;
		for (Map.Entry<Category, Float> entry : categoryMap.entrySet()) {
			Category category = entry.getKey();
			float categoryScore = entry.getValue();
			int sourceStart = sourceNr - category.getSourceSegmentCount();
			int targetStart = targetNr - category.getTargetSegmentCount();
			if (elementExists(matrix, sourceStart, targetStart)) {
				List<String> sourceList = sourceSegmentList.subList(sourceStart, 
						sourceNr);
				List<String> targetList = targetSegmentList.subList(targetStart, 
						targetNr);
				float score = categoryScore + 
						calculator.calculateScore(sourceList, targetList);
				float totalScore = score + matrix.get(
						sourceStart, targetStart).getTotalScore();
				if (totalScore < minTotalScore) {
					minTotalScore = totalScore;
					minScore = score;
					bestCategory = category;
				}
			}
		}
		if (bestCategory == null) {
			return null;
		} else {
			return new ViterbiData(bestCategory, minScore, minTotalScore);
		}
	}

	/**
	 * Retrieves best alignment from populated matrix by reconstructing the
	 * most probable path by iterating over it backwards and always selecting 
	 * the most probable alignment.
	 * 
	 * @param sourceSegmentList initial source segment list
	 * @param targetSegmentList initial target segment list
	 * @param matrix populated matrix
	 * @return alignment with highest total probability
	 */
	private List<Alignment> backtrace(List<String> sourceSegmentList, 
			List<String> targetSegmentList, Matrix<ViterbiData> matrix) {
		
		ListIterator<String> sourceIterator = 
			sourceSegmentList.listIterator(sourceSegmentList.size());
		ListIterator<String> targetIterator = 
			targetSegmentList.listIterator(targetSegmentList.size());
		List<Alignment> alignmentList = new ArrayList<Alignment>();
		
		while (sourceIterator.hasPrevious() || targetIterator.hasPrevious()) {
			ViterbiData data = matrix.get(sourceIterator.previousIndex() + 1, 
					targetIterator.previousIndex() + 1);
			if (data == null) {
				throw new IllegalStateException("Unable to reconstruct " +
						"previously calculated alignment during backtrace.");
			}
			List<String> sourceList = createReverseList(sourceIterator, 
					data.getCategory().getSourceSegmentCount());
			List<String> targetList = createReverseList(targetIterator, 
					data.getCategory().getTargetSegmentCount());
			Alignment alignment = new Alignment(sourceList, targetList, 
					data.getScore());
			alignmentList.add(alignment);
		}
		Collections.reverse(alignmentList);
		
		return alignmentList; 
	}

	/**
	 * Retrieves given count of elements from list iterator by going backward
	 * (using {@link ListIterator#previous()}) and returns them as a list in 
	 * reverse the order.
	 * 
	 * @param iterator list iterator
	 * @param count number of elements to retrieve from iterator
	 * @return list of elements
	 */
	private List<String> createReverseList(ListIterator<String> iterator, 
			int count) {
		List<String> list = new ArrayList<String>(count);
		for (int i = 0; i < count; ++i) {
			list.add(iterator.previous());
		}
		Collections.reverse(list);
		return list;
	}

	
}
