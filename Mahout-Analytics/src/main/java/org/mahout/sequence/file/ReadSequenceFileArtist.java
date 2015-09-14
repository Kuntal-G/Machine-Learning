package org.mahout.sequence.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

/**
 * A sample example to read from sequence file and output as csv
 * @author kuntal
 *
 */
public class ReadSequenceFileArtist {

	public static void main(String[] argvs) throws IOException	{
		String filename = "/home/kuntal/lastfm/	sequencesfiles/part-0000";
		Path path = new Path(filename);
		String outputfilename = "/mnt/new/lastfm/sequencesfiles/dump.csv";
		FileWriter writer = new FileWriter(outputfilename);
		PrintWriter pw = new PrintWriter(writer);
		String newline = System.getProperty("line.separator");
		//creating header
		pw.print("key,value" + newline);
		//creating Sequence Writer
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		SequenceFile.Reader reader = new
				SequenceFile.Reader(fs,path,conf);
		LongWritable key = new LongWritable();
		Text value = new Text();
		while (reader.next(key, value)) {
			System.out.println( "reading key:" + key.toString() +
					" with value " + value.toString());
			pw.print(key.toString() + "," + value.toString() +
					newline);
		}
		reader.close();
		pw.close();
		writer.close();
	}
}