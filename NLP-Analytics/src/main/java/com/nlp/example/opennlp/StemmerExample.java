package com.nlp.example.opennlp;

import opennlp.tools.stemmer.PorterStemmer;

public class StemmerExample {
	
	public static void main(String[] args) {
		StemmerExample.basicPorterStemmer();
	}
	
	private static void basicPorterStemmer() {
        String words[] = {"bank", "banking", "banks", "banker",
            "banked", "bankart"};
        PorterStemmer ps = new PorterStemmer();
        for (String word : words) {
            String stem = ps.stem(word);
            System.out.println("Word: " + word + "  Stem: " + stem);
        }
    }


}
