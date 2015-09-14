package com.nlp.example.lingpipe;

import java.util.ArrayList;
import java.util.List;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;

public class TokenizerExample {
	
	public static void main(String[] args) {
		TokenizerExample.basicTokenizer();
	}
	
	 private static void basicTokenizer() {
	        List<String> tokenList = new ArrayList<String>();
	        List<String> whiteList = new ArrayList<String>();
	        String text = "A sample sentence processed \nby \tthe LingPipe tokenizer.";
	       //Using Indo European Tokenizer.
	        Tokenizer tokenizer = IndoEuropeanTokenizerFactory.INSTANCE.tokenizer(text.toCharArray(), 0, text.length());
	        tokenizer.tokenize(tokenList, whiteList);
	        for (String element : tokenList) {
	            System.out.print(element + " ");
	        }
	        System.out.println();

	    }

}
