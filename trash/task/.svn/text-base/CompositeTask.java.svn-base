package align.task;

import static align.Utils.merge;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import align.aligner.AlignmentImpossibleException;
import align.data.Alignment;

/**
 * Reprezentuje wieloetapowe zadanie podziału tekstów na segmenty i dopasowania
 * ich. W każdym etapie wykonywane jest podzadanie. Wejściem kolejnych podzadań
 * są wyjścia ich poprzedników, odpowiednio scalone.
 *
 * @author Jarek Lipski (loomchild)
 */
public class CompositeTask implements Task {

	private List<Task> taskList;
    
	/**
	 * Tworzy nie zawierające podzadań zadanie złożone.
	 */
	public CompositeTask() {
		this.taskList = new LinkedList<Task>();
	}
    
	/**
	 * Uruchamia proces dzielenia i dopasowywania dla danych tekstów kolejno 
	 * za pomocą każdego swojego podzadania. 
	 * Ostatecznie wynikiem jest dopasowanie dokonane przez ostatnie podzadanie.
	 * @param sourceReader Strumień tekstu źródłowego.
	 * @param targetReader Strumień tekstu docelowego.
	 * @return Zwraca listę uzyskanych dopasowań.
	 * @throws AlignmentImpossibleException Zgłaszany gdy dopasowanie jest niemożliwe.
	 * @throws IOException Zgłaszany gdy nastąpi błąd odczytu strumienia.
	 */
	public List<Alignment> run(Reader sourceReader, Reader targetReader)
			throws AlignmentImpossibleException, IOException {
		Iterator<Task> taskIterator = taskList.iterator();
		if (!taskIterator.hasNext()) {
			return Collections.emptyList();
		}
		Task firstTask = taskIterator.next();
		List<Alignment> alignmentList = firstTask.run(sourceReader, 
				targetReader); 
		return runRemainingTasks(taskIterator, alignmentList);
	}

	/**
	 * Uruchamia proces dzielenia i dopasowywania dla danych tekstów kolejno 
	 * za pomocą każdego swojego podzadania. 
	 * Ostatecznie wynikiem jest dopasowanie dokonane przez ostatnie podzadanie.
	 * @param sourceString Tekst źródłowy.
	 * @param targetString Tekst docelowy.
	 * @return Zwraca listę uzyskanych dopasowań.
	 * @throws AlignmentImpossibleException Zgłaszany gdy dopasowanie jest niemożliwe.
	 */
	public List<Alignment> run(String sourceString, String targetString) 
			throws AlignmentImpossibleException {
		Iterator<Task> taskIterator = taskList.iterator();
		if (!taskIterator.hasNext()) {
			return Collections.emptyList();
		}
		Task firstTask = taskIterator.next();
		List<Alignment> alignmentList = firstTask.run(sourceString, 
				targetString); 
		return runRemainingTasks(taskIterator, alignmentList);
	}

	private List<Alignment> runRemainingTasks(Iterator<Task> taskIterator,
			List<Alignment> alignmentList) 
			throws AlignmentImpossibleException {
		while(taskIterator.hasNext()) {
			Task task = taskIterator.next();
			alignmentList = runSubTask(task, alignmentList);
		}
		return alignmentList;
	}
	
	private List<Alignment> runSubTask(Task task, 
			List<Alignment> previousTaskAlignmentList)
			throws AlignmentImpossibleException {
		List<Alignment> alignmentList = new LinkedList<Alignment>();
		for(Alignment alignment : previousTaskAlignmentList) {
			String sourceSegment = merge(alignment.getSourceSegmentList());
			String targetSegment = merge(alignment.getTargetSegmentList());
			List<Alignment> currentSegmentAlignmentList =
				 task.run(sourceSegment, targetSegment);
			alignmentList.addAll(currentSegmentAlignmentList);
		}
		return alignmentList;
	}
	
	/**
	 * Dołącza zadanie na koniec listy podzadań.
	 * @param task Zadanie.
	 */
	public void addTask(Task task) {
		taskList.add(task);
	}

}
