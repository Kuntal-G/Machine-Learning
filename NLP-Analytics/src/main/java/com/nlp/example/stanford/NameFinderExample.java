package com.nlp.example.stanford;

import java.util.List;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class NameFinderExample {
	
	public static void main(String[] args) {
		NameFinderExample.basicNER();
	}
	 private static void basicNER() {
		 String sentences[] = {"Joe was the last person to see Fred. ",
			        "He saw him in Boston at McKenzie's pub at 3:00 where he paid "
			        + "$2.45 for an ale. ",
			        "Joe wanted to go to Vermont for the day to visit a cousin who "
			        + "works at IBM, but Sally and he had to look for Fred"};
		 
	        String model = "english.conll.4class.distsim.crf.ser.gz";
	        CRFClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(model);

	        String sentence = "";
	        for (String element : sentences) {
	            sentence += element;
	        }

	        List<List<CoreLabel>> entityList = classifier.classify(sentence);

	        for (List<CoreLabel> internalList : entityList) {
	            for (CoreLabel coreLabel : internalList) {
	                String word = coreLabel.word();
	                String category = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
//	                System.out.println(word + ":" + category);
	                if (!"O".equals(category)) {
	                    System.out.println(word + ":" + category);
	                }

	            }

	        }
	    }


}
