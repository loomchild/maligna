package net.loomchild.maligna.model.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents mutable translation data for some source word. Used in all 
 * translation models after initial training iteration. After making changes
 * to the stored data {@link #normalize()} and {@link #sort()} methods should
 * be called.
 * @author loomchild
 */
class MutableSourceData implements SourceData {
	
	private List<TargetData> translationList;
	
	/**
	 * Creates empty source translation data.
	 */
	public MutableSourceData() {
		this.translationList = new ArrayList<TargetData>();
	}
	
	public double getTranslationProbability(int targetWid) {
		int index = findTargetData(targetWid);
		if (index != -1) {
			return translationList.get(index).getProbability();
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns immutable translation list with probability > 0.
	 * Note: List may not be always sorted, {@link #sort()} method should always 
	 * be executed before calling this method.
	 *  
	 * @return sorted translation list.
	 */
	public List<TargetData> getTranslationList() {
		return translationList;
	}

	/**
	 * Sets translation probability of this source word to target word with 
	 * given id. After making any changes like this {@link #normalize()} and
	 * {@link #sort()} methods must be called before retrieving any data
	 * from the model.
	 * @param targetWid target word id, >= 0.
	 * @param probability translation probability.
	 */
	public void setTranslationProbability(int targetWid, double probability) {
		TargetData newData = new TargetData(targetWid, probability);
		int index = findTargetData(targetWid);
		if (index != -1) {
			translationList.set(index, newData);
		} else {
			translationList.add(newData);
		}
	}
	
	/**
	 * Normalizes translation probabilities so they sum up to one.
	 * This method should be called after making any changes to this object
	 * via {@link #setTranslationProbability(int, double)} but before retrieving
	 * any data from it.
	 */
	public void normalize() {
		double totalProbability = 0.0f;
		for (TargetData data : translationList) {
			totalProbability += data.getProbability();
		}
		int index = 0;
		for (TargetData data : translationList) {
			double newProbability = data.getProbability() / totalProbability;
			TargetData newData = new TargetData(data.getWid(), newProbability);
			translationList.set(index, newData);
			++index;
		}
	}

	/**
	 * Sorts translation list by probability descending.
	 * This method should be called after making any changes to this object
	 * via {@link #setTranslationProbability(int, double)} but before retrieving
	 * any data from it.
	 */
	public void sort() {
		Collections.sort(translationList, new TargetDataProbabilityComparator());
	}
	
	/**
	 * Finds a word with given id on translation list and returns its
	 * index. If word could not be found returns -1.
	 * 
	 * @param targetWid target word id
	 * @return translation data index on the list if it exists or -1. 
	 */
	private int findTargetData(int targetWid) {
		int index = 0;
		for (TargetData data : translationList) {
			if (data.getWid() == targetWid) {
				return index;
			}
			++index;
		}
		return -1;
	}
	
}
