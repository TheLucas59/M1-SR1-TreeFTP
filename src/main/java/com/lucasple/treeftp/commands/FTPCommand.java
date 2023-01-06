package com.lucasple.treeftp.commands;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucasple.treeftp.exceptions.CommandFailedException;
import com.lucasple.treeftp.utils.FTPUtils;
import com.lucasple.treeftp.utils.SocketUtils;

/**
 * Interface defining any FTP command
 * @author lucas
 *
 */
public abstract class FTPCommand {
	
	private static final Log LOGGER = LogFactory.getLog(FTPCommand.class);
	
	protected String commandName;
	protected int expectedStatusCode;

	/**
	 * Return the server response for the run command
	 * @param s The Socket to communicate with the server
	 * @return A Map Entry which will hae the status code as the key and the following message as the value
	 * @throws CommandFailedException If there is a problem while running the command
	 */
	public Entry<Integer, String> run(Socket s) throws CommandFailedException {
		BufferedReader reader = SocketUtils.getReadableInputStream(s);
		PrintWriter writer = SocketUtils.getWritableOutputStream(s);
		
		callCommand(writer);
		Entry<Integer, String> response = FTPUtils.getFTPResponse(reader);
		checkResponse(response);
		
		return response;
	}

	/**
	 * This method must be implemented in the sub-classes and it is where call to the command should be name.
	 * @param writer The writer to run the command on the distant FTP server
	 */
	protected abstract void callCommand(PrintWriter writer);

	/**
	 * This method checks the response of the distant FTP server to know if it was successful or not
	 * @param response The response of the distant server
	 */
	private void checkResponse(Entry<Integer, String> response) {
		if(response.getKey() == expectedStatusCode) {
			LOGGER.info(commandName + " command successfull");
		}
		else {
			LOGGER.warn(commandName + " command returned an unexpected status : " + response.getKey() + " : " + response.getValue());
		}
	}
}
