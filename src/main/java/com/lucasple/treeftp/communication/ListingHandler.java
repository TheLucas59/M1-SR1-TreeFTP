package com.lucasple.treeftp.communication;

import java.net.Socket;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.commands.FTPPwd;
import com.lucasple.treeftp.exceptions.CommandFailedException;

public class ListingHandler {
	
	private static final Log LOGGER = LogFactory.getLog(ListingHandler.class);

	public static String printWorkingDirectory(Socket s) {
		FTPPwd pwd = new FTPPwd();
		Entry<Integer, String> result = null;
		
		try {
			result = pwd.run(s);
		}
		catch(CommandFailedException ce) {
			LOGGER.error("PWD failed", ce);
		}
		
		String resultPhrase = "";
		if(result != null) {
			resultPhrase = result.getValue();
		}
		
		return resultPhrase.substring(resultPhrase.indexOf("\"")+1, resultPhrase.lastIndexOf("\""));
	}
}
