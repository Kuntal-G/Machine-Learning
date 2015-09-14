package com.nlp.example.lingpipe;

import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;

public class StopwordExample {
	
	
	public static void main(String[] args) {
		StopwordExample.basicStopWord();
	}
	
	private static void basicStopWord() {
        String paragraph = "A simple approach is to create a class to hold and remove stopwords.";
        TokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
//        factory = new LowerCaseTokenizerFactory(factory);
        factory = new EnglishStopTokenizerFactory(factory);
//         factory = new PorterStemmerTokenizerFactory(factory);
        System.out.println(factory.tokenizer(paragraph.toCharArray(), 0, paragraph.length()));
        com.aliasi.tokenizer.Tokenizer tokenizer
                = factory.tokenizer(paragraph.toCharArray(), 0, paragraph.length());
        for (String token : tokenizer) {
            System.out.println(token);
        }
    }

}
