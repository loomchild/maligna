package net.sourceforge.align.model.translation;


/**
 * Represents single source word translation. 
 * Responsible for storing target word id and translation probability.
 * @author loomchild
 */
public class TargetData {

	private int wid;
	
	private double probability;
	
	/**
	 * Creates target data.
	 * @param wid target word id
	 * @param probability translation probability
	 */
	public TargetData(int wid, double probability) {
		this.wid = wid;
		this.probability = probability;
	}

	/**
	 * @return target word id
	 */
	public int getWid() {
		return wid;
	}

	/**
	 * @return source to target word translation probability 
	 */
	public double getProbability() {
		return probability;
	}

}
