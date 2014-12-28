package com.cwb.check.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.slf4j.impl.*;

/**
 * Command line driver for averaging a series of numbers
 * @author chrisbrann
 *
 */
public class FileHistogram {
	
	private static Logger logger = LoggerFactory.getLogger(FileHistogram.class);
	
	public static void main(String[] args){
		StringBuffer usage = new StringBuffer();
		usage.append("Usage: ");
		usage.append("\nFileAverage <number of bins> <file with single number on each line>");
		try {
			if (args == null || args.length != 2) {
				System.out.println(args[0]);
				System.out.println(usage.toString());
			} else if ("-h".equalsIgnoreCase(args[0])) {
				System.out.println(usage.toString());
			} else if (args.length != 2) {
				System.out.println(usage.toString());
			} else {
				System.out.println(Histogram.displayAll(args[0].toString(), args[1].toString()));
			}
		} catch (Exception e) {
			logger.error("Error processing " + args[0]);
			System.err.println(e.getStackTrace());
		}
		
		
	}

}
