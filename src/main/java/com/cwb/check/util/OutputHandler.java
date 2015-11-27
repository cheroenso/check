package com.cwb.check.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chrisbrann
 *
 */
public class OutputHandler {
	public static String outputPath = "/tmp/imgcheck/";
	public static String fileNameFormat = "yyyyMMddhhmmss.SSSS";
	private static Logger log = LoggerFactory.getLogger(OutputHandler.class);
	
	/**
	 * Save an image to a file name (based on a timestamp)
	 * @param bufImage
	 * @param format
	 * @return
	 */
	public String saveImageFile(BufferedImage bufImage, String format){
		String path = null;
		SimpleDateFormat formatter = new SimpleDateFormat(fileNameFormat);
		Date now = new Date();
		path = outputPath + "test_" + formatter.format(now);
		try {
			File outputdir = new File(outputPath);
			if (!outputdir.exists()){
				outputdir.mkdir();
			}
			File outputfile = new File(path);
			ImageIO.write(bufImage, format, outputfile);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return path;
	}
	
	/**
	 * Save an image to a file name (based on a timestamp)
	 * @param rawResponse
	 * @param format
	 * @return
	 */
	public String saveRawResponseFile(byte[] rawResponse, String format){
		String path = null;
		SimpleDateFormat formatter = new SimpleDateFormat(fileNameFormat);
		Date now = new Date();
		path = outputPath + "test_" + formatter.format(now);
		try {
			File outputdir = new File(outputPath);
			if (!outputdir.exists()){
				outputdir.mkdir();
			}
			FileOutputStream outputfile = new FileOutputStream(path);
			outputfile.write(rawResponse);
			outputfile.flush();
			outputfile.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return path;
	}	
	
	public String saveString(String content, String name){
		String status = "";
		try {
			FileWriter writer = new FileWriter(name);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			status = e.getMessage();
		}
		return status;
	}
	
	public static void removeFile(String name) {
		try {
			File file = new File(name);
			file.delete();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Removing all files in the specified directory that start with the specified string
	 * Print deleted files to stdout for test result auditing.
	 * @param directory
	 * @param base
	 */
	public static void deleteBaseFiles(String directory, String base) {
		BaseFileFilter filter = new BaseFileFilter(base);
		File dir = new File(directory);

		String[] list = dir.list(filter);
		File file;
		if ( list != null && list.length == 0) return;

		for (int i = 0; i < list.length; i++) {
			file = new File(directory, list[i]);
			file.delete();
		}
	}
	
}

