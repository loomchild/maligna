package align.aligner;

import java.util.Collections;
import java.util.List;

import align.data.Alignment;

/**
 * Reprezentuje abstakcyjny aligner sprawdzający wejście i w razie możliwości
 * zwracający od razu wynik albo przekazujący sterowanie do konkretnej 
 * implementacji.
 *
 * @author Jarek Lipski (loomchild)
 */
public abstract class AbstractAligner implements Aligner {

	/**
	 * Dopasowywuje do siebie listy segmentów. Jeśli obie listy są puste 
	 * zwraca pustą liste. Jeśli jedna z list jest pusta zwraca jedno 
	 * dopasowanie wiele do zera. W przeciwnym wypadku wywołuje metode
	 * klasy potomnej dokonującą właściwego dopasowania.
	 * @param sourceSegmentList Lista segmentów tekstu źródłowego.
	 * @param targetSegmentList Lista segmentów tekstu docelowego.
	 * @return Zwraca listę dopasowań która obejmuje wszystkie segmenty obu tekstów.
	 * @throws AlignmentImpossibleException Zgłaszany przez klase pochodną gdy nie da się dopasować tekstów.
	 */
	public List<Alignment> align(List<String> sourceSegmentList, 
			List<String> targetSegmentList) throws AlignmentImpossibleException {
		List<Alignment> alignmentList;
		if (sourceSegmentList.size() == 0 && targetSegmentList.size() == 0) {
			alignmentList = Collections.emptyList();
		} else if (sourceSegmentList.size() == 0) {
			alignmentList = align.aligner.Utils.alignZeroToSegmentList(
					targetSegmentList);
		} else if (targetSegmentList.size() == 0) {
			alignmentList = align.aligner.Utils.alignSegmentListToZero(
					sourceSegmentList);
		} else {
			//Zastosowanie właściwego algorytmu gdy listy niepuste.
			alignmentList = alignNotEmpty(sourceSegmentList, targetSegmentList);
		}
		return alignmentList;
	}
	
	/**
	 * Właściwa implementacja algorytmu dopasowywania niepustych list segmentów.
	 * @param sourceSegmentList Lista segmentów tekstu źródłowego.
	 * @param targetSegmentList Lista segmentów tekstu docelowego.
	 * @return Zwraca listę dopasowań która obejmuje wszystkie segmenty obu tekstów.
	 * @throws AlignmentImpossibleException Zgłaszany gdy nie da się dopasować tekstów.
	 */
	protected abstract List<Alignment> alignNotEmpty(
			List<String> sourceSegmentList, List<String> targetSegmentList) 
			throws AlignmentImpossibleException;
	
}
