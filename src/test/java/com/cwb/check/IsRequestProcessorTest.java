package com.cwb.check;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cwb.request.IsRequestProcessor;

public class IsRequestProcessorTest extends IsRequestProcessor {
	String host = "localhost";
	String dir = "resources/";
	

	@Test
	public void testPNG() {
		IsRequestProcessor processor = new IsRequestProcessor();
		boolean totalSuccess = processor.testImageFile(host, dir + "requests_png.txt");
		assertTrue("Image Results",totalSuccess);
	}
	
	@Test
	public void testGIF() {
		IsRequestProcessor processor = new IsRequestProcessor();
		boolean totalSuccess = processor.testImageFile(host, dir + "requests_gif.txt");
		assertTrue("Image Results",totalSuccess);
	}
	
	@Test
	public void testJPG() {
		IsRequestProcessor processor = new IsRequestProcessor();
		boolean totalSuccess = processor.testImageFile(host, dir + "requests_jpg.txt");
		assertTrue("Image Results",totalSuccess);
	}
}
