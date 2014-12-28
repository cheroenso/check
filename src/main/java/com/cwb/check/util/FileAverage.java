package com.cwb.check.util;

/**
 * Command line driver for averaging a series of numbers
 * @author chrisbrann
 *
 */
public class FileAverage {
	
	public static void main(String[] args){
		StringBuffer usage = new StringBuffer();
		usage.append("Usage: ");
		usage.append("\nFileAverage <file with single number on each line>");
		try {
			if (args == null || args.length != 1) {
				System.out.println(usage.toString());
			} else {
				System.out.println(Average.averageFile(args[0].toString()));
			}
		} catch (Exception e) {
			System.err.println("Error processing " + args[0]);
			System.err.println(e.getStackTrace());
		}
		
		
	}

}
