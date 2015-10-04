package org.mahout.classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.CrossFoldLearner;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

/**
 * CrossFold Learning with 5 Online Logistic Regression models
 * @author kuntal
 *
 */
public class CrossFoldLearning {
	
	static OnlineLogisticRegression lr = new OnlineLogisticRegression();

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/home/kuntal/git/Machine-Learning/Mahout-Analytics/src/main/resources/data/cancer.csv"));
		String line = br.readLine();
		int cnt_line = 0;
		CrossFoldLearner clf = new CrossFoldLearner(5, 2, 10, new L1()).lambda(1 * 1.0e-3).learningRate(50);

		while (line != null) {
			if (cnt_line > 0) {
				String[] values = line.split(",");
				double[] vecValues = new double[values.length];

				for (int i = 0; i < values.length - 2; i++) {
					vecValues[i] = Double.parseDouble(values[i]);
				}
				int target = Integer.parseInt(values[values.length - 1]);
				Vector v = new SequentialAccessSparseVector(values.length);
				v.assign(vecValues);
				clf.train(target, v);

			}
			line = br.readLine();
			cnt_line++;

		}
		System.out.println("Auc of cross fold learner is "+ clf.auc());
		br.close();
		int model_number=1;
		for (OnlineLogisticRegression model : clf.getModels()) {

			lr = model;
			br = new BufferedReader(new FileReader("/home/kuntal/git/Machine-Learning/Mahout-Analytics/src/main/resources/data/cancer.csv"));
			String pred_line = br.readLine();
			int cnt_pred_line = 0;
			Auc collector = new Auc();
			while (pred_line != null) {
				if (cnt_pred_line > 0) {
					String[] values = pred_line.split(",");
					double[] vecValues = new double[values.length];

					for (int i = 0; i < values.length - 2; i++) {
						vecValues[i] = Double.parseDouble(values[i]);
					}
					int target = Integer.parseInt(values[values.length - 1]);
					Vector v = new SequentialAccessSparseVector(values.length);
					v.assign(vecValues);
					double score = lr.classifyScalar(v);
					collector.add(target, score);
				}
				pred_line = br.readLine();
				cnt_pred_line++;

			}
			br.close();
			System.out.println("Auc of model " +model_number+ " = "+ collector.auc());
			Matrix m = collector.confusion();
			System.out.println("The confusion matrix is" +m);
			model_number++;
		}
	}

}


