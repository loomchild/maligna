package align.aligner.algorithms.moore.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reprezentuje słownik mapujący słowa na identyfikatory.
 *
 * @author Jarek Lipski (loomchild)
 */
public class Vocabulary {

	private List<String> wordArray;
	
	private Map<String, Integer> wordMap;
	
	/**
	 * Tworzy pusty słownik.
	 */
	public Vocabulary() {
		this.wordArray = new ArrayList<String>();
		this.wordMap = new HashMap<String, Integer>(); 
	}
	
	/**
	 * Wyszukuje identyfikator danego słowa. Jeśli słowa nie ma w słowniku
	 * zgłasza wyjątek.
	 * @param word Słowo.
	 * @return Zwraca identyfikator słowa.
	 * @throws WordNotFoundException Zgłaszany jeśli nie istnieje podane słowo.
	 */
	public int getWid(String word) {
		Integer wid = wordMap.get(word);
		if (wid != null) {
			return wid;
		} else {
			throw new WordNotFoundException(word);
		}
	}
	
	/**
	 * Sprawdza czy dany identyfikator znajduje się w słowniku.
	 * @param wid Identyfikator słowa.
	 * @return Zwrawca true jeśli podany identyfikator znajduje się w słowniku.
	 */
	public boolean containsWid(int wid) {
		return wid >= 0 && wid < wordArray.size();
	}

	/**
	 * Wyszukuje słowo po identyfikatorze. Jeśli nie znajdzie zgłasza wyjątek.
	 * @param wid Identyfikator słowa.
	 * @return Zwraca słowo.
	 * @throws WordIdNotFoundException Zgłaszany jeśli nie istnieje słowo o podanym identyfikatorze.
	 */
	public String getWord(int wid) {
		if (wid < wordArray.size()) {
			return wordArray.get(wid);
		} else {
			throw new WordIdNotFoundException(wid);
		}
	}

	/**
	 * Sprawdza czy dane słowo znajduje się w słowniku.
	 * @param word Słowo.
	 * @return Zwrawca true jeśli podane słowo znajduje się w słowniku.
	 */
	public boolean containsWord(String word) {
		return wordMap.containsKey(word);
	}
	
	/**
	 * Dodaje nowe słowo do słownika jeśli go tam nie ma, w przeciwnym wypadku
	 * nic nie robi.
	 * @param word Słowo
	 */
	public void put(String word) {
		if (!wordMap.containsKey(word)) {
			int wid = wordArray.size();
			wordArray.add(word);
			wordMap.put(word, wid);
		}
	}
	
	/**
	 * Zwraca liczbę słów w słowniku. Równocześnie jest to maksymalny 
	 * identyfikator słowa + 1.
	 * @return Zwraca liczbę słów w słowniku.
	 */
	public int getWordCount() {
		return wordArray.size();
	}
	
}
