package com.nlp.example.stanford;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class RelationshipExtractExample {
	
	public static void main(String[] args) {
		RelationshipExtractExample.basicRelationshipExtractor();
	}
	
	 private static void basicRelationshipExtractor() {
	        Properties properties = new Properties();
	        properties.put("annotators", "tokenize, ssplit, parse");
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
	        Annotation annotation = new Annotation("The meaning and purpose of life is plain to see.");
	        pipeline.annotate(annotation);
	        pipeline.prettyPrint(annotation, System.out);

	    }

}
