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
import com.lucasple.treeftp.utils.SocketData;

/**
 * Utility class used to navigate on the distant server and list the files
 * @author Lucas Plé
 *
 */
public class ListingHandler {
	
	private static final Log LOGGER = LogFactory.getLog(ListingHandler.class);

	/**
	 * Calls the FTP PWD command on the distant server
	 * @param s The socket connected to the distant server
	 * @return The name of the current directory
	 * @throws CommandFailedException 
	 */
	public static String printWorkingDirectory(Socket s) throws CommandFailedException {
		FTPPwd pwd = new FTPPwd();
		Entry<Integer, String> result = null;
		
		result = pwd.run(s);
		
		String resultPhrase = "";
		if(result != null) {
			resultPhrase = result.getValue();
		}
		
		return resultPhrase.substring(resultPhrase.indexOf("\"")+1, resultPhrase.lastIndexOf("\""));
	}
	
	/**
	 * Calls the FTP CWD command on the distant server
	 * @param s The socket connected to the distant server
	 * @param directory The name of the directory to go
	 * @throws CommandFailedException
	 */
	public static void changeWorkingDirectory(Socket s, String directory) throws CommandFailedException {
		FTPCwd cwd = new FTPCwd(directory);
		cwd.run(s);
		LOGGER.info("Current working directory is now " + directory);
	}
	
	/**
	 * Calls the FTP PASV command on the distant server
	 * @param s The socket connected to the distant server
	 * @return A new socket to receive data in passive mode from the distant server
	 * @throws CommandFailedException 
	 */
	public static Entry<Integer, String> passiveMode(Socket s) throws CommandFailedException {
		FTPPasv pasv = new FTPPasv();
		Entry<Integer, String> result = null;
		result = pasv.run(s);
		return result;
	}

	/**
	 * Create a SocketData from the result of PASV command
	 * @param result The result of PASV
	 * @return A SocketData object to receive data from the distant server
	 */
	protected static SocketData createSocketData(Entry<Integer, String> result) {
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
	 * @throws CommandFailedException 
	 */
	public static void listDirectory(Socket s, SocketData socketData, FTPFile architecture) throws CommandFailedException {
		FTPList list = new FTPList(socketData, architecture);
		list.run(s);
	}
	
}