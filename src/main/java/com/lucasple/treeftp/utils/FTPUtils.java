package com.lucasple.treeftp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.lucasple.treeftp.exceptions.CommandFailedException;

/**
 * Utility class used to easily handle FTP server responses
 * @author Lucas Plé
 *
 */
public class FTPUtils {
	
	public static final int beginStatusCode = 0;
	public static final int endStatusCode = 3;
	
	/**
	 * Method that provide a couple of the status code and the status message of a FTP server response
	 * @param reader The reader where the response must be read
	 * @return An Entry with code as the key and the message as the value
	 * @throws CommandFailedException In case there is a problem when reading the response
	 */
	public static Entry<Integer, String> getFTPResponse(BufferedReader reader) throws CommandFailedException {
		String response = null;
		try {
			response = reader.readLine();
		} catch (IOException e) {
			throw new CommandFailedException(e.getMessage());
		}
		return Map.entry(getFTPStatusCode(response), getFTPStatusMessage(response));
	}
	
	/**
	 * Method that extracts the status code from a FTP server response.
	 * @param ftpResponse
	 * @return The code as an int
	 */
	private static int getFTPStatusCode(String ftpResponse) {
		return Integer.parseInt(ftpResponse.substring(beginStatusCode, endStatusCode));
	}
	
	/**
	 * Method that extracts the message from a FTP server response.
	 * @param ftpResponse
	 * @return The message following the code
	 */
	private static String getFTPStatusMessage(String ftpResponse) {
		return ftpResponse.substring(endStatusCode);
	}

}
