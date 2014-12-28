package com.cwb.check.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwb.check.util.InputHandler;

public class Average {
	/**
	 * Average times from input file. This assumes that teh file only contains the 
	 * values to be averaged. If any of the file contents cannot be converted to
	 * a double the program will exit and return an average of all values up to that point.
	 * 
	 */
	
	private static Logger log = LoggerFactory.getLogger(Average.class);
	
	public static Double averageFile(String fileName){
		InputHandler input = new InputHandler();
		ArrayList<String> valueSet = input.getAllRequestsFromFile(fileName);
		if (valueSet.isEmpty()){
			log.error("Unable to get valid numbers from " + fileName);
		}
		Iterator<String> itr = valueSet.iterator();
		Double count = 0.0;
		Double total = 0.0;
		Double average = 0.0;
		while (itr.hasNext()){
			try {
				total += Double.parseDouble(itr.next());
				count++;
			} catch (Exception e) {
				String location = Double.toString(count +1).substring(0, Double.toString(count).indexOf('.'));
				log.error("Error parsing " + fileName + " at " + location + " ", e);
			}
		}
		if (count > 0.0 && total > 0.0 ){
			average = total/count;
		}

		return average;
	}

}
