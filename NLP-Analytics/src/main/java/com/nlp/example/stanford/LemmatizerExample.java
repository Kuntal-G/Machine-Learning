package com.nlp.example.stanford;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class LemmatizerExample {
	
	
	public static void main(String[] args) {
		LemmatizerExample.basicLemmatizer();
	}
	
	private static void basicLemmatizer() {
         String paragraph = "Similar to stemming is Lemmatization. "
                + "This is the process of finding its lemma, its form "
                + "as found in a dictionary.";

        StanfordCoreNLP pipeline;
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(paragraph);
        pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        List<String> lemmas = new LinkedList<String>();
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel word : sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
                lemmas.add(word.get(LemmaAnnotation.class));
            }
        }

        System.out.print("[");
        for (String element : lemmas) {
            System.out.print(element + " ");
        }
        System.out.println("]");
    }


}
