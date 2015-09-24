package align.task;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import align.aligner.AlignmentImpossibleException;
import align.data.Alignment;

/**
 * Reprezemtuje zadanie podzielenia tekstów źródłowego i docelowego na segmenty
 * i zrównoleglenia ich.
 *
 * @author Jarek Lipski (loomchild)
 */
public interface Task {

	/**
	 * Uruchamia proces dzielenia i dopasowywania dla danych tekstów.
	 * @param sourceReader Strumień tekstu źródłowego.
	 * @param targetReader Strumień tekstu docelowego.
	 * @return Zwraca listę uzyskanych dopasowań.
	 * @throws AlignmentImpossibleException Zgłaszany gdy dopasowanie jest niemożliwe.
	 * @throws IOException Zgłaszany gdy nastąpi błąd odczytu strumienia.
	 */
	public List<Alignment> run(Reader sourceReader, Reader targetReader) 
			throws AlignmentImpossibleException, IOException;

	/**
	 * Uruchamia proces dzielenia i dopasowywania dla danych tekstów.
	 * @param sourceString Tekst źródłowy.
	 * @param targetString Tekst docelowy.
	 * @return Zwraca listę uzyskanych dopasowań.
	 * @throws AlignmentImpossibleException Zgłaszany gdy dopasowanie jest niemożliwe.
	 */
	public List<Alignment> run(String sourceString, String targetString)
			throws AlignmentImpossibleException;

}
