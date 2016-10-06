package com.dc.audioconverter;

import java.io.IOException;

public class CommandExecuter {
	
	public  void execute(String audioPath, String soxProgramPath) throws IOException
	{
		StringBuilder s = new StringBuilder(soxProgramPath+" "); // command itself is 'sox'

		// everything after this is an argument
		s.append(audioPath+"\\audio.wav ");
		s.append("-b 16 ");
		s.append(audioPath+"\\audio.flac ");
		s.append("channels 1 ");
		s.append("rate 16k");
		
		//Process soxProcess = new ProcessBuilder(s.toString()).start();
		//String cmd = "gedit";

		Runtime run = Runtime.getRuntime();

		Process pr = run.exec(s.toString());
		
	}
	/*public static void main(String args[])
	{
		try {
			
			execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
