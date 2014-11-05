package com.google.gwt.sample.culturalspaces.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class ReadCVS {
 
  public static void main(String[] args) {
 
	ReadCVS obj = new ReadCVS();
	obj.run();
 
  }
 
  public void run() {
 
	String csvFile = "/Users/jasdeepkahlon/Desktop/CulturalSpaces.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
 
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
		        // use comma as separator
			String[] ADDRESS = line.split(cvsSplitBy);
 
			System.out.println("Space [name= " + ADDRESS[0]
					+", address= " + ADDRESS[2] 
                                 + ", suburb=" + ADDRESS[3]
                                 		+ ", longitude=" + ADDRESS[5] 
                                 				+ ", latitude=" + ADDRESS[6] + "]" );
 
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	System.out.println("Done");
  }
 
}