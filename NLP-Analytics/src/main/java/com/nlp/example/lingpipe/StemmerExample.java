package com.nlp.example.lingpipe;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.PorterStemmerTokenizerFactory;
import com.aliasi.tokenizer.Tokenization;
import com.aliasi.tokenizer.TokenizerFactory;

public class StemmerExample {
	
	public static void main(String[] args) {
		StemmerExample.basicStemmer();
	}
	
	 private static void basicStemmer() {
	        
		 String words[] = {"bank", "banking", "banks", "banker","banked", "bankart"};
	        TokenizerFactory tokenizerFactory= IndoEuropeanTokenizerFactory.INSTANCE;
	        TokenizerFactory porterFactory = new PorterStemmerTokenizerFactory(tokenizerFactory);
	        String[] stems = new String[words.length];
	        for (int i = 0; i < words.length; i++) {
	            Tokenization tokenizer = new Tokenization(words[i], porterFactory);
	            stems = tokenizer.tokens();
	            System.out.print("Word: " + words[i]);
	            for (String stem : stems) {
	                System.out.println("  Stem: " + stem);
	            }
	        }

	    }

}
