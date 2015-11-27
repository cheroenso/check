package com.cwb.check.util;

import java.io.*;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cbrann
 * 
 */
public class InputHandler {

	private static Logger log = LoggerFactory.getLogger(InputHandler.class);
	/**
	 * Get requests from file
	 * @param fileName
	 * @return
	 */
	public ArrayList<String> getAllRequestsFromFile(String fileName){
		ArrayList<String> requestSet = new ArrayList<String>();
    	StringBuffer buff = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    	String line = null;
	    	while ((line = reader.readLine()) != null) {
	    		buff.append(line);	
	    		requestSet.add(line);
	    	}
	    	reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return requestSet;	
	}
	
	/**
	 * Get file contents as string
	 * @param fileName
	 * @return
	 */
	public static String getFileContent(String fileName) {

    	StringBuffer buff = new StringBuffer();
		try {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    	String line = null;
		    	while ((line = reader.readLine()) != null) {
		    		buff.append(line);	
		    	}
		    	reader.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		return buff.toString();
	}
}

