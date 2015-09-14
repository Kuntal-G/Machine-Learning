package com.nlp.example.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class TokenizerExample {
	
	public static void main(String[] args) {
		TokenizerExample.basicTokenizer();
	}
	
	private static void basicTokenizer() {
        try {
        	InputStream is = new FileInputStream( new File("en-token.bin")) ;
            TokenizerModel model = new TokenizerModel(is);
            Tokenizer tokenizer = new TokenizerME(model);
            String tokens[] = tokenizer.tokenize("He lives at 1511 W. Randolph.");
            for (String a : tokens) {
                System.out.print("[" + a + "] ");
            }
            System.out.println();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
