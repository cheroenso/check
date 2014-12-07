package com.cwb.check.util;

import java.util.HashMap;

/**
 * Contain expected response values for comparison with actual server response.
 * @author cbrann
 * 
 */
public class HttpResponseBean {
	private String responseCode;
	private String responsePhrase;
	private String bodyText;
	private HashMap<String, String> headers;

	HttpResponseBean() {
		responseCode = "";
		responsePhrase = "";
		bodyText = "";
		headers = new HashMap<String, String>();
 	}
	
	public String getResponseCode(){
		return responseCode;
	}
	
	public void setResponseCode(String code){
		if (code != null){
			responseCode = code;
		}
	}
	
	public String getResponsePhrase(){
		return responsePhrase;
	}
	
	public void setResponsePhrase(String phrase){
		if (phrase != null){
			responsePhrase = phrase;
		}
	}
	
	public String getResponseText(){
		return bodyText;
	}
	
	public void setResponseText(String respBodyText){
		if (respBodyText != null){
			bodyText = respBodyText;
		}
	}
	
	public HashMap<String, String> getHeaders(){
		return headers;
	}

}

