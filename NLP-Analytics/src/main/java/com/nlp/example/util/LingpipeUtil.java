package com.nlp.example.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

import com.aliasi.classify.BaseClassifier;
import com.aliasi.classify.Classification;
import com.aliasi.classify.ConfusionMatrix;
import com.aliasi.util.Strings;

public class LingpipeUtil {

	public static int ANNOTATION_OFFSET = 2; 
	public static int TEXT_OFFSET = 3; 


	//Annotated CSV Header Removes
	public static List<String[]> readAnnotatedCsvRemoveHeader(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,Strings.UTF8);
		CSVReader csvReader = new CSVReader(inputStreamReader);
		csvReader.readNext();//skip headers
		List<String[]> rows = new ArrayList<String[]>();
		String[] row;

		while ((row = csvReader.readNext()) != null) {

			if (row[ANNOTATION_OFFSET].equals("")) {
				continue;
			}
			rows.add(row);
		}
		csvReader.close();
		return rows;
	}

	//CSV header remove
	public static List<String[]> readCsvRemoveHeader(File file) throws IOException {
		FileInputStream fileIn = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileIn,Strings.UTF8);
		CSVReader csvReader = new CSVReader(inputStreamReader);
		csvReader.readNext();//skip headers
		List<String[]> rows = new ArrayList<String[]>();
		String[] row;
		while ((row = csvReader.readNext()) != null) {
			if (row[TEXT_OFFSET] == null || row[TEXT_OFFSET].equals("")) {
				continue;
			}
			rows.add(row);
		}
		csvReader.close();
		return rows; 
	}

	//Print Confusion Matrix
	public static void printConfusionMatrix(ConfusionMatrix confMatrix) {
		System.out.println(confusionMatrixToString(confMatrix));
	}
	//Confusion Matix to String outpput
	public static String confusionMatrixToString(ConfusionMatrix confMatrix) {
		StringBuilder sb = new StringBuilder();
		String[] labels = confMatrix.categories();
		int[][] outcomes = confMatrix.matrix();
		sb.append("reference\\response\n");
		sb.append("          \\");
		for (String category : labels) {
			sb.append(category + ",");
		}
		for (int i = 0; i< outcomes.length; ++ i) {
			sb.append("\n         " + labels[i] + " ");
			for (int j = 0; j < outcomes[i].length; ++j) {
				sb.append(outcomes[i][j] + ",");
			}
		}
		return sb.toString();
	}


	//Get catergoris of Data
	public static String[] getCategories(List<String[]> data) {
		Set<String> categories = new HashSet<String>();
		for (String[] csvData : data) {
			if (!csvData[ANNOTATION_OFFSET].equals("")) {
				categories.add(csvData[ANNOTATION_OFFSET]);
			}
		}
		return categories.toArray(new String[0]);
	}
	
	//Print Classification to Console
	public static void consoleInputPrintClassification(BaseClassifier<CharSequence> classifier) throws IOException {
		BufferedReader reader = new BufferedReader(new 	InputStreamReader(System.in));
		while (true) {
			System.out.println("\nType a string to be classified. Empty string to quit.");
			String data = reader.readLine();
			if (data.equals("")) {
				return;
			}
			Classification classification 
			= classifier.classify(data);
			System.out.println(classification);
		}
	}
}
