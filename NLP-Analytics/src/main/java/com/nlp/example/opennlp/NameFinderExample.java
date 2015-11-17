package com.nlp.example.opennlp;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

public class NameFinderExample {
	
	public static void main(String[] args) {
		NameFinderExample.baiscnameFinder();
	}
	
	private static void baiscnameFinder() {
        try {
            String[] sentences = {
                "Tim was a good neighbor. Perhaps not as good a Bob "
                + "Haywood, but still pretty good. Of course Mr. Adam "
                + "took the cake!"};
            Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            TokenNameFinderModel model = new TokenNameFinderModel(new File("en-ner-person.bin"));
            NameFinderME finder = new NameFinderME(model);

            for (String sentence : sentences) {
                // Split the sentence into tokens
                String[] tokens = tokenizer.tokenize(sentence);

                // Find the names in the tokens and return Span objects
                Span[] nameSpans = finder.find(tokens);

                // Print the names extracted from the tokens using the Span data
                System.out.println(Arrays.toString( Span.spansToStrings(nameSpans, tokens)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
