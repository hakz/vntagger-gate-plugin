package vn.com.epi.gate.vnpostagger;

import edu.stanford.nlp.ling.HasWord;

public class WordToken implements HasWord {

	private String word;
	
	public WordToken(String word) {
		super();
		this.word = word;
	}

	@Override
	public String word() {
		return word;
	}

	@Override
	public void setWord(String word) {
		this.word = word;
	}

}
