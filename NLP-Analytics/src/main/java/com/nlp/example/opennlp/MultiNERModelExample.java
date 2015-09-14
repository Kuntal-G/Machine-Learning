package com.nlp.example.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class MultiNERModelExample {
	
	public static void main(String[] args) {
		
		 final String sentences[] = {"Joe was the last person to see Fred. ",
		        "He saw him in Boston at McKenzie's pub at 3:00 where he paid "
		        + "$2.45 for an ale. ",
		        "Joe wanted to go to Vermont for the day to visit a cousin who "
		        + "works at IBM, but Sally and he had to look for Fred"};

		MultiNERModelExample.basicMultipleNERModels(sentences);
	}
	
	 private static void basicMultipleNERModels(String[] sentences) {
	        // Models - en-ner-person.bin en-ner-location.bin en-ner-money.bin 
	        // en-ner-organization.bin en-ner-time.bin
	        try {
	            InputStream tokenStream = new FileInputStream(new File( "en-token.bin")    );

	            TokenizerModel tokenModel = new TokenizerModel(tokenStream);
	            Tokenizer tokenizer = new TokenizerME(tokenModel);

	            String modelNames[] = {"en-ner-person.bin", "en-ner-location.bin", "en-ner-organization.bin"};
	            ArrayList<String> list = new ArrayList();
	            for (String name : modelNames) {
	                TokenNameFinderModel entityModel = new TokenNameFinderModel(new FileInputStream(new File(name)));
	                NameFinderME nameFinder = new NameFinderME(entityModel);
	                for (int index = 0; index < sentences.length; index++) {
	                    String tokens[] = tokenizer.tokenize(sentences[index]);
	                    Span nameSpans[] = nameFinder.find(tokens);
	                    for (Span span : nameSpans) {
	                        list.add("Sentence: " + index
	                                + " Span: " + span.toString() + " Entity: "
	                                + tokens[span.getStart()]);
	                    }
	                }
	            }
	            System.out.println("Multiple Entities");
	            for (String element : list) {
	                System.out.println(element);
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }


}
