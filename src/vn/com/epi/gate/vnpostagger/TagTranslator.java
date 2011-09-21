package vn.com.epi.gate.vnpostagger;

import java.util.HashMap;
import java.util.Map;

/**
 * Excerpt from vnTagger's README:
		1.  Np - Proper noun
		2.  Nc - Classifier
		3.  Nu - Unit noun
		4.  N - Common noun
		5.  V - Verb
		6.  A - Adjective
		7.  P - Pronoun
		8.  R - Adverb
		9.  L - Determiner
		10. M - Numeral
		11. E - Preposition
		12. C - Subordinating conjunction
		13. CC - Coordinating conjunction
		14. I - Interjection
		15. T - Auxiliary, modal words
		16. Y - Abbreviation
		17. Z - Bound morphemes
		18. X - Unknown
 * @author cumeo89
 *
 */
public class TagTranslator {

	private final static Map<String, String> tagMap;
	
	static {
		tagMap = new HashMap<String, String>();
		tagMap.put("Np", "Danh từ riêng");
		tagMap.put("Nu", "Danh từ đơn vị");
		tagMap.put("N", "Danh từ chung");
		tagMap.put("V", "Động từ");
		tagMap.put("A", "Tính từ");
		tagMap.put("P", "Đại từ");
		tagMap.put("R", "Phó từ");
		tagMap.put("M", "Số từ");
		tagMap.put("E", "Giới từ");
		tagMap.put("C", "Liên từ");
		tagMap.put("CC", "Liên từ");
		tagMap.put("I", "Thán từ");
	}
	
	public TagTranslator() {
	}
	
	public String translate(String tag) {
		String ret = tagMap.get(tag);
		return ret == null ? "Khác" : ret;
	}
	
}
