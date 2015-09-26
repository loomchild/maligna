package net.loomchild.maligna.model.length;

import java.util.List;

/**
 * Represents length model utilities.
 * @author loomchild
 */
public class LengthModelUtil {

	/**
	 * Trains a length model using given segments corpus
	 * @param segmentLengthList segment list
	 * @return created length model reflecting segment length occurrence 
	 * 		probabilities
	 */
	public static LengthModel train(List<Integer> segmentLengthList) {
		MutableLengthModel model = new MutableLengthModel();

		for (int segmentLength : segmentLengthList) {
			model.addLengthOccurence(segmentLength);
		}
		model.normalize();
		
		return model;
	}

}
