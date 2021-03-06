package com.cwb.check.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.Logger;
//import java.util.logging.Logger;
//import java.util.logging.*;


import com.cwb.check.util.InputHandler;

public class Histogram {
	
	private static Logger log = LoggerFactory.getLogger(Histogram.class);
	// put all numbers in  original array
	// get minimum and maximum
	// get total size by subtract minimum from maximum
	// get bin size by dividing total count by number of bins
	// define separate array for each bin
	// process original array
	// for each member in array decide which bin it belongs to
	// increment the count for a specific bin if it matches
	// display bin boundaries followed by count of members
	
	public static String displayAll(String numBins, String fileName){

		StringBuffer report = new StringBuffer();
		InputHandler input = new InputHandler();
		// put all numbers in  original array
		ArrayList<String> valueSet = input.getAllRequestsFromFile(fileName);
		if (valueSet.isEmpty()){
			log.error("Unable to get valid numbers from " + fileName);
		}
		// Convert original String set to Double set
		ArrayList<Double> doubleSet = new ArrayList();

		Iterator<String> itr = valueSet.iterator();
		Double count = 0.0;

		while (itr.hasNext()){
			try {
				doubleSet.add(Double.parseDouble(itr.next()));
			} catch (Exception e) {
				String location = Double.toString(count +1).substring(0, Double.toString(count).indexOf('.'));
				log.error("Error parsing " + fileName + " at " + location + " ", e);
			}
		}

		log.trace("sorting doubleSet");
		Collections.sort(doubleSet);
//		Double max = doubleSet.get(doubleSet.size()-1);
		
		Iterator<Double> dblIter = doubleSet.iterator();
		dblIter = doubleSet.iterator();
		Bin[] binArr = makeBinArr(numBins);
		
		Double max = doubleSet.get(doubleSet.size()-1);
		Double bins = Double.valueOf(String.valueOf(binArr.length));
		// Define size of each bin
		Double size = max / bins;
		log.trace("bin size: " + size);		
		// Initialize Bins
		binArr = initializeBinArr(binArr, bins, size);

		log.debug("Allocate to bins");
		dblIter = doubleSet.iterator();
		while (dblIter.hasNext()){
			Double d = dblIter.next();
			log.trace("categorizing " + d);
			// find the right bin
			boolean found = false;
			int i = 0;
			while (!found && i < binArr.length){
				if (d > binArr[i].getLower() && d <= binArr[i].getUpper()){
					binArr[i].addToCount(1.0);
					found = true;
					log.debug("adding to bin: " + binArr[i].getName() + ", new count: " + binArr[i].getCount().intValue());
				}
				i++;
			}
		}
		report.append("Histogram of " + fileName + " into " + bins.toString() + " bins \n");
		for (int j = 0; j < binArr.length; j++){
			String bin = "bin: " + j + " range: " + binArr[j].getLower() + "-" + binArr[j].getUpper();
			report.append(bin + " count: " + binArr[j].getCount().intValue() + "\n");
		}
		return report.toString();
	}
	
	private static Bin[] makeBinArr(String numBins){
		Double bins = 0.0;
		try {
			bins = new Integer(numBins).doubleValue();
		} catch (Exception e){
			log.error(e.getMessage());
		}
		Bin[] binArr = new  Bin[bins.intValue()];
		return binArr;
	}
	
	private static Bin[] initializeBinArr(Bin[] binArr, Double bins, Double size){
		for ( int i = 0; i < bins.intValue(); i++){
			binArr[i] = new Bin(Integer.valueOf(i));
			binArr[i].setLower(i * size);
			binArr[i].setUpper((i*size) + size);
			log.debug("creating bin " + i + " with range from " + binArr[i].getLower() + " to " + binArr[i].getUpper());
		}
		return binArr;
	}
	
	private static class Bin{
		public Bin(int i) {
			name = i;
		};
		Integer name = null;
		Double binLower = 0.0;
		Double binUpper = 0.0;
		Double binTotal = 0.0;

		Integer getName(){
			return name;
		}
		void setLower(Double d){
			binLower = d;
		}
		Double getLower(){
			return binLower;
		}
		void setUpper(Double d){
			binUpper = d;
		}
		Double getUpper(){
			return binUpper;
		}
		void addToCount(Double d){
			binTotal += d;
		}
		Double getCount(){
			return binTotal;
		}
	}

}
