package align.task;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import align.aligner.Aligner;
import align.aligner.AlignmentImpossibleException;
import align.data.Alignment;
import align.splitter.Splitter;

/**
 * Reprezentuje jednoetapowe zadanie podziału i dopasowania tekstów. Odpowiada
 * za podział tekstu źródłowego i docelowego na segmenty za pomocą osobnych
 * splitterów i dopasowanie uzyskanych segmentów za pomocą alignera. 
 *
 * @author Jarek Lipski (loomchild)
 */
public class SimpleTask implements Task {

	private Splitter sourceSplitter;
	
	private Splitter targetSplitter;
	
	private Aligner aligner;
	
	/**
	 * Towrzy zadanie używające danych Splitterów i Alignera.
	 * @param sourceSplitter Splitter dla tekstu źrógłowego.
	 * @param targetSplitter Splitter dla tekstu docelowego.
	 * @param aligner Aligner dopasowujący segmenty.
	 */
	public SimpleTask(Splitter sourceSplitter, Splitter targetSplitter,
			Aligner aligner) {
		this.sourceSplitter = sourceSplitter;
		this.targetSplitter = targetSplitter;
		this.aligner = aligner;
	}
	
	/**
	 * @inheritDoc
	 */
	public List<Alignment> run(Reader sourceReader, Reader targetReader)
			throws AlignmentImpossibleException, IOException {
		List<String> sourceSegmentList = sourceSplitter.split(sourceReader);
		List<String> targetSegmentList = targetSplitter.split(targetReader);
		return align(sourceSegmentList, targetSegmentList);
	}

	/**
	 * @inheritDoc
	 */
	public List<Alignment> run(String sourceString, String targetString)
			throws AlignmentImpossibleException {
		List<String> sourceSegmentList = sourceSplitter.split(sourceString);
		List<String> targetSegmentList = targetSplitter.split(targetString);
		return align(sourceSegmentList, targetSegmentList);
	}

	private List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList) throws AlignmentImpossibleException  {
		return aligner.align(sourceSegmentList, targetSegmentList);
	}
	
}
