package vn.com.epi.gate.vnpostagger;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import vn.hus.nlp.tagger.VietnameseMaxentTaggerProvider;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TaggerTest {

	public static void main(String[] args) {
		
		Sentence<WordToken> sentence = new Sentence<WordToken>();
		sentence.add(new WordToken("Hành_động"));
		sentence.add(new WordToken("nhanh_chóng"));
		sentence.add(new WordToken("và"));
		sentence.add(new WordToken("hiệu_quả"));
		sentence.add(new WordToken(","));
		sentence.add(new WordToken("thử_nghiệm"));
		sentence.add(new WordToken("thành_công"));
		sentence.add(new WordToken("."));
		
		MaxentTagger tagger = VietnameseMaxentTaggerProvider.getInstance();
		Sentence<TaggedWord> taggedSentence = tagger.processSentence(sentence);
		for (TaggedWord taggedWord : taggedSentence) {
			System.out.println(taggedWord.tag());
		}
		
		VietnameseMaxentTagger tagger2 = new VietnameseMaxentTagger();
		String taggedContent = tagger2.tagText("Hành động nhanh chóng và hiệu quả, thử nghiệm thành công.");
		System.out.println(taggedContent);
	}
	
}
