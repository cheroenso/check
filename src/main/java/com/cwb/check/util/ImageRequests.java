package com.cwb.check.util;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 *
 * @author cbrann
 *
 */
public class ImageRequests {


	/**
	 * Request an image from a server-under-test
	 * @param host
	 * @param port
	 * @param path
	 * @param query
	 * @return
	 */
	public BufferedImage getImage(String host, String port, String path, String query){
		BufferedImage buffImage = null;
		String userInfo = "";
		getImageAuth(host, userInfo, port, path, query);
		return buffImage;
	}

	/**
	 * Request an image from a server under-test-that requires http authentication.
	 * Pass 'user:password' values as a string to userInfo
	 * @param host
	 * @param userInfo
	 * @param port
	 * @param path
	 * @param query
	 * @return
	 */
	public BufferedImage getImageAuth(String host, String userInfo, String port, String path, String query){
		URI uri = null;
		BufferedImage buffImage = null;

		try {
			String scheme = "http";
			String authority = host + ":" + port;
			String fragment = "";
			try {
				uri = new URI(scheme, userInfo, host, Integer.parseInt(port), path, query, fragment);
				URL url = uri.toURL();
				try {
					buffImage = ImageIO.read(url);
				} catch (javax.imageio.IIOException iioe) {
					System.out.println("\nERROR retrieving " + scheme + authority + path + query + fragment);
					System.out.println(iioe.getMessage());
					buffImage = null;
				}
			} catch (Exception e) {
				System.out.println("ERROR request: " + scheme + authority + path + "?" + query + fragment);
				e.getMessage();
				e.printStackTrace();
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return buffImage;
	}


	/**
	 * Make http request and return the response as a byte array.
	 * @param host
	 * @param port
	 * @param path
	 * @param query
	 * @return
	 */
	public byte[] getStream(String host, String port, String path, String query){
		URI uri = null;
		byte[] bArray = null;

		try {
			String scheme = "http";
			String authority = host + ":" + port;
			String fragment = "";
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			InputStream is = null;
			try {
				uri = new URI(scheme, authority, path, query, fragment);
				URL url = new URL(uri.toURL().toString());
				try {
					is = url.openStream();
				} catch (IOException ioe) {
					System.err.println(ioe.getMessage());
					System.err.println("request: " + url);
				}
				byte[] byteChunk = new byte[4096];
				int i;
				while (is != null && (i = is.read(byteChunk)) > 0){
					bais.write(byteChunk, 0, i);
				}
			} catch (Exception e) {
				System.out.println("ERROR: stream request: " + scheme + authority + path + "?" + query + fragment);
				e.getMessage();
			} finally {
				if (is != null){
					is.close();
				}
			}
			if (bais != null){
				bArray = bais.toByteArray();
			}

		} catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return bArray;
	}

	/**
	 * Make http request and return the response as a byte array.
	 * Use when working with a target server that requires http authentication.
	 * Pass 'user:password' values as a string to userInfo
	 * @param host
	 * @param userInfo
	 * @param port
	 * @param path
	 * @param query
	 * @return
	 */
	public byte[] getStreamAuth(String host, String userInfo, String port, String path, String query){
		URI uri = null;
		byte[] bArray = null;

		try {
			String scheme = "http";
			String fragment = "";
			String encoded = new sun.misc.BASE64Encoder().encode (userInfo.getBytes());
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			InputStream is = null;
			try {
				uri = new URI(scheme, userInfo, host, Integer.parseInt(port), path, query, fragment);
				URL url = new URL(uri.toURL().toString());
				try {
					URLConnection conn = url.openConnection();
					conn.setRequestProperty ("Authorization", "Basic " + encoded);
					is = conn.getInputStream();
				} catch (IOException ioe) {
					System.err.println(ioe.getMessage());
					System.err.println("request: " + url);
				}
				byte[] byteChunk = new byte[4096];
				int i;
				while (is != null && (i = is.read(byteChunk)) > 0){
					bais.write(byteChunk, 0, i);
				}
			} catch (URISyntaxException e) {

				System.err.println(e.getMessage());
				System.err.println("scheme : " + scheme);
				System.err.println("userInfo : " + userInfo);
				System.err.println("host : " + host);
				System.err.println("port : " + port);
				System.err.println("path : " + path);
				System.err.println("query : " + query);
				System.err.println("fragment : " + fragment);
			} catch (Exception e) {
				System.err.println("ERROR: scheme:" + uri.getScheme() + ", userInfo:" + uri.getAuthority()
						+ ", host:" + uri.getHost() + ", port:" + uri.getPort() + ", path:" + uri.getPath());
				e.getMessage();
			} finally {
				if (is != null){
					is.close();
				}
			}
			if (bais != null){
				bArray = bais.toByteArray();
			}

		} catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return bArray;
	}

}

