package org.mahout.clustering;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.evaluation.ClusterEvaluator;
import org.apache.mahout.clustering.evaluation.RepresentativePointsDriver;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

public class ClusterEvaluation {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		
		Configuration conf = new Configuration();
		//create the distance measure object
		DistanceMeasure measure = new EuclideanDistanceMeasure();
		
		RepresentativePointsDriver.run(conf, new Path("clustering_output_fkmeans/clusters-3-final"),
				new Path("/clustering_output", " /clusteredPoints"),
				new Path("/clustering_output_fkmeans"),measure,10, true);
		
		ClusterEvaluator cv = new ClusterEvaluator(conf,new Path("/clustering_output/clusters-3-final"));
		
		// Calculate the inter-cluster and intra-cluster density of the clusters:
		System.out.println(cv.interClusterDensity());
		System.out.println(cv.intraClusterDensity());
	}

}
