package org.mahout.clustering;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.common.PartialVectorMerger;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

public class ClusteringDemo {

    String outputFolder;
    Configuration configuration;
    FileSystem fileSystem;
    Path documentsSequencePath;
    Path tokenizedDocumentsPath;
    Path tfidfPath;
    Path termFrequencyVectorsPath;

    public static void main(String args[]) throws Exception {
        ClusteringDemo tester = new ClusteringDemo();

        tester.createTestDocuments();
        tester.calculateTfIdf();
        tester.clusterDocs();

        tester.printSequenceFile(tester.documentsSequencePath);

        System.out.println("\n Clusters: ");
        tester.printSequenceFile(new Path(tester.outputFolder
                + "clusters/clusteredPoints/part-m-00000"));
    }

    public ClusteringDemo() throws IOException {
        configuration = new Configuration();
        fileSystem = FileSystem.get(configuration);

        outputFolder = "output/";
        documentsSequencePath = new Path(outputFolder, "sequence");
        tokenizedDocumentsPath = new Path(outputFolder,
                DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
        tfidfPath = new Path(outputFolder + "tfidf");
        termFrequencyVectorsPath = new Path(outputFolder
                + DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
    }

    public void createTestDocuments() throws IOException {
        SequenceFile.Writer writer = new SequenceFile.Writer(fileSystem,
                configuration, documentsSequencePath, Text.class, Text.class);

        Text id1 = new Text("Document 1");
        Text text1 = new Text("John saw a red car.");
        writer.append(id1, text1);

        Text id2 = new Text("Document 2");
        Text text2 = new Text("Marta found a red bike.");
        writer.append(id2, text2);

        Text id3 = new Text("Document 3");
        Text text3 = new Text("Don need a blue coat.");
        writer.append(id3, text3);

        Text id4 = new Text("Document 4");
        Text text4 = new Text("Mike bought a blue boat.");
        writer.append(id4, text4);

        Text id5 = new Text("Document 5");
        Text text5 = new Text("Albert wants a blue dish.");
        writer.append(id5, text5);

        Text id6 = new Text("Document 6");
        Text text6 = new Text("Lara likes blue glasses.");
        writer.append(id6, text6);

        Text id7 = new Text("Document 7");
        Text text7 = new Text("Donna, do you have red apples?");
        writer.append(id7, text7);

        Text id8 = new Text("Document 8");
        Text text8 = new Text("Sonia needs blue books.");
        writer.append(id8, text8);

        Text id9 = new Text("Document 9");
        Text text9 = new Text("I like blue eyes.");
        writer.append(id9, text9);

        Text id10 = new Text("Document 10");
        Text text10 = new Text("Arleen has a red carpet.");
        writer.append(id10, text10);

        writer.close();
    }

    public void calculateTfIdf() throws ClassNotFoundException, IOException,
            InterruptedException {
        DocumentProcessor.tokenizeDocuments(documentsSequencePath,
                StandardAnalyzer.class, tokenizedDocumentsPath, configuration);

        DictionaryVectorizer.createTermFrequencyVectors(tokenizedDocumentsPath,
                new Path(outputFolder),
                DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER,
                configuration, 1, 1, 0.0f, PartialVectorMerger.NO_NORMALIZING,
                true, 1, 100, false, false);

        Pair<Long[], List<Path>> documentFrequencies = TFIDFConverter
                .calculateDF(termFrequencyVectorsPath, tfidfPath,
                        configuration, 100);

        TFIDFConverter.processTfIdf(termFrequencyVectorsPath, tfidfPath,
                configuration, documentFrequencies, 1, 100,
                PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);
    }

    void clusterDocs() throws ClassNotFoundException, IOException,
            InterruptedException {
        String vectorsFolder = outputFolder + "tfidf/tfidf-vectors/";
        String canopyCentroids = outputFolder + "canopy-centroids";
        String clusterOutput = outputFolder + "clusters";

        FileSystem fs = FileSystem.get(configuration);
        Path oldClusterPath = new Path(clusterOutput);

        if (fs.exists(oldClusterPath)) {
            fs.delete(oldClusterPath, true);
        }

        CanopyDriver.run(new Path(vectorsFolder), new Path(canopyCentroids),
                new EuclideanDistanceMeasure(), 20, 5, true, 0, true);

        FuzzyKMeansDriver.run(new Path(vectorsFolder), new Path(
                canopyCentroids, "clusters-0-final"), new Path(clusterOutput),
                0.01, 20, 2, true, true, 0, false);
    }

    void printSequenceFile(Path path) {
        SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<Writable, Writable>(
                path, configuration);
        for (Pair<Writable, Writable> pair : iterable) {
            System.out
                    .format("%10s -> %s\n", pair.getFirst(), pair.getSecond());
        }
    }
}
