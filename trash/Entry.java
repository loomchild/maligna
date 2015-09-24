package align.aligner.aligners.moore.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Entry {

	private List<Translation> translationList;
	
	public Entry() {
		this.translationList = new ArrayList<Translation>();
	}
	
	public List<Translation> getTranslationList() {
		return Collections.unmodifiableList(translationList);
	}
	
	public void addTranslation(Translation translation) {
		translationList.add(translation);
	}

	public void removeTranslation(Translation translation) {
		translationList.remove(translation);
	}
	
	public float getProbability(String word) {
		int index = translationList.indexOf(word);
		if (index != -1) {
			return translationList.get(index).getProbability();
		} else {
			return 0.0f;
		}
	}

}
