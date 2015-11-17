package com.nlp.example.stanford;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;

public class POSDetectionExample {
	
	public static void main(String[] args) {
		String textsentences
         = "The voyage of the Abraham Lincoln was for a long time marked by "
         + "no special incident.";	
		
		POSDetectionExample.usingStanfordMaxentPOS();
		POSDetectionExample.usingStanfordPOSTagger(textsentences);
	}
	
	  private static void usingStanfordMaxentPOS() {
	        try {
	            MaxentTagger tagger = new MaxentTagger("//wsj-0-18-bidirectional-distsim.tagger");
//	            MaxentTagger tagger = new MaxentTagger(getModelDir() + "//gate-EN-twitter.model");
//	            System.out.println(tagger.tagString("AFAIK she H8 cth!"));
//	            System.out.println(tagger.tagString("BTW had a GR8 tym at the party BBIAM."));
	            List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader("sentences.txt")));
	            for (List<HasWord> sentence : sentences) {
	                List<TaggedWord> taggedSentence = tagger.tagSentence(sentence);
	                // Simple display
	                System.out.println("---" + taggedSentence);
	                // Simple conversion to String
//	                System.out.println(Sentence.listToString(taggedSentence, false));
	                // Display of words and tags
//	                for (TaggedWord taggedWord : taggedSentence) {
//	                    System.out.print(taggedWord.word() + "/" + taggedWord.tag() + " ");
//	                }
//	                System.out.println();
	                // List of specifc tags
//	                System.out.print("NN Tagged: ");
//	                for (TaggedWord taggedWord : taggedSentence) {
//	                    if (taggedWord.tag().startsWith("NN")) {
//	                        System.out.print(taggedWord.word() + " ");
//	                    }
//	                }
//	                System.out.println();
	            }
	        } catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        }
	    }

	    private static void usingStanfordPOSTagger(String textsentences) {
	        Properties props = new Properties();
	        props.put("annotators", "tokenize, ssplit, pos");
	        props.put("pos.model", "english-caseless-left3words-distsim.tagger");
	        props.put("pos.maxlen", 10);
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	        Annotation document = new Annotation(textsentences);
	        pipeline.annotate(document);

	        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	        for (CoreMap sentence : sentences) {
	            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	                String word = token.get(TextAnnotation.class);
	                String pos = token.get(PartOfSpeechAnnotation.class);
	                System.out.print(word + "/" + pos + " ");
	            }
	            System.out.println();

	            try {
	                pipeline.xmlPrint(document, System.out);
	                pipeline.prettyPrint(document, System.out);
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

}
