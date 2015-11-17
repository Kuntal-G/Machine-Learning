package com.nlp.example.opennlp;

import java.io.File;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class POSDetectionExample {
	
	public static void main(String[] args) {
		POSDetectionExample.basicPOS();
	}
	
	 private static void basicPOS() {
	        String sentence = "POS processing is useful for enhancing the "
	                + "quality of data sent to other elements of a pipeline.";

	        POSModel model = new POSModelLoader()
	                .load(new File("en-pos-maxent.bin"));
	        POSTaggerME tagger = new POSTaggerME(model);

	        String tokens[] = WhitespaceTokenizer.INSTANCE
	                .tokenize(sentence);
	        String[] tags = tagger.tag(tokens);

	        POSSample sample = new POSSample(tokens, tags);
	        String posTokens[] = sample.getSentence();
	        String posTags[] = sample.getTags();
	        for (int i = 0; i < posTokens.length; i++) {
	            System.out.print(posTokens[i] + " - " + posTags[i]);
	        }
	        System.out.println();

	        for (int i = 0; i < tokens.length; i++) {
	            System.out.print(tokens[i] + "[" + tags[i] + "] ");
	        }
	    }

}
