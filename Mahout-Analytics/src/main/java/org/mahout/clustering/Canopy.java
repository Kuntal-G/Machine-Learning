package org.mahout.clustering;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;

public class Canopy {

	private static final String DIRECTORY_INPUT = "test/CanopyTest";
	private static final String DIRECTORY_CONTAINING_CONVERTED_INPUT = "data/CanopyConvertedInput";
	private static final String DIRECTORY_OUTPUT = "data/CanopyOutput";
	
	public static void main(String[] args) throws Exception {

		Path output = new Path(DIRECTORY_OUTPUT);
	      
	    Configuration conf = new Configuration();
	    
	    HadoopUtil.delete(conf, output);
	    
	    run(conf, new Path(DIRECTORY_INPUT), output, new EuclideanDistanceMeasure(), 0.1, 0.3, 0.5, 10);
	}
	
	public static void run(Configuration conf, Path input, Path output, DistanceMeasure measure, double t1, double t2,
	      double convergenceDelta, int maxIterations) throws Exception {
		
		// Preparing input
	    Path directoryContainingConvertedInput = new Path(output, DIRECTORY_CONTAINING_CONVERTED_INPUT);
	   
	    InputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.RandomAccessSparseVector");
	   
	    // Run Canopy to get initial clusters
	    Path canopyOutput = new Path(output, "canopies");
	    CanopyDriver.run(new Configuration(), directoryContainingConvertedInput, canopyOutput, measure, t1, t2, false, 0.0,
	        false);
	    
	    // Run K-means using the initial clusters found using Canopy algorithm 
	    KMeansDriver.run(conf, directoryContainingConvertedInput, new Path(canopyOutput, Cluster.INITIAL_CLUSTERS_DIR
	        + "-final"), output, convergenceDelta, maxIterations, true, 0.0, false);
	    
	    // run ClusterDumper
	    ClusterDumper clusterDumper = new ClusterDumper(new Path(output, "clusters-*-final"), new Path(output,
	        "clusteredPoints"));
	    clusterDumper.printClusters(null);
	  }
}
