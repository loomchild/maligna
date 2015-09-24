package align.aligner.aligners.moore.models;

public class Translation {

	private String word;
	
	private float probability;

	public Translation(String word, float probability) {
		this.word = word;
		this.probability = probability;
	}

	public float getProbability() {
		return probability;
	}

	public String getWord() {
		return word;
	}

	
}
