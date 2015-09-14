package com.nlp.example.stanford;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;

public class SentenceDetectorExample {
	
	public static void main(String[] args) {
		SentenceDetectorExample.basicSentenceDetector();
		SentenceDetectorExample.basicWordToSentenceDetector();
	}
	
	 private static void basicSentenceDetector() {
	        String paragraph = "The first sentence. The second sentence.";
	        Reader reader = new StringReader(paragraph);
	        DocumentPreprocessor documentPreprocessor  = new DocumentPreprocessor(reader);
	        List<String> sentenceList = new LinkedList<String>();
	        for (List<HasWord> element : documentPreprocessor) {
	            StringBuilder sentence = new StringBuilder();
	            List<HasWord> hasWordList = element;
	            for (HasWord token : hasWordList) {
	                sentence.append(token).append(" ");
	            }
	            sentenceList.add(sentence.toString());
	        }
	        for (String sentence : sentenceList) {
	            System.out.println(sentence);
	        }

	    }
	 
	 
	 private static void basicWordToSentenceDetector() {
		String paragraph = "The colour of money is green. Common fraction "
	                + "characters such as ½  are converted to the long form 1/2. "
	                + "Quotes such as “cat” are converted to their simpler form.";
		PTBTokenizer  ptb = new PTBTokenizer( new StringReader(paragraph), new CoreLabelTokenFactory(),
	                "americanize=true,normalizeFractions=true,asciiQuotes=true");
		WordToSentenceProcessor wtsp = new WordToSentenceProcessor();
		List<List<CoreLabel>> sents = wtsp.process(ptb.tokenize());
	        for (List<CoreLabel> sent : sents) {
	            for (CoreLabel element : sent) {
	                System.out.print(element + " ");
	            }
	            System.out.println();
	        }   

	    }

}
