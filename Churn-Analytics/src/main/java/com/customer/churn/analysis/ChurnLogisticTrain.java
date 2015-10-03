package com.customer.churn.analysis;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.classifier.sgd.RecordFactory;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.google.common.base.Charsets;



public class ChurnLogisticTrain {


	private static double predictorWeight(OnlineLogisticRegression lr, int row, RecordFactory csv, String predictor) {
		double weight = 0;
		for (Integer column : csv.getTraceDictionary().get(predictor)) {
			weight += lr.getBeta().get(row, column);
		}
		return weight;
	}


	public static void main(String[] args) throws IOException 
	{
		String inputFile = "/home/kuntal/Downloads/github-notes/churn_data_clean.all.csv";
		String outputFile = "/home/kuntal/Downloads/github-notes/lmodel";

		List<String> predictorList =Arrays.asList("account.length","area.code","international.plan",
				"voice.mail.plan","number.vmail.messages","total.day.minutes","total.day.calls",
				"total.eve.minutes","total.eve.calls","total.night.minutes","total.night.calls",
				"total.intl.minutes","total.intl.calls","number.customer.service.calls","avg.minute.day",
				"avg.minute.eve","avg.minute.night","avg.minute.intl");
		List<String> typeList = Arrays.asList("n","w","w","w","n","n","n","n","n","n","n","n","n","n","n","n","n","n");

		
		
		LogisticModelParameters lmp = new LogisticModelParameters();
		lmp.setTargetVariable("Status");
		lmp.setMaxTargetCategories(2);
		lmp.setNumFeatures(19);
		lmp.setUseBias(false);
		lmp.setTypeMap(predictorList,typeList);
		lmp.setLearningRate(0.5);


		int passes = 100;
		OnlineLogisticRegression lr;        

		CsvRecordFactory csv = lmp.getCsvRecordFactory();
		lr = lmp.createRegression();


		int k = 0;

		for (int pass = 0; pass < passes; pass++) {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));


			csv.firstLine(in.readLine());

			String line = in.readLine();
			int lineCount = 2;
			while (line != null) {

				Vector input = new RandomAccessSparseVector(lmp.getNumFeatures());
				int targetValue = csv.processLine(line, input);

				// update model
				lr.train(targetValue, input);
				k++;

				line = in.readLine();
				lineCount++;
			}
			in.close();
		}

		
		OutputStream modelOutput = new FileOutputStream(outputFile);
		try {
			lmp.saveTo(modelOutput);
		} finally {
			modelOutput.close();
		}
		PrintWriter output=new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8), true);
		output.println(lmp.getNumFeatures());
		output.println(lmp.getTargetVariable() + " ~ ");
		String sep = "";
		for (String v : csv.getTraceDictionary().keySet()) {
			double weight = predictorWeight(lr, 0, csv, v);
			if (weight != 0) {
				output.printf(Locale.ENGLISH, "%s%.3f*%s", sep, weight, v);
				sep = " + ";
			}
		}
		output.printf("%n");
		for (int row = 0; row < lr.getBeta().numRows(); row++) {
			for (String key : csv.getTraceDictionary().keySet()) {
				double weight = predictorWeight(lr, row, csv, key);
				if (weight != 0) {
					output.printf(Locale.ENGLISH, "%20s %.5f%n", key, weight);
				}
			}
			for (int column = 0; column < lr.getBeta().numCols(); column++) {
				output.printf(Locale.ENGLISH, "%15.9f ", lr.getBeta().get(row, column));
			}
			output.println();
		}
	}
}
