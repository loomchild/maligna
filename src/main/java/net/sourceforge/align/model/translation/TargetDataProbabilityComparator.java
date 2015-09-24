package net.sourceforge.align.model.translation;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Responsible for comparing {@link TargetData} objects by translation 
 * probability and ordering them from the most probable to the least probable.
 * @author loomchild
 */
public class TargetDataProbabilityComparator 
		implements Comparator<TargetData>, Serializable {

	private static final long serialVersionUID = -9161863179489700671L;

	public int compare(TargetData o1, TargetData o2) {
		double difference = o2.getProbability() - o1.getProbability();
		if (difference > 0) {
			return 1;
		} else if (difference < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
