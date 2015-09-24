package net.sourceforge.align.filter.aligner.align.hmm.adaptive;

import java.util.List;
import java.util.Map;

import net.sourceforge.align.calculator.Calculator;
import net.sourceforge.align.coretypes.Alignment;
import net.sourceforge.align.coretypes.Category;
import net.sourceforge.align.coretypes.CategoryDefaults;
import net.sourceforge.align.filter.aligner.align.AlignAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.HmmAlignAlgorithmFactory;
import net.sourceforge.align.filter.aligner.align.hmm.fb.ForwardBackwardAlgorithm;
import net.sourceforge.align.filter.aligner.align.hmm.viterbi.ViterbiAlgorithm;
import net.sourceforge.align.matrix.BandMatrix;
import net.sourceforge.align.matrix.BandMatrixFactory;
import net.sourceforge.align.matrix.MatrixFactory;

/**
 * <p>Represents meta-alignment algorithm. It uses given alignment algorithm and
 * by increasing width of the diagonal band in {@link BandMatrix} tries to find
 * reasonable alignment.</p> 
 * 
 * <p>The idea is that it first creates a matrix with narrow band and performs 
 * the alignment using the algorithm. It evaluates it by checking if the result 
 * is not closer to the band edge than the given margin (this suggests that
 * there might be better alignment outside the band). If it is then it 
 * increases the size of band and tries again. The whole process is
 * repeated until alignment is not within the margin.</p> 
 * 
 * <p>This improves overall performance of the alignment because the whole
 * matrix does not need to be calculated, just the elements on the 
 * narrow band around diagonal.</p>
 * 
 * @author loomchild
 */
public class AdaptiveBandAlgorithm implements AlignAlgorithm {
		
	public static final float DEFAULT_BAND_INCREMENT_RATIO = 1.5f;
	
	public static final int DEFAULT_INITIAL_BAND_RADIUS = 
		BandMatrixFactory.DEFAULT_BAND_RADIUS;
	
	public static final int DEFAULT_MIN_BAND_MARGIN = 
		DEFAULT_INITIAL_BAND_RADIUS / 4;
	
	private Map<Category, Float> categoryMap;
	
	private Calculator calculator;
	
	private int initialBandRadius;
	
	private float bandIncrementRatio;
	
	private int minBandMargin;
	
	private HmmAlignAlgorithmFactory algorithmFactory;
	
	/**
	 * Creates meta-algorithm.
	 * 
	 * @param algorithmFactory factory used to create actual alignment
	 * 		algorithm (for example {@link ViterbiAlgorithm} or 
	 * 		{@link ForwardBackwardAlgorithm}) 
	 * @param calculator calculator used by actual algorithm
	 * @param initialBandRadius initial size of band
	 * @param bandIncrementRatio the number by which the band size will be 
	 * 		duplicated on alignment failure 
	 * @param minBandMargin size of margin used to evaluate an alignment
	 * @param categoryMap categories used by actual algorithm
	 */
	public AdaptiveBandAlgorithm(HmmAlignAlgorithmFactory algorithmFactory, 
			Calculator calculator, int initialBandRadius, 
			float bandIncrementRatio, int minBandMargin, 
			Map<Category, Float> categoryMap) {
		this.categoryMap = categoryMap;
		this.calculator = calculator;
		this.initialBandRadius = initialBandRadius;
		this.bandIncrementRatio = bandIncrementRatio;
		this.minBandMargin = minBandMargin;
		this.algorithmFactory = algorithmFactory;
	}

	/**
	 * Creates meta-algorithm using default categories 
	 * (@link{CategoryDefaults#BEST_CATEGORY_MAP}).
	 * 
	 * @param algorithmFactory factory used to create actual alignment
	 * 		algorithm (for example {@link ViterbiAlgorithm} or 
	 * 		{@link ForwardBackwardAlgorithm}) 
	 * @param calculator calculator used by actual algorithm
	 * @param initialBandRadius initial size of band
	 * @param bandIncrementRatio the number by which the band size will be 
	 * 		duplicated on alignment failure 
	 * @param minBandMargin size of margin used to evaluate an alignment
	 */
	public AdaptiveBandAlgorithm(HmmAlignAlgorithmFactory algorithmFactory, 
			Calculator calculator, int initialBandRadius, 
			float bandIncrementRatio, int minBandMargin) {
		this(algorithmFactory, calculator, initialBandRadius, 
				bandIncrementRatio, minBandMargin, 
				CategoryDefaults.BEST_CATEGORY_MAP);
	}

	/**
	 * Creates meta-algorithm using the following defaults:
	 * {@link #DEFAULT_INITIAL_BAND_RADIUS}, 
	 * {@link #DEFAULT_BAND_INCREMENT_RATIO} and
	 * {@link #DEFAULT_MIN_BAND_MARGIN}.
	 * 
	 * @param algorithmFactory factory used to create actual alignment
	 * 		algorithm (for example {@link ViterbiAlgorithm} or 
	 * 		{@link ForwardBackwardAlgorithm}) 
	 * @param calculator calculator used by actual algorithm
	 */
	public AdaptiveBandAlgorithm(HmmAlignAlgorithmFactory algorithmFactory, 
			Calculator calculator) {
		this(algorithmFactory, calculator, DEFAULT_INITIAL_BAND_RADIUS, 
				DEFAULT_BAND_INCREMENT_RATIO, DEFAULT_MIN_BAND_MARGIN);
	}

	/**
	 * Creates {@link BandMatrix} with narrow band at first and increases 
	 * its width until it finds an alignment that does not come closer to 
	 * the band edge than given margin.
	 */
	public List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList) {
		float bandRadius = initialBandRadius /  bandIncrementRatio;
		int maxAlignmentRadius = (int)bandRadius + 1;
		List<Alignment> alignmentList = null;
		
		while((maxAlignmentRadius + minBandMargin) > bandRadius) {
			bandRadius *= bandIncrementRatio;
			MatrixFactory matrixFactory = new BandMatrixFactory((int)bandRadius);
			AlignAlgorithm algorithm = algorithmFactory.createAlignAlgorithm(
					calculator, categoryMap, matrixFactory);
			alignmentList = algorithm.align(sourceSegmentList, 
					targetSegmentList);
			maxAlignmentRadius = calculateMaxAlignmentRadius(alignmentList, 
					sourceSegmentList.size(), targetSegmentList.size());
		}
		
		assert alignmentList != null;
		return alignmentList;
	}

	/**
	 * Calculates maximum deviation of given alignment from diagonal 
	 * (can be interpreted as maximum alignment radius).
	 * 
	 * @param alignmentList alignment list
	 * @param sourceCount source segment count
	 * @param targetCount target segment count
	 * @return
	 */
	private int calculateMaxAlignmentRadius(List<Alignment> alignmentList,
			int sourceCount, int targetCount) {
		int maxRadius = 0;
		int sourceNr = 0;
		int targetNr = 0;
		float targetSourceRatio = (float)targetCount / (float)sourceCount;
		
		for (Alignment alignment : alignmentList) {
			sourceNr += alignment.getSourceSegmentList().size();
			targetNr += alignment.getTargetSegmentList().size();
			int diagonalTargetNr = (int)((float)sourceNr * targetSourceRatio);
			int radius = Math.abs(targetNr - diagonalTargetNr);
			if (radius > maxRadius) {
				maxRadius = radius;
			}
		}
	
		return maxRadius;
	}
	
}
