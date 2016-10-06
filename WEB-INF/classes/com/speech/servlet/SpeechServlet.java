package com.speech.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.dc.audioconverter.CommandExecuter;
import com.speech.GSpeechResponseListenerImpl;

@WebServlet("/SpeechServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class SpeechServlet extends HttpServlet {

	static GSpeechDuplex duplex = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2455870336252572807L;
	/**
	 * Name of the directory where uploaded files will be saved, relative to the
	 * web application directory.
	 */
	private static final String SAVE_DIR = "recordedAudio";

	/**
	 * handles file upload
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String localWord = "";

		if ("getResult".equals(request.getParameter("action"))) {

			localWord = GSpeechResponseListenerImpl.mywords;

			if (!"".equals(localWord) && localWord != null)
				localWord = localWord.split(",")[0].replace("\"", "");

			response.getWriter().append(localWord);

			return;

		} else {

			// gets absolute path of the web application
			String appPath = request.getServletContext().getRealPath("");
			// constructs path of the directory to save uploaded file
			String savePath = appPath + File.separator + SAVE_DIR;

			createFileAndDirectory(savePath, request);
			String soxPath = "sox"+File.separator+"sox";//System.getenv("ProgramFiles");

			new CommandExecuter().execute(savePath, soxPath);

			try {
				Thread.sleep(1000);
				getTextFromVoice(savePath+File.separator , "audio.flac", response, request);

			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	/*
	 * private String extractFileName(Part part) { String contentDisp =
	 * part.getHeader("content-disposition"); String[] items =
	 * contentDisp.split(";"); for (String s : items) { if
	 * (s.trim().startsWith("filename")) { return s.substring(s.indexOf("=") +
	 * 2, s.length() - 1); } } return ""; }
	 */
	/**
	 * 
	 * @param savePath
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	private void createFileAndDirectory(String savePath, HttpServletRequest request)
			throws IOException, ServletException {

		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		String fileName = "";

		for (Part part : request.getParts()) {

			fileName = "audio.wav";
			part.write(savePath + File.separator + fileName);
			//part.write("D:\\" + SAVE_DIR + "\\" + fileName);
		}

	}

	/**
	 * 
	 * @return
	 */
	private GSpeechDuplex duplex() {
		if (duplex == null) {

			duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
			duplex.setLanguage("en");
		}

		return duplex;
	}

	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	private void getTextFromVoice(String path, String fileName, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		duplex = duplex();
		GSpeechResponseListener grlistener = new GSpeechResponseListenerImpl();

		File inp = new File(path + fileName);

		duplex.recognize(inp, 16000);

		System.out.print("bfr req res");

		((GSpeechResponseListenerImpl) grlistener).setRequest(request);
		((GSpeechResponseListenerImpl) grlistener).setResponse(response);
		System.out.print("bfr listener");
		duplex.addResponseListener(grlistener);
		// TODO Auto-generated method stub

	}

}