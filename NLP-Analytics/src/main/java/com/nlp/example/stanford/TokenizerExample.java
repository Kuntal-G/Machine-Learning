package com.nlp.example.stanford;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

public class TokenizerExample {
	
	public static void main(String[] args) {
		
		String paragraph="He lives at 1511 W. Randolph.";
		TokenizerExample.basicPTBTokenizer(paragraph);
		TokenizerExample.basicCoreLabelTokenizer(paragraph);
		TokenizerExample.basicDocumentProcessor(paragraph);
		TokenizerExample.basicPipeline(paragraph);
	}
	
	 private static void basicPTBTokenizer(String paragraph) {
	        PTBTokenizer ptb = new PTBTokenizer(new StringReader(paragraph),new CoreLabelTokenFactory(), null);
	        while (ptb.hasNext()) {
	            System.out.println(ptb.next());
	        }

	    }

	 
	 
	 private static void basicCoreLabelTokenizer(String paragraph) {
		 CoreLabelTokenFactory ctf = new CoreLabelTokenFactory();
	        PTBTokenizer ptb = new PTBTokenizer(new StringReader(paragraph),ctf, "invertible=true");
	     //   PTBTokenizer ptb = new PTBTokenizer(new StringReader(paragraph), new WordTokenFactory(), null);
	        while (ptb.hasNext()) {
	            CoreLabel cl = (CoreLabel) ptb.next();
	            System.out.println(cl.originalText() + " ("+ cl.beginPosition() + "-" + cl.endPosition() + ")");
	        }


	    }

	 
	 
	 
	 private static void basicDocumentProcessor(String paragraph) {
		 Reader reader = new StringReader(paragraph);
	        DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(reader);

	        Iterator<List<HasWord>> it = documentPreprocessor.iterator();
	        while (it.hasNext()) {
	            List<HasWord> sentence = it.next();
	            for (HasWord token : sentence) {
	                System.out.println(token);
	            }
	        }

	    }

	 
	 private static void basicPipeline(String paragraph) {
		 Properties properties = new Properties();
	        properties.put("annotators", "tokenize, ssplit");

	        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
	        Annotation annotation = new Annotation(paragraph);

	        pipeline.annotate(annotation);
	        pipeline.prettyPrint(annotation, System.out);


	    }

	 
}
