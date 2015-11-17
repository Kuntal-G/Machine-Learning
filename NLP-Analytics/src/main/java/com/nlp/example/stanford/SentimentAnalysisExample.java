package com.nlp.example.stanford;

import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalysisExample {
	
	
	public static void main(String[] args) {
		SentimentAnalysisExample.basicSentimentAnalysis();
	}
	
	private static void basicSentimentAnalysis() {
        String review = "An overly sentimental film with a somewhat "
                + "problematic message, but its sweetness and charm "
                + "are occasionally enough to approximate true depth "
                + "and grace. ";

        String sam = "Sam was an odd sort of fellow. Not prone to angry and "
                + "not prone to merriment. Overall, an odd fellow.";
        String mary = "Mary thought that custard pie was the best pie in the "
                + "world. However, she loathed chocolate pie.";
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = new Annotation(review);
        pipeline.annotate(annotation);

        System.out.println("---sentimentText");
        String[] sentimentText = {"Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};
        for (CoreMap sentence : annotation.get( CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentAnnotatedTree.class);
            System.out.println("---Number of children: " + tree.numChildren());
            System.out.println("[" + tree.getChild(0) + "][" + tree.getChild(1) + "]");
            tree.printLocalTree();
            int score = RNNCoreAnnotations.getPredictedClass(tree);
            System.out.println(sentimentText[score]);
        }

        // Classifer
        CRFClassifier crf  = CRFClassifier.getClassifierNoExceptions("english.all.3class.distsim.crf.ser.gz");
        String S1 = "Good afternoon Rajat Raina, how are you today?";
        String S2 = "I go to school at Stanford University, which is located in California.";
        System.out.println(crf.classifyToString(S1));
        System.out.println(crf.classifyWithInlineXML(S2));
        System.out.println(crf.classifyToString(S2, "xml", true));

        Object classification[] = crf.classify(S2).toArray();
        for (int i = 0; i < classification.length; i++) {
            System.out.println(classification[i]);
        }
    }


}
