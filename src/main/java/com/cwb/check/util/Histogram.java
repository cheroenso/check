package com.cwb.check.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import sun.misc.Sort;

import com.cwb.check.util.InputHandler;

public class Histogram {
	// put all numbers in  original array
	// get minimum and maximum
	// get total size by subtract minimum from maximum
	// get bin size by dividing total count by number of bins
	// define separate array for each bin
	// process original array
	//    for each member in array decide which bin it belongs to
	//    increment the count for a specific bin if it matches
	// display bin boundaries followed by count of members
	
	public static Double displayAll(String fileName){
		InputHandler input = new InputHandler();
		// put all numbers in  original array
		ArrayList<String> valueSet = input.getAllRequestsFromFile(fileName);
		if (valueSet.isEmpty()){
			System.err.println("Unable to get valid numbers from " + fileName);
		}
		// Convert String to Double
		ArrayList<Double> doubleSet = new ArrayList();
		Iterator<String> itr = valueSet.iterator();
		Double count = 0.0;
		Double total = 0.0;
		Double bins = 3.0;
		while (itr.hasNext()){
			try {
				doubleSet.add(Double.parseDouble(itr.next()));
//				count++;
			} catch (Exception e) {
				String location = Double.toString(count +1).substring(0, Double.toString(count).indexOf('.'));
				System.err.println("Error parsing " + fileName + " at " + location);
				System.err.println(e.getMessage());
			}
		}
		Iterator<Double> dblIter = doubleSet.iterator();
////		bins = doubleSet.size()/;
		while (dblIter.hasNext()){
			System.out.println(dblIter.next());
		}

//		System.out.println(valueSet.get(0));
//		System.out.println(valueSet.get(valueSet.size() - 1));
		
//		while (itr.hasNext()){
//			System.out.println(itr.hasNext());
//		}
		System.out.println("Sorting doubleSet");
		Collections.sort(doubleSet);
		Double max = doubleSet.get(doubleSet.size()-1);
//		dblIter = doubleSet.iterator();
//		while (dblIter.hasNext()){
//			total += dblIter.next();
//			System.out.println(dblIter.next());
//		}
//		System.out.println("total: " + total);
//		Double size = total / bins;
		Double size = max / bins;
		System.out.println("bin size: " + size);
//		System.out.println("after total run");
		
		dblIter = doubleSet.iterator();
//		System.out.println("total bins: " + (bins.intValue() + 1));
//		Bin[] binArr = new  Bin[bins.intValue() +1];
		Bin[] binArr = new  Bin[bins.intValue()];
		// Initialize Bins
		for ( int i = 0; i < bins.intValue(); i++){
			binArr[i] = new Bin(Integer.valueOf(i));
			binArr[i].setLower(i * size);
			binArr[i].setUpper((i*size) + size);
			System.out.println("lower: " + binArr[i].getLower());
		}
//		Double doubleArr[][];
//		while (dblIter.hasNext()){
//			
//			System.out.println(dblIter.next());
//		}
		System.out.println("third run");
		dblIter = doubleSet.iterator();
		while (dblIter.hasNext()){
			Double d = dblIter.next();
			System.out.println("looking for " + d);
			// find the right bin
			boolean found = false;
			int i = 0;
			while (!found && i <= binArr.length){
				Double lower = binArr[i].getLower();
				Double upper = binArr[i].getUpper();
				System.out.println("Checking bin " + binArr[i].getName() + ", lower: " + lower + ", upper: " + upper);
				if (d > binArr[i].getLower() && d <= binArr[i].getUpper()){
					binArr[i].addToCount(1.0);
					found = true;
					System.out.println("bin: " + binArr[i].getName() + ", count: " + binArr[i].getCount());
				}
				i++;
			}

		}
		System.out.println("Histogram:");
		for (int j = 0; j < binArr.length; j++){
			System.out.println("bin: " + binArr[j].getName() + ", count: " + binArr[j].getCount());
		}
		return bins;
	}
	
	private Bin findBin(Double d){
		Bin b = new Bin(1);
		return b;
	}
	
	private static class Bin{
		public Bin(int i) {
			name = i;
		};
		Integer name = null;
		Double binLower = 0.0;
		Double binUpper = 0.0;
		Double binTotal = 0.0;
		void setName(Integer i){
			name = i;
		}
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
