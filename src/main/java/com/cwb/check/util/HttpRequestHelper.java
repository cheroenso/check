package com.cwb.check.util;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Utility class for testing various http requests, including those that return errors.
 * Currently limited to requests that are expected to return text responses.
 * @author cbrann
 * 
 */
public class HttpRequestHelper {
	public static String cachHdr = "Cache-Control: ";
	public static String pragmaHdr = "Pragma: ";
	public static String typeHdr = "Content-Type: ";
	public static String lengthHdr = "Content-Length: ";
	public static String serverHdr = "Server: ";
	

	/**
	 * Send an HTTP GET request and check expected header values and body text.
	 * Helper method for JUnit tests.
	 * 
	 * @param request
	 * @param expectedValues Expected values to be compared with actual response from server
	 */
	public static void compareHttpRequest(String request, HttpResponseBean expectedValues) {
		HttpClient client = new DefaultHttpClient();
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		try {
			// Get response
			HttpResponse response = client.execute(new HttpGet(request));
			HttpEntity entity = response.getEntity();
			if (entity != null){
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					// Make sure the request returned something
					if (response.getStatusLine().getStatusCode() != 404) {
						InputStream responseBody = entity.getContent();	// We can only do this once
						bais = new ByteArrayOutputStream();
						byte[] byteChunk = new byte[4096];
						int i = 0;
						try {
							while (responseBody != null && (i = responseBody.read(byteChunk)) > 0){
								bais.write(byteChunk, 0, i);
							}
						} finally {
							bais.close();
						}
					}
				}
			}		
			// Check Status
			assertEquals("Status code error. ", expectedValues.getResponseCode(), Integer.toString(response.getStatusLine().getStatusCode()));
			assertEquals("Status reason error. ", expectedValues.getResponsePhrase(), response.getStatusLine().getReasonPhrase());
			// TODO handle unexpected headers
			Iterator <Map.Entry<String, String>>expectedHdrs = expectedValues.getHeaders().entrySet().iterator();
			// Check all expected headers
			while (expectedHdrs.hasNext()){
				boolean found = false;
				Map.Entry <String, String> pairs = (Map.Entry<String, String>)expectedHdrs.next();
				String hdrName = pairs.getKey().toString();
				String hdrValue = pairs.getValue().toString();
				// Look for the expected header in the response headers
				HeaderIterator it = response.headerIterator();
				while (it.hasNext()){
					String respHdr = it.next().toString();
					// is this the expected header?
					if (respHdr.startsWith(hdrName)){
						found = true;
						assertEquals(hdrName + " error", hdrValue, respHdr.substring(hdrName.length()));
					}
				}
				if (!found){
					assertTrue("Response is missing header " + hdrName, false);
				}
			}
			// Check response body
			assertEquals("Response body error for request:\n" + request + "\n", expectedValues.getResponseText(), bais.toString());
		} catch (Exception e) {
			// If test encounters an error print the Exception message and fail the test
			assertTrue(e.getMessage(), false);
		}
	}

}
