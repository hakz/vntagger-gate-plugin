package vn.com.epi.gate.vnpostagger;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import gate.Annotation;
import gate.Factory;
import gate.FeatureMap;
import gate.Node;
import gate.Resource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.OffsetComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTaggerProvider;

public class VnTaggerPlugin2 extends AbstractLanguageAnalyser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The maxent tagger.
	 */
	private MaxentTagger tagger;
	
	
	@Override
	public Resource init() throws ResourceInstantiationException {
		tagger = VietnameseMaxentTaggerProvider.getInstance();
		return this;
	}
	
	
	@Override
	public void execute() throws ExecutionException {
		Comparator<Annotation> comparator = new OffsetComparator();
		for (Annotation sent : document.getAnnotations().get("Sentence")) {
			List<Annotation> tokens = new ArrayList<Annotation>(document
					.getAnnotations()
					.getContained(sent.getStartNode().getOffset(),
							sent.getEndNode().getOffset()).get("Token"));
			Collections.sort(tokens, comparator);

			Sentence<WordToken> sentence = new Sentence<WordToken>(tokens.size());
			for (Annotation token : tokens) {
				String string = (String) token.getFeatures().get("string");
				//
				// vì bộ vnTagger được huấn luyện với các từ ghép có gạch nối
				// ở giữa nên bắt buộc thay dấu cách bằng gạch nối
				//
				string = replaceSpace(string);
				sentence.add(new WordToken(string));
			}
			
			Sentence<TaggedWord> taggedSentence = tagger.apply(sentence);
			for (int i = 0; i < tokens.size(); i++) {
				String pos = taggedSentence.get(i).tag();
				tokens.get(i).getFeatures().put("category", pos);
			}
		}
	}


	private String replaceSpace(String s) {
		int pos = s.indexOf(' ');
		if (pos >= 0) {
			char[] chars = new char[s.length()];
			int i = 0;
			for ( ; i < pos; i++) {
				chars[i] = s.charAt(i);
			}
			chars[i++] = '_';
			for ( ; i < s.length(); i++) {
				chars[i] = s.charAt(i);
				if (chars[i] == ' ') {
					chars[i] = '_';
				}
			}
			return new String(chars);
		}
		return s;
	}

}
