package vn.com.epi.gate.vnpostagger;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class TaggerTest {

	public static void main(String[] args) {
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		String taggedContent = tagger.tagText("Không có việc gì khó///chỉ sợ lòng không b�?n. �?ào núi và lấp biển, quyết chí ắt làm nên.");
		System.out.println(taggedContent);
	}
	
}
