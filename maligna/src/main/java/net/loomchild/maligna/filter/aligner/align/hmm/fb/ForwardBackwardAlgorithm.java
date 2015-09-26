package net.loomchild.maligna.filter.aligner.align.hmm.fb;

import static net.loomchild.maligna.filter.aligner.align.hmm.Util.elementExists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.loomchild.maligna.calculator.Calculator;
import net.loomchild.maligna.coretypes.Alignment;
import net.loomchild.maligna.coretypes.Category;
import net.loomchild.maligna.filter.aligner.align.AlignAlgorithm;
import net.loomchild.maligna.matrix.Matrix;
import net.loomchild.maligna.matrix.MatrixFactory;
import net.loomchild.maligna.matrix.MatrixIterator;
import net.loomchild.maligna.progress.ProgressManager;
import net.loomchild.maligna.progress.ProgressMeter;
import net.loomchild.maligna.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents alignment algorithm which uses Forward Backward algorithm.
 * In simple words it finds a path in alignment matrix (representing
 * all possible alignments) with maximum probability of each individual
 * alignment on the path.
 * 
 * This algorithm is independent of method of calculating the individual
 * alignment probabilities (see {@link Calculator} and the way the 
 * matrix is iterated (some irrelevant elements may be omitted by 
 * {@link MatrixIterator}, implemented by a {@link Matrix}). 
 * 
 * TODO: Check the link
 * @see <a href="http://en.wikipedia.org/wiki/Forward_Backward_algorithm">Forward Backward Algorithm</a>
 * 
 * @see "A Tutorial on Hidden Markov Models and Selected 
 * 		Applications in Speech Recognition, Lawrence R. Rabiner"
 * 
 * @author loomchild
 */
public class ForwardBackwardAlgorithm implements AlignAlgorithm {
	
	private Log log = LogFactory.getLog(ForwardBackwardAlgorithm.class);

	private Map<Category, Float> categoryMap;
	
	private Calculator calculator;
	
	private MatrixFactory matrixFactory;
	
	/**
	 * Creates an algorithm. 
	 * @param calculator probability calculator
	 * @param categoryMap possible alignment categories with their probabilities
	 * @param matrixFactory factory creating two dimensional matrices
	 */
	public ForwardBackwardAlgorithm(Calculator calculator, 
			Map<Category, Float> categoryMap, MatrixFactory matrixFactory) {
		this.matrixFactory = matrixFactory;
		this.calculator = calculator;
		this.categoryMap = categoryMap;
	}

	/**
	 * Consists of two phases - forward and backward, and populates forward
	 * and backward matrices during these phases (by using 
	 * {@link #createForwardData(int, int, List, List, Matrix)} and 
	 * {@link #createBackwardData(int, int, List, List, Matrix)} methods).
	 * 
	 * After that retrieves the alignment list with highest individual 
	 * alignment probabilities.
	 * 
	 * TODO: Describe in more detail.
	 */
	public List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {

		Matrix<Float> forwardMatrix = matrixFactory.createMatrix(
				sourceSegmentList.size() + 1, targetSegmentList.size() + 1);
		
		ProgressMeter progress = new ProgressMeter("Forward-Backward Align", forwardMatrix.getSize() * 2);
		ProgressManager.getInstance().registerProgressMeter(progress);
		
		MatrixIterator<Float> forwardIterator = forwardMatrix.getIterator();
		while(forwardIterator.hasNext()) {
			forwardIterator.next();
			int x = forwardIterator.getX();
			int y = forwardIterator.getY();
			float data = createForwardData(x, y, sourceSegmentList, 
					targetSegmentList, forwardMatrix);
			forwardMatrix.set(x, y, data);

			progress.completeTask();
		}
		
		Matrix<Float> backwardMatrix = matrixFactory.createMatrix(
				sourceSegmentList.size() + 1, targetSegmentList.size() + 1);
		MatrixIterator<Float> backwardIterator = backwardMatrix.getIterator();

		backwardIterator.afterLast();
		while(backwardIterator.hasPrevious()) {
			backwardIterator.previous();
			int x = backwardIterator.getX();
			int y = backwardIterator.getY();
			float data = createBackwardData(x, y, sourceSegmentList, 
					targetSegmentList, backwardMatrix);
			backwardMatrix.set(x, y, data);
			
			progress.completeTask();
		}

		List<Alignment> alignmentList = new ArrayList<Alignment>();
		
		float totalScore = forwardMatrix.get(sourceSegmentList.size(), 
				targetSegmentList.size());
		int x = 0;
		int y = 0;
		while (x < sourceSegmentList.size() || y < targetSegmentList.size()) {
			float bestScore = Float.POSITIVE_INFINITY;
			Category bestCategory = null;
			for (Category category : categoryMap.keySet()) {
				int newX = x + category.getSourceSegmentCount();
				int newY = y + category.getTargetSegmentCount();
				if (newX <= sourceSegmentList.size() && 
						newY <= targetSegmentList.size()) {
					if (forwardMatrix.get(newX, newY) != null &&
							backwardMatrix.get(newX, newY) != null) {
						float forwardScore = forwardMatrix.get(newX, newY);
						float backwardScore = backwardMatrix.get(newX, newY);
						float score = forwardScore + backwardScore - totalScore;
						if (score < bestScore) {
							bestScore = score;
							bestCategory = category;
						}
					}
				}
			}
			List<String> sourceList = createSubList(sourceSegmentList, 
					x, x + bestCategory.getSourceSegmentCount());
			List<String> targetList = createSubList(targetSegmentList, 
					y, y + bestCategory.getTargetSegmentCount());
			Alignment alignment = 
					new Alignment(sourceList, targetList, bestScore);
			alignmentList.add(alignment);
			x += bestCategory.getSourceSegmentCount();
			y += bestCategory.getTargetSegmentCount();
			log.trace("(" + x + ", " + y + ") - s: " + bestScore + " (" + Math.exp(-bestScore) + ")");
		}

		/*
		int previousX = 0;
		int previousY = 0;
		float score05 = (float)toScore(0.5);
		float totalScore = forwardMatrix.get(sourceSegmentList.size(), 
				targetSegmentList.size());
		forwardIterator.beforeFirst();
		while(forwardIterator.hasNext()) {
			forwardIterator.next();
			int x = forwardIterator.getX();
			int y = forwardIterator.getY();
			float forwardScore = forwardMatrix.get(x, y);
			float backwardScore = backwardMatrix.get(x, y);
			float score = forwardScore + backwardScore - totalScore;
			if (score < score05 && (x > 0 || y > 0)) {
				if (x < previousX || y < previousY) {
					throw new AlignmentNotFoundException();
				}
				List<String> sourceList = 
					createSubList(sourceSegmentList, previousX, x);
				List<String> targetList = 
					createSubList(targetSegmentList, previousY, y);
				Alignment alignment = new Alignment(sourceList, targetList, score);
				alignmentList.add(alignment);
				previousX = x;
				previousY = y;
				log.trace("(" + x + ", " + y + "): f: " + forwardScore + ", b: " + backwardScore + ", t: " + totalScore + ", s: " + score + " (" + Math.exp(-score) + ")");
			}
		}
		*/
		
		ProgressManager.getInstance().unregisterProgressMeter(progress);
		
		return alignmentList;
	}

	/**
	 * Calculates sum of probabilities (returned as score equal to 
	 * -ln (probability)) of all paths leading to this element.
	 *
 	 * Matrix should be populated from upper left corner to lower right corner.
	 * 
	 * @param x source segment position on the matrix
	 * @param y target segment position on the matrix
	 * @param sourceSegmentList list of current source segments
	 * @param targetSegmentList list of current target segments
	 * @param matrix forward matrix
	 * @return sum of all paths probabilities as score (-ln (probability)). 
	 */
	private float createForwardData(int x, int y, 
			List<String> sourceSegmentList, List<String> targetSegmentList, 
			Matrix<Float> matrix) {
		List<Float> scoreList = new ArrayList<Float>(categoryMap.size());
		for (Map.Entry<Category, Float> entry : categoryMap.entrySet()) {
			Category category = entry.getKey();
			float categoryScore = entry.getValue();
			int startX = x - category.getSourceSegmentCount();
			int startY = y - category.getTargetSegmentCount();
			if (elementExists(matrix, startX, startY)) {
				List<String> sourceList = sourceSegmentList.subList(startX, x);
				List<String> targetList = targetSegmentList.subList(startY, y);
				float score = categoryScore + 
						calculator.calculateScore(sourceList, targetList);
				float totalScore = score + matrix.get(startX, startY);
				scoreList.add(totalScore);
			}
		}
		float scoreSum = Util.scoreSum(scoreList);
		return scoreSum;
	}

	/**
	 * Calculates sum of probabilities (returned as score equal to 
	 * -ln (probability)) of all paths leading to this element but starting
	 * from the lower right corner of the matrix (backward because 
	 * it represents going from the back of the texts).
	 * 
	 * Matrix should be populated from lower right corner to upper left
	 * corner.
	 * 
	 * @param x source segment position on the matrix
	 * @param y target segment position on the matrix
	 * @param sourceSegmentList list of current source segments
	 * @param targetSegmentList list of current target segments
	 * @param matrix backward matrix
	 * @return sum of all paths probabilities as score (-ln (probability)). 
	 */
	private float createBackwardData(int x, int y, 
			List<String> sourceSegmentList, List<String> targetSegmentList, 
			Matrix<Float> matrix) {
		List<Float> scoreList = new ArrayList<Float>(categoryMap.size());
		for (Map.Entry<Category, Float> entry : categoryMap.entrySet()) {
			Category category = entry.getKey();
			float categoryScore = entry.getValue();
			int endX = x + category.getSourceSegmentCount();
			int endY = y + category.getTargetSegmentCount();
			if (elementExists(matrix, endX, endY)) {
				List<String> sourceList = sourceSegmentList.subList(x, endX);
				List<String> targetList = targetSegmentList.subList(y, endY);
				float score = categoryScore + 
						calculator.calculateScore(sourceList, targetList);
				float totalScore = score + matrix.get(endX, endY);
				scoreList.add(totalScore);
			}
		}
		float scoreSum = Util.scoreSum(scoreList);
		return scoreSum;
	}

	/**
	 * Creates physical sub list.
	 * TODO: Do I need to do this? Unit tests are fine without it, maybe this 
	 * is a performance bottleneck?
	 * 
	 * @param list
	 * @param start
	 * @param end
	 * @return
	 */
	private List<String> createSubList(List<String> list, int start, int end) {
		return new ArrayList<String>(list.subList(start, end));
	}
	
}
