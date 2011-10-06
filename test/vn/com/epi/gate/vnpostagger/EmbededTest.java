package vn.com.epi.gate.vnpostagger;

import gate.Annotation;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.ProcessingResource;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.creole.splitter.SentenceSplitter;
import gate.util.ExtensionFileFilter;
import gate.util.GateException;
import gate.util.OffsetComparator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import vn.hus.nlp.plugin.TokenizerPlugin;

public class EmbededTest {

	private static ProcessingResource tokenizerPlugin = null;
	private static SerialAnalyserController controller;
	private static ProcessingResource taggerPlugin;

	@BeforeClass
	public static void initClass() throws GateException, IOException {
		Gate.init();
		Gate.getCreoleRegister().registerDirectories(
				new File(Gate.getPluginsHome(), "Tools").toURI().toURL()
				);
		controller = (SerialAnalyserController) Factory
				.createResource("gate.creole.SerialAnalyserController");
		
		Gate.getCreoleRegister().registerComponent(TokenizerPlugin.class);
		tokenizerPlugin = (ProcessingResource) Factory
				.createResource(TokenizerPlugin.class.getCanonicalName());
		controller.add(tokenizerPlugin);
		
		Gate.getCreoleRegister().registerComponent(SentenceSplitter.class);
		FeatureMap sentenceSplitterfeatures = Factory.newFeatureMap();
		sentenceSplitterfeatures.put("encoding", "UTF-8");
		sentenceSplitterfeatures.put("gazetteerListsURL", "file:/C:/Program%20Files/GATE-6.1/plugins/ANNIE/resources/sentenceSplitter/gazetteer/lists.def");
		sentenceSplitterfeatures.put("transducerURL", "file:/C:/Program%20Files/GATE-6.1/plugins/ANNIE/resources/sentenceSplitter/grammar/main.jape");
		ProcessingResource sentenceSplitter = (ProcessingResource) Factory
				.createResource(SentenceSplitter.class.getCanonicalName(), sentenceSplitterfeatures);
		controller.add(sentenceSplitter);
		
		Gate.getCreoleRegister().registerComponent(VnTaggerPlugin2.class);
		taggerPlugin = (ProcessingResource) Factory
				.createResource(VnTaggerPlugin2.class.getCanonicalName());
		controller.add(taggerPlugin);
	}
	
	@Test
	public void simple() throws ResourceInstantiationException, IOException, ExecutionException {
		Corpus corpus = createCorpus("corpuses/simple");
		controller.setCorpus(corpus);
		controller.init();
		controller.execute();
		Document doc = (Document) corpus.get(0);
		List<Annotation> annotList = new ArrayList<Annotation>(doc.getAnnotations());
		Collections.sort(annotList, new OffsetComparator());
		Annotation firstAnnot = annotList.get(0);
		for (Annotation ann : annotList) {
			System.out.print(ann.getFeatures().get("string"));
			if (ann.getFeatures().containsKey("category")) {
				System.out.println(" : " + ann.getFeatures().get("category"));
			}
		}
	}

	private Corpus createCorpus(String dirPath) throws ResourceInstantiationException,
			IOException, MalformedURLException {
		Corpus corpus = Factory.newCorpus("Simple corpus");
		File dir = new File(dirPath);
		ExtensionFileFilter filter = new ExtensionFileFilter("XML files", "xml");
		corpus.populate(dir.toURI().toURL(), filter, null, false);
		return corpus;
	}

	@AfterClass
	public static void afterClass() {
		
	}
	
}
