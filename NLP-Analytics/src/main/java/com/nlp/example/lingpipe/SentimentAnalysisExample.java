package com.nlp.example.lingpipe;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.util.Files;

public class SentimentAnalysisExample {
	
	
	private static void usingLingPipeSentimentAnalysis() {
      String[]  categories = new String[2];
        categories[0] = "neg";
        categories[1] = "pos";
       int  nGramSize = 8;
       DynamicLMClassifier  classifier = DynamicLMClassifier.createNGramProcess(categories, nGramSize);

        trainingLingPipeSentimentAnalysis(categories,classifier);
        classifyLingPipeSentimentAnalysis(classifier);
    }

    private static void trainingLingPipeSentimentAnalysis(String[] categories,DynamicLMClassifier  classifier) {
        String directory = "C:/Current Books/NLP and Java/Downloads/Sentiment Data";
        File trainingDirectory = new File(directory, "txt_sentoken");
        System.out.println("\nTraining.");
        for (int i = 0; i < categories.length; ++i) {
            Classification classification
                    = new Classification(categories[i]);
            File file = new File(trainingDirectory, categories[i]);
            File[] trainingFiles = file.listFiles();
            for (int j = 0; j < trainingFiles.length; ++j) {
                try {
                    String review = Files.readFromFile(trainingFiles[j], "ISO-8859-1");
                    Classified<CharSequence> classified;
                    classified = new Classified<CharSequence>((CharSequence)review, classification);
                    classifier.handle(classified);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
	
	 private static void classifyLingPipeSentimentAnalysis(DynamicLMClassifier  classifier) {
	        System.out.println("---------------");
	        //http://www.rottentomatoes.com/m/forrest_gump/
	        String review = "An overly sentimental film with a somewhat "
	                + "problematic message, but its sweetness and charm "
	                + "are occasionally enough to approximate true depth "
	                + "and grace. ";
	        System.out.println("Text: " + review);
	        Classification classification = classifier.classify(review);
	        String bestCategory = classification.bestCategory();
	        System.out.println("Best Category: " + bestCategory);

	        for (String category : classifier.categories()) {
	            System.out.println(category);
	        }
	    }

}
