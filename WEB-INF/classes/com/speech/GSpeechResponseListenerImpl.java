package com.speech;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

public class GSpeechResponseListenerImpl implements GSpeechResponseListener{
	
	private HttpServletResponse response;
	private HttpServletRequest request;
	public static String mywords="";

	@Override
	public void onResponse(GoogleResponse gr) {
		String grResponse = gr.getResponse();
		//System.out.println("bfr grResp");
		System.out.println(grResponse);
		mywords = grResponse;
}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}

