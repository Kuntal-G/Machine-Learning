package com.nlp.example.lingpipe;

import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.PorterStemmerTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class NormalizerExample {
	
	public static void main(String[] args) {
		NormalizerExample.basicNormalization();
	}
	
	 private static void basicNormalization() {
	       String paragraph = "A simple approach is to create a class to hold and remove stopwords.";
	        TokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
	        factory = new LowerCaseTokenizerFactory(factory);
	        factory = new EnglishStopTokenizerFactory(factory);
	        factory = new PorterStemmerTokenizerFactory(factory);
	        //System.out.println(factory.tokenizer(paragraph.toCharArray(), 0, paragraph.length()));
	        Tokenizer tokenizer = factory.tokenizer(paragraph.toCharArray(), 0, paragraph.length());
	        for (String token : tokenizer) {
	            System.out.println(token);
	        }
	    }

}
