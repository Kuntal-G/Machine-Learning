package com.nlp.example.lingpipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.sentences.IndoEuropeanSentenceModel;
import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceChunker;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class SentenceDetectorExample {
	
	public static void main(String[] args) {
	String paragraph = "HepG2 cells were obtained from the American Type Culture Collection (Rockville, MD, USA) and were used only until passage 30. They were routinely grown at 37°C in Dulbecco’s modified Eagle’s medium (DMEM) containing 10 % fetal bovine serum (FBS), 2 mM glutamine, 1 mM sodium pyruvate, and 25 mM glucose (Invitrogen, Carlsbad, CA, USA) in a humidified atmosphere containing 5% CO2. For precursor and 13C-sugar experiments, tissue culture treated polystyrene 35 mm dishes (Corning Inc, Lowell, MA, USA) were seeded with 2 × 106 cells and grown to confluency in DMEM.";
	SentenceDetectorExample.basicIndoEuropeanModel(paragraph);
	SentenceDetectorExample.basicMedLineModel(paragraph);
	}
	
	private static void basicIndoEuropeanModel(String paragraph) {
        TokenizerFactory TOKENIZER_FACTORY  = IndoEuropeanTokenizerFactory.INSTANCE;
        SentenceModel sentenceModel = new IndoEuropeanSentenceModel();

        List<String> tokenList = new ArrayList<String>();
        List<String> whiteList = new ArrayList<String>();
        Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(paragraph.toCharArray(),
                        0, paragraph.length());
        tokenizer.tokenize(tokenList, whiteList);

        String[] tokens = new String[tokenList.size()];
        String[] whites = new String[whiteList.size()];
        tokenList.toArray(tokens);
        whiteList.toArray(whites);
        int[] sentenceBoundaries
                = sentenceModel.boundaryIndices(tokens, whites);
//        System.out.println(tokenList.size());
//        System.out.println(whiteList.size());
//        System.out.println("[" + whiteList.get(0) + "]");
        for (int boundary : sentenceBoundaries) {
            System.out.println(boundary);
        }
        int start = 0;
        for (int boundary : sentenceBoundaries) {
            while (start <= boundary) {
                System.out.print(tokenList.get(start) + whiteList.get(start + 1));
                start++;
            }
            System.out.println();
        }

    }
	
	
	private static void basicMedLineModel(String paragraph) {
             
        TokenizerFactory tokenizerfactory  = IndoEuropeanTokenizerFactory.INSTANCE;
       MedlineSentenceModel sentenceModel = new MedlineSentenceModel();
        SentenceChunker sentenceChunker = new SentenceChunker(tokenizerfactory, sentenceModel);

        Chunking chunking = sentenceChunker.chunk(
                paragraph.toCharArray(),0, paragraph.length());
        Set<Chunk> sentences = chunking.chunkSet();
        String slice = chunking.charSequence().toString();

//        int i = 1;
        for (Chunk sentence : sentences) {
//            int start = sentence.start();
//            int end = sentence.end();
//            System.out.println("SENTENCE " + (i++) + ":");
            System.out.println("[" + 
                    slice.substring(sentence.start(), sentence.end()) + "]");
        }

    }

}
