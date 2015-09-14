package com.nlp.example.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

public class CategorizeExample {
	
	public static void main(String[] args) {
		String text="";
		CategorizeExample.basicDocumentCategorizer(text);
	}
	
	
	private static void basicDocumentCategorizer(String text) {
        try {
        	InputStream modelIn = new FileInputStream(new File("en-animal.model"));
            DoccatModel model = new DoccatModel(modelIn);
            DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
            double[] outcomes = categorizer.categorize(text);
            for (int i = 0; i < categorizer.getNumberOfCategories(); i++) {
                String category = categorizer.getCategory(i);
                System.out.println(category + " - " + outcomes[i]);
            }
            System.out.println(categorizer.getBestCategory(outcomes));
            System.out.println(categorizer.getAllResults(outcomes));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
