package com.lucasple.treeftp.communication;

import java.net.Socket;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.commands.FTPList;
import com.lucasple.treeftp.commands.FTPPasv;
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
	
	public static SocketData passiveMode(Socket s) {
		FTPPasv pasv = new FTPPasv();
		
		Entry<Integer, String> result = null;
		
		try {
			result = pasv.run(s);
		}
		catch(CommandFailedException ce) {
			LOGGER.error("PWD failed", ce);
		}
		
		String resultPhrase = "";
		if(result != null) {
			resultPhrase = result.getValue();
			LOGGER.info(resultPhrase);
		}
		
		String informations = "";
		if(!resultPhrase.isBlank()) {
			informations = resultPhrase.substring(resultPhrase.indexOf("(")+1, resultPhrase.indexOf(")"));
		}
		
		String[] addressAndPort = informations.split(",");
		SocketData socketData = new SocketData();
		if(addressAndPort != null && addressAndPort.length > 0) {
			String[] address = new String[] { addressAndPort[0], addressAndPort[1], addressAndPort[2], addressAndPort[3] };
			String port = addressAndPort[4];
			socketData.setAddress(String.join(".", address));
			socketData.setPort(Integer.parseInt(port)*256 + Integer.parseInt(addressAndPort[5]));
		}
		
		return socketData;
	}
	
	public static void listDirectory(Socket s, SocketData socketData) {
		FTPList list = new FTPList(socketData);
		try {
			list.run(s);
			socketData.close();
		} catch (CommandFailedException e1) {
			LOGGER.error("Error while running LIST command", e1);
		}
	}
	
}