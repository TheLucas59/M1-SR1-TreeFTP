package com.lucasple.treeftp.communication;

import java.net.Socket;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.commands.FTPCwd;
import com.lucasple.treeftp.commands.FTPList;
import com.lucasple.treeftp.commands.FTPPasv;
import com.lucasple.treeftp.commands.FTPPwd;
import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPFile;

public class ListingHandler {
	
	private static final Log LOGGER = LogFactory.getLog(ListingHandler.class);

	/**
	 * Calls the FTP PWD command on the distant server
	 * @param s The socket connected to the distant server
	 * @return The name of the current directory
	 */
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
	
	public static void changeWorkingDirectory(Socket s, String directory) {
		FTPCwd cwd = new FTPCwd(directory);
		
		try {
			cwd.run(s);
		}
		catch(CommandFailedException ce) {
			LOGGER.error("PWD failed", ce);
		}
	}
	
	/**
	 * Calls the FTP PASV command on the distant server
	 * @param s The socket connected to the distant server
	 * @return A new socket to receive data in passive mode from the distant server
	 */
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
	
	/**
	 * Calls the FTP LIST command on the distant server
	 * @param s The socket connected to the distant server
	 * @param socketData The socket to receive data in passive mode from the distant server
	 */
	public static void listDirectory(Socket s, SocketData socketData, FTPFile architecture) {
		FTPList list = new FTPList(socketData, architecture);
		try {
			list.run(s);
		} catch (CommandFailedException e1) {
			LOGGER.error("Error while running LIST command", e1);
		}
	}
	
}