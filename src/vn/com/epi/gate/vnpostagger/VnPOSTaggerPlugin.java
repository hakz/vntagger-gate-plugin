package vn.com.epi.gate.vnpostagger;

import java.util.LinkedList;

import gate.AnnotationSet;
import gate.Factory;
import gate.Resource;
import gate.corpora.DocumentContentImpl;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.InvalidOffsetException;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class VnPOSTaggerPlugin extends AbstractLanguageAnalyser {
	
	private VietnameseMaxentTagger tagger;
	private TagTranslator translator = new TagTranslator();

	@Override
	public Resource init() throws ResourceInstantiationException {
		tagger = new VietnameseMaxentTagger();			
		return super.init();
	}
	
	@Override
	public void execute() throws ExecutionException {
		try {
			LinkedList<AnnotationData> asList = new LinkedList<AnnotationData>();
			String content = document.getContent().toString();
			String taggedContent = tagger.tagText(content);
			StringBuilder sb = new StringBuilder(content.length());
			int lastPos = 0, pos;
			while ((pos = taggedContent.indexOf('/', lastPos)) >= 0) {
				int wsPos = taggedContent.indexOf(' ', pos+1);
				int nextSlashPos = taggedContent.indexOf(' ', pos+1);
				if (nextSlashPos >= 0 && nextSlashPos < wsPos) {
					pos = nextSlashPos;
				}
				String word = taggedContent.substring(lastPos, pos);
				if (wsPos < 0) {
					wsPos = taggedContent.length();
				}
				String tag = translator.translate(taggedContent.substring(pos+1, wsPos));
				
				if (lastPos > 0) {
					sb.append(' ');
				}
				long startIndex = sb.length();
				sb.append(word);
				long endIndex = sb.length();
				asList.add(new AnnotationData(startIndex, endIndex, tag));

				lastPos = wsPos+1;
			}

			String oldContent = content;
			content = sb.toString();
			document.edit((long)0, (long)oldContent.length(), new DocumentContentImpl(content));
			AnnotationSet as = document.getAnnotations();
			for (AnnotationData data : asList) {
				as.add(data.getStart(), data.getEnd(), data.getTag(), Factory.newFeatureMap());
			}
			
			System.out.println(content.substring(0, 100));
		} catch (InvalidOffsetException e) {
			throw new ExecutionException(e);
		}
	}
	
	
}
