package com.nlp.example.lingpipe;

import java.io.File;
import java.util.List;

import com.aliasi.tokenizer.RegExTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;

public class DeDupExample {
	
	public static void main(String[] args) {
		
		String inputPath = args.length > 0 ? args[0] : "data/disney.csv";	
		String outputPath = args.length > 1 ? args[1] : "data/disneyDeduped.csv";	
		//List<String[]> data = Util.readCsvRemoveHeader(new File(inputPath));
	//	System.out.println(data.size());
		TokenizerFactory tokenizerFactory = new RegExTokenizerFactory("\\w+");
		double cutoff = .5d;
		//List<String[]> dedupedData = Util.filterJaccard(data, tokenizerFactory, cutoff);
	//	System.out.println(dedupedData.size());
		//Util.writeCsvAddHeader(dedupedData, new File(outputPath));
		
	}

}
